package com.group.approval.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.exception.FindException;
import com.group.exception.SearchException;

@Repository("confirmDocsDAO")
public class ConfirmDocsDAOOracle implements ConfirmDocsDAO {

	@Autowired
	private SqlSessionFactory sessionFactory;

	/**
	 * 사용자는 결재 문서를 선택했을 때, 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
	 * @param docsNo
	 * @throws FindException
	 */
	@Override
	public Document selectByDocsDetail(String docsNo) throws FindException {

		SqlSession session = null;
		
		try {
			//List<Document> list = new ArrayList<>();
			Document d ;
			session = sessionFactory.openSession();
			d=session.selectOne("com.group.approval.ApprovalConfirmMapper.selectByDocsDetail", docsNo);
			
			
			

			return d;
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
	 * (전체)사용자는 확인/미확인 문서를 선택해서 볼 수 있다.
	 * @return 확인/미확인한 문서 목록
	 * @param id,check
	 * @throws FindException
	 */
	@Override
	public List<Document> selectByCheckAll(String id,String check) throws FindException {
		SqlSession session = null;
		try {
			List<Document> list = new ArrayList<>();
			session = sessionFactory.openSession();
			HashMap<String, String>map = new HashMap<>();
			map.put("id", id);
			map.put("check", check);
			return session.selectList("com.group.approval.ApprovalConfirmMapper.selectByCheckAll", map);
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
	 * (대기/승인/반려)사용자는 확인/미확인 문서를 선택해서 볼 수 있다.
	 * @return 확인/미확인한 문서 목록
	 * @param id,check,status
	 * @throws FindException
	 */
	@Override
	public List<Document> selectByCheckStatus(String id,String check,String status) throws FindException {
		SqlSession session = null;
		
		try {
			List<Document> list = new ArrayList<>();
			
			session = sessionFactory.openSession();
			HashMap<String, String>map = new HashMap<>();
			map.put("id", id);
			map.put("status", status);
			map.put("check", check);
			System.out.println("map"+map);
			return session.selectList("com.group.approval.ApprovalConfirmMapper.selectByCheckStatus", map);
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
	 * 사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
	 * @param id,docsNo
	 * @throws FindException
	 */
	@Override
	public List<Approval> selectByMyClick(String id, String docsNo) throws FindException {
		SqlSession session = null;
		
		try {
			List<Approval> list = new ArrayList<>();
			
			session = sessionFactory.openSession();
			HashMap<String, String>map = new HashMap<>();
			map.put("id", id);
			map.put("docsNo", docsNo);
			
			list=session.selectList("com.group.approval.ApprovalConfirmMapper.selectByMyClick", map);
			System.out.println(list);
			if(list.size()==0) {
				throw new FindException("승인 목록이 없습니다.");
			}

			return list;
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
	 * 사용자는 결재 문서를 선택했을 때, 해당 문서의 코멘트 정보를 확인할 수 있다.
	 * @param docsNo
	 * @throws FindException
	 */
	@Override
	public List<Approval> selectByComments(String docsNo) throws FindException {

		SqlSession session = null;
		
		try {
			List<Approval> list = new ArrayList<>();
			
			session = sessionFactory.openSession();
		
			list=session.selectList("com.group.approval.ApprovalConfirmMapper.selectByComments", docsNo);
			System.out.println(list);
			if(list.size()==0) {
				throw new FindException("코멘트 내용이  없습니다.");
			}

			return list;
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
	 * (전체)문서에 대해 제목/내용으로 검색할 수 있다.
	 * @param id
	 * @param searchType : title / content
	 * @param mySearch 내가 검색하는 값
	 * @return 검색 내용
	 * @throws SearchException
	 */
	@Override
	public List<Document> selectBySearchAll(String id, String searchType,String mySearch) throws SearchException {
		// TODO Auto-generated method stub

		SqlSession session = null;
		
		try {
			List<Document> list = new ArrayList<>();
			
			session = sessionFactory.openSession();
			Map<String,Object> map= new HashMap<String, Object>();
			map.put("id", id);
			map.put("searchType", searchType);
			map.put("search", mySearch);
		
			list=session.selectList("com.group.approval.ApprovalConfirmMapper.selectBySearchAll", map);
			System.out.println(list);
			if(list.size()==0) {
				throw new FindException("검색값이  없습니다.");
			}

			return list;
		}catch(Exception e) {
			e.printStackTrace();
			throw new SearchException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * (대기/승인/반려)문서에 대해 제목/내용으로 검색할 수 있다.
	 * @param id
	 * @param searchType : title / content
	 * @param mySearch 내가 검색하는 값
	 * @param status 문서 상태값 
	 * @return 검색 내용
	 * @throws SearchException
	 */
	@Override
	public List<Document> selectBySearchStatus(String id, String searchType,String mySearch,String status) throws SearchException {
		// TODO Auto-generated method stub

		SqlSession session = null;
		
		try {
			List<Document> list = new ArrayList<>();
			
			session = sessionFactory.openSession();
			Map<String,Object> map= new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", status);
			map.put("searchType", searchType);
			map.put("search", mySearch);
		
			list=session.selectList("com.group.approval.ApprovalConfirmMapper.selectBySearchStatus", map);
			System.out.println(list);
			if(list.size()==0) {
				throw new FindException("검색값이  없습니다.");
			}

			return list;
		}catch(Exception e) {
			e.printStackTrace();
			throw new SearchException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}


}
