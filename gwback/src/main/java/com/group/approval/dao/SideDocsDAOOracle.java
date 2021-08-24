package com.group.approval.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.approval.dto.Document;
import com.group.exception.FindException;

@Repository("sideDocsDAO")
public class SideDocsDAOOracle implements SideDocsDAO{

	@Autowired
	private SqlSessionFactory sesionFactory;

	/**
	 * (전체)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @return 전체 사이드바 목록 개수 
	 * @param id 
	 * @throws FindException
	 */
	@Override
	public int selectByCountAll(String id) throws FindException {
		SqlSession session = null;
		
		try {
			session = sesionFactory.openSession();
			int cntAll = session.selectOne("com.group.approval.ApprovalSideMapper.selectByCountAll", id);
			if(cntAll==0) {
				throw new FindException("전체 목록이 존재하지 않습니다."); 
			}
			return cntAll;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	/**
	 * (대기)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @return 대기 사이드바 목록 개수  
	 * @param id
	 * @throws FindException
	 */
	@Override
	public int selectByCountWait(String id) throws FindException {
		SqlSession session = null;
		
		try {
			session = sesionFactory.openSession();
			int cntAll = session.selectOne("com.group.approval.ApprovalSideMapper.selectByCountWait", id);
			if(cntAll==0) {
				throw new FindException("대기 목록이 존재하지 않습니다."); 
			}
			return cntAll;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * (승인)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @return 승인 사이드바 목록 개수  
	 * @param id
	 * @throws FindException
	 */	
	@Override
	public int selectByCountOk(String id) throws FindException {
		SqlSession session = null;
		
		try {
			session = sesionFactory.openSession();
			int cntAll = session.selectOne("com.group.approval.ApprovalSideMapper.selectByCountOk", id);
			if(cntAll==0) {
				throw new FindException("대기 목록이 존재하지 않습니다."); 
			}
			return cntAll;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	/**
	 * (반려)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @return 반려 사이드바 목록 개수  
	 * @param id
	 * @throws FindException
	 */
	@Override
	public int selectByCountNo(String id) throws FindException {
		SqlSession session = null;
		
		try {
			session = sesionFactory.openSession();
			int cntAll = session.selectOne("com.group.approval.ApprovalSideMapper.selectByCountNo", id);
			if(cntAll==0) {
				throw new FindException("대기 목록이 존재하지 않습니다."); 
			}
			return cntAll;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	 /**
	  * (전체)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	  * @return 전체 문서 목록 
	  * @param id
	  * @throws FindException
	  */
	@Override
	public List<Document> selectByListAll(String id) throws FindException {
		SqlSession session = null;
		try {
			session = sesionFactory.openSession();
			return session.selectList("com.group.approval.ApprovalSideMapper.selectByListAll",id);
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	 /**
	  * (승인/대기/반려)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	  * @return 승인/대기/반려 문서 목록
	  * @param id,status
	  * @throws FindException
	  */
	@Override
	public List<Document> selectByListStatus(String id,String status) throws FindException {
		SqlSession session = null;
		try {
			session = sesionFactory.openSession();
			HashMap<String, String>map = new HashMap<>();
			map.put("id", id);
			map.put("status", status);
			return session.selectList("com.group.approval.ApprovalSideMapper.selectByListStatus",map);
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

}
