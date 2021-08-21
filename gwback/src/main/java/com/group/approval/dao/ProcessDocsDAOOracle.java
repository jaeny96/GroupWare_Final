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
			list=session.selectList("com.group.approval.ApprovalWriteMapper.searchByDep", depTitle);
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
			list=session.selectList("com.group.approval.ApprovalWriteMapper.searchApLine");
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
				throw new UpdateException("업데이트 실패했습니다.");
			}
		}catch(Exception e) {
			throw new UpdateException(e.getMessage());
		}finally {
			session.close();
		}
	}
	

//	@Override
//	// 사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (결재승인테이블)
//	public void updateApproval(Approval ap) throws UpdateException {
//		// DB연결
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//			con.setAutoCommit(false);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new UpdateException(e.getMessage());
//		}
//
//		// ap_type, ap_date, ap_comment가 수정돼야 함
//		String updateApprovalSQL = "UPDATE approval SET ap_type=?, ap_date=SYSTIMESTAMP, ap_comment=?\r\n"
//				+ "WHERE document_no=? and employee_id=?";
//
//		PreparedStatement pstmt = null;
//
//		try {
//			pstmt = con.prepareStatement(updateApprovalSQL);
//			pstmt.setString(1, ap.getAp_type().getApStatus_type());
//			pstmt.setString(2, ap.getAp_ap_comment());
//			pstmt.setString(3, ap.getDocument_no().getDocument_no());
//			pstmt.setString(4, ap.getEmployee_id().getEmployee_id());
//			int cnt = pstmt.executeUpdate();
//			if (cnt == 1) {
//				System.out.println("결재완료");
//			} else {
//				System.out.println(cnt);
//				throw new UpdateException("입력양식을 확인해주세요");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new UpdateException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, null);
//		}
//
//	}
//
//	// 사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (합의승인테이블)
//	@Override
//	public void updateAgreement(Agreement ag) throws UpdateException {
//		// DB연결
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//			con.setAutoCommit(false);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new UpdateException(e.getMessage());
//		}
//		// ap_type, ap_date, ap_comment가 수정돼야 함
//		String updateAgreementSQL = "UPDATE agreement SET ap_type=?, ap_date=SYSTIMESTAMP, ap_comment=?\r\n"
//				+ "WHERE document_no=? and employee_id=?";
//
//		PreparedStatement pstmt = null;
//
//		try {
//			pstmt = con.prepareStatement(updateAgreementSQL);
//			pstmt.setString(1, ag.getAg_ap_type().getApStatus_type());
//			pstmt.setString(2, ag.getAg_ap_comment());
//			pstmt.setString(3, ag.getDocument_no().getDocument_no());
//			pstmt.setString(4, ag.getEmployee_id().getEmployee_id());
//			int cnt = pstmt.executeUpdate();
//			if (cnt == 1) {
//				System.out.println("합의결재 완료");
//			} else {
//				System.out.println(cnt);
//				throw new UpdateException("입력양식을 확인해주세요");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new UpdateException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, null);
//		}
//
//	}
//

//
//	@Override
//	// 모두 승인처리를 할 경우, 최종 상태를 '승인'으로
//	public void documentAudmit(String document_no, String id) throws ModifyException {
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
