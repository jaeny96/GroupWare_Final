package com.group.board.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.group.board.dto.BoardComment;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;

@Repository("BoardCommentDAO")
public class BoardCommentDAOOracle implements BoardCommentDAO {
	@Autowired
	private DataSource ds;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public List<BoardComment> selectAll(String bdNo) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<BoardComment> cmList = session.selectList("com.group.board.dto.BoardCommentMapper.selectAll", bdNo);
			if (cmList.size() == 0) {
				throw new FindException("댓글이 없습니다");
			}
			return cmList;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void insert(BoardComment cm) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.insert("com.group.board.dto.BoardCommentMapper.insert", cm);
		} catch (Exception e) {
			throw new AddException(e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	@Transactional(rollbackFor = RemoveException.class)
	public void delete(BoardComment cm) throws RemoveException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.group.board.dto.BoardCommentMapper.delete", cm);
		} catch (Exception e) {
			throw new RemoveException(e.getMessage());
		}
	}
	@Override
	@Transactional(rollbackFor = RemoveException.class)
	public void deleteAdmin(BoardComment cm) throws RemoveException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.group.board.dto.BoardCommentMapper.deleteAdmin", cm);
		} catch (Exception e) {
			throw new RemoveException(e.getMessage());
		}
	}

}