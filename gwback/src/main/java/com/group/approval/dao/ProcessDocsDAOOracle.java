package com.group.approval.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.UpdateException;
import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Reference;
import com.group.employee.dto.Employee;

@Repository("ProcessDocsDAO")
public class ProcessDocsDAOOracle implements ProcessDocsDAO {
	
	@Autowired
	private SqlSessionFactory sessionFactory;

	
	/**
	 *  참조자는 참조를 승인한다.  (Reference : 참조)
	 * @param re
	 * @throws UpdateException
	 */
	@Override
	public void updateReference(Reference re) throws UpdateException {
		SqlSession session = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println(re);
			session = sessionFactory.openSession();
			map.put("docsNo", re.getDocumentNo()); 
			map.put("id", re.getEmployee().getEmployeeId());
			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateRe", re);
			session.update("com.group.approval.ApprovalProcessMapper.audmitProcedure", map);
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
	 *  결재자는 승인 + 코멘트를 남길 수 있다. (Approval : 결재)
	 * 
	 * @param ap
	 * @throws UpdateException
	 */
	@Override
	public void updateAudmitAp(Approval ap) throws UpdateException {
		Map<String, Object> map = new HashMap<String, Object>();
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			map.put("docsNo", ap.getDocumentNo()); 
			map.put("id", ap.getEmployee().getEmployeeId());
			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateAp", ap);
			session.update("com.group.approval.ApprovalProcessMapper.audmitProcedure", map);
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
	 *  결재자는 반려 + 코멘트를 남길 수 있다. (Approval : 결재)
	 * 
	 * @param ap
	 * @throws UpdateException
	 */
	@Override
	public void updateRefuseAp(Approval ap) throws UpdateException {
		Map<String, Object> map = new HashMap<String, Object>();
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			map.put("docsNo", ap.getDocumentNo()); 
			map.put("id", ap.getEmployee().getEmployeeId());
			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateAp", ap);
			session.update("com.group.approval.ApprovalProcessMapper.refuseProcedure", map);
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
	 *  합의자는 승인 + 코멘트를 남길 수 있다. (Agreement : 합의 )
	 * 
	 * @param ag
	 * @throws UpdateException
	 */
	@Override
	public void updateAudmitAg(Agreement ag) throws UpdateException {
		SqlSession session = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			session = sessionFactory.openSession();
			map.put("docsNo", ag.getDocumentNo()); 
			map.put("id", ag.getEmployee().getEmployeeId());
			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateAg", ag);
			session.update("com.group.approval.ApprovalProcessMapper.audmitProcedure", map);
			
			if(rowcnt == 0) {
				throw new UpdateException("합의 승인처리에 실패했습니다.");
			}
		}catch(Exception e) {
			throw new UpdateException(e.getMessage());
		}finally {
			session.close();
		}
	}


	/**
	 *  합의자는 반려 + 코멘트를 남길 수 있다. (Agreement : 합의 )
	 * 
	 * @param ag
	 * @throws UpdateException
	 */
	@Override
	public void updateRefuseAg(Agreement ag) throws UpdateException {
		SqlSession session = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			session = sessionFactory.openSession();
			map.put("docsNo", ag.getDocumentNo()); 
			map.put("id", ag.getEmployee().getEmployeeId());
			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.updateAg", ag);
			session.update("com.group.approval.ApprovalProcessMapper.refuseProcedure", map);
			
			if(rowcnt == 0) {
				throw new UpdateException("합의 승인처리에 실패했습니다.");
			}
		}catch(Exception e) {
			throw new UpdateException(e.getMessage());
		}finally {
			session.close();
		}
	}




//	/**
//	 * 모두 승인처리를 내리면,최종 문서 상태의 값을 '승인'으로 바꾼다.
//	 * @param docsNo, id
//	 * @throws ModifyException
//	 */
//	@Override
//	public void documentAudmit(String docsNo, String id) throws UpdateException {
//		SqlSession session = null;
//		
//
//		try {
//			System.out.println("합의 프로시저 호출 ");
//			session = sessionFactory.openSession();
//	
//			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.audmitProcedure", map);
//			if(rowcnt == 0) {
//				throw new UpdateException("합의 프로시저 처리에 실패했습니다.");
//			}
//		}catch(Exception e) {
//			throw new UpdateException(e.getMessage());
//		}finally {
//			session.close();
//		}
//	
//	}
//
//	/**
//	 * 한명이라도 반려시 '반려'로 변경한다.
//	 * @param docsNo, id
//	 * @throws ModifyException
//	 */
//	@Override
//	public void documentRefuse(String docsNo, String id) throws UpdateException {
//		SqlSession session = null;
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		try {
//			session = sessionFactory.openSession();
//			System.out.println("반려 프로시저 호출 ");
//			map.put("docsNo", docsNo); 
//			map.put("id", id);
//			int rowcnt = session.update("com.group.approval.ApprovalProcessMapper.refuseProcedure", map);
//			if(rowcnt == 0) {
//				throw new UpdateException("반려 프로시저 처리에 실패했습니다.");
//			}
//		}catch(Exception e) {
//			throw new UpdateException(e.getMessage());
//		}finally {
//			session.close();
//		}
//	}
}
