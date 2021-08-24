package com.group.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.group.board.dto.Board;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;


@Repository("BoardDAO")
public class BoardDAOOracle implements BoardDAO {
	@Autowired
	private DataSource ds;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public List<Board> selectAll() throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectList("com.group.board.dto.BoardMapper.selectAll");
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Board> selectAll(int currentPage) throws FindException {
		int cnt_per_page = 5;
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			HashMap<String, Integer> map = new HashMap<>();
			map.put("cnt_per_page", cnt_per_page);
			map.put("currentPage", currentPage);
			List<Board> bdlist = session.selectList("com.group.board.dto.BoardMapper.selectAllPage", map);
			if (bdlist.size() == 0) {
				throw new FindException("게시글이 없습니다");
			}
			return bdlist;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Board> selectByWord(String category, String word) throws FindException {
		SqlSession session = null;
		// List<Board> bdList = new ArrayList<Board>();
		try {
			session = sqlSessionFactory.openSession();
			Map<String, String> map = new HashMap<String, String>();
			map.put("category", category);
			map.put("word", word);			
			return session.selectList("com.group.board.dto.BoardMapper.selectByWord", map);
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public Board selectBdInfo(String bdNo) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Board bd = session.selectOne("com.group.board.dto.BoardMapper.selectBdInfo", bdNo);
			if (bd == null) {
				throw new FindException("게시글이 없습니다, BdInfo메서드 에러메시지");
			}
			return bd;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void insert(Board bd) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.insert("com.group.board.dto.BoardMapper.insert", bd);
		} catch (Exception e) {
			throw new AddException(e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public void update(Board bd) throws ModifyException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			int rowcnt = session.update("com.group.board.dto.BoardMapper.update", bd);
			if (rowcnt == 1) {
				System.out.println("내용이 변경되었습니다");
			} else {
				throw new ModifyException("내용이 변경되지 않았습니다");
			}
		} catch (Exception e) {
			throw new ModifyException(e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	@Transactional(rollbackFor = RemoveException.class)
	public void delete(Board bd) throws RemoveException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			int rowcnt = session.update("com.group.board.dto.BoardMapper.delete", bd);
			if (rowcnt == 0) {
				throw new RemoveException("삭제실패: 작성자아이디 확인");
//			session.update("com.group.board.dto.BoardMapper.delete", bd);
			}
			System.out.println("삭제완료");
		} catch (Exception e) {
			throw new RemoveException(e.getMessage());
		}
	}
}