package com.group.approval.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.exception.FindException;
import com.group.exception.UpdateException;
import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.employee.dto.Employee;

@Repository("ProcessDocsDAO")
public class ProcessDocsDAOOracle implements ProcessDocsDAO {
	
	@Autowired
	private SqlSessionFactory sessionFactory;

	/**
	 * 결재선을 설정하는 과정에서 참여시킬 사원의 조직을 검색한다
	 * @param depTitle
	 * @throws FindException
	 */
	public List<Employee> searchByDep(String depTitle) throws FindException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Employee> list = new ArrayList<>();
			list=session.selectList("com.group.approval.ApprovalProcessMapper.searchByDep", depTitle);
			System.out.println(list);
			if(list.size()==0) {
				throw new FindException("검색값이  없습니다.");
			}
			return list;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	/**
	 *  전체 사원의 이름, 부서 정보 갖고오기
	 * @return 조회한 사원들
	 */
	@Override
	public List<Employee> searchApLineStaff() throws FindException {
		
		SqlSession session = null;
		try {
			
			session = sessionFactory.openSession();
			List<Employee> list = new ArrayList<>();
			list=session.selectList("com.group.approval.ApprovalProcessMapper.searchApLine");
			System.out.println(list);
			if(list.size()==0) {
				throw new FindException("검색값이  없습니다.");
			}
			return list;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	/**
	 *  참조자는 참조를 승인한다.
	 * @param documentId,documentNo
	 * @throws UpdateException
	 */
	@Override
	public void updateReference(String documentNo, String documentId) throws UpdateException {
		
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			HashMap<String, String> map = new HashMap<>();
			map.put("docsNo", documentNo);
			map.put("docsId", documentId);
			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateRe", map);
			if(rowcnt == 0) {
				throw new UpdateException("참조 승인처리에 실패했습니다.");
			}
		}catch(Exception e) {
			throw new UpdateException(e.getMessage());
		}finally {
			session.close();
		}
	}
	

	/**
	 *  사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (결재승인테이블)
	 * 
	 * @param ap
	 * @throws UpdateException
	 */
	@Override
	public void updateApproval(Approval ap) throws UpdateException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateAp", ap);
			if(rowcnt == 0) {
				throw new UpdateException("결재 승인처리에 실패했습니다.");
			}
		}catch(Exception e) {
			throw new UpdateException(e.getMessage());
		}finally {
			session.close();
		}
	}

	/**
	 *  사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (합의승인테이블)
	 * 
	 * @param ag
	 * @throws UpdateException
	 */
	@Override
	public void updateAgreement(Agreement ag) throws UpdateException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateAg", ag);
			if(rowcnt == 0) {
				throw new UpdateException("합의 승인처리에 실패했습니다.");
			}
		}catch(Exception e) {
			throw new UpdateException(e.getMessage());
		}finally {
			session.close();
		}
	}



//	@Override
//	// 모두 승인처리를 할 경우, 최종 상태를 '승인'으로
//	public void documentAudmit(String document_no, String id) throws ModifyException {
//		SqlSession session = null;
//		try {
//			session = sessionFactory.openSession();
//			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateAg", ag);
//			if(rowcnt == 0) {
//				throw new UpdateException("합의 승인처리에 실패했습니다.");
//			}
//		}catch(Exception e) {
//			throw new UpdateException(e.getMessage());
//		}finally {
//			session.close();
//		}
//		
//		// DB연결
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		String audmitCallSQL = "call audmit(?,?)";
//		CallableStatement cstmt = null;
//		try {
//			cstmt = con.prepareCall(audmitCallSQL);
//			cstmt.setString(1, document_no);
//			cstmt.setString(2, id);
//			cstmt.execute();
//
//			System.out.println("승인 프로시저가 호출되었습니다");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	// 한 명이라도 반려 할 경우, 최종 상태는 '반려'
//	public void documentRefuse(String document_no, String id) throws ModifyException {
//		// DB연결
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		String refuseCallSQL = "call refuse(?,?)";
//		CallableStatement cstmt = null;
//		try {
//			cstmt = con.prepareCall(refuseCallSQL);
//			cstmt.setString(1, document_no);
//			cstmt.setString(2, id);
//			cstmt.execute();
//
//			System.out.println("반려 프로시저가 호출되었습니다");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
