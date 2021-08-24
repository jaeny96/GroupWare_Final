package com.group.approval.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.Reference;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.UpdateException;

public interface ProcessDocsDAO {

	/**
	 *  참조자는 참조를 승인한다. (Reference : 참조)
	 * @param re
	 * @throws UpdateException
	 */
	public void updateReference(Reference re) throws UpdateException;

	/**
	 *  결재자는 승인 + 코멘트를 남길 수 있다. (Approval : 결재)
	 * 
	 * @param ap
	 * @throws UpdateException
	 */
	public void updateAudmitAp(Approval ap) throws UpdateException;

	
	/**
	 *  결재자는 반려 + 코멘트를 남길 수 있다. (Approval : 결재)
	 * 
	 * @param ap
	 * @throws UpdateException
	 */
	public void updateRefuseAp(Approval ap) throws UpdateException;
	
	
	/**
	 *  합의자는 승인 + 코멘트를 남길 수 있다. (Agreement : 합의 )
	 * 
	 * @param ag
	 * @throws UpdateException
	 */
	public void updateAudmitAg(Agreement ag) throws UpdateException;


	/**
	 *  합의자는 반려 + 코멘트를 남길 수 있다. (Agreement : 합의 )
	 * 
	 * @param ag
	 * @throws UpdateException
	 */
	public void updateRefuseAg(Agreement ag) throws UpdateException;


//	/**
//	 * 모두 승인처리를 내리면,최종 문서 상태의 값을 '승인'으로 바꾼다.
//	 * @param docsNo, id
//	 * @throws ModifyException
//	 */
//	void documentAudmit(String docsNo,String id) throws UpdateException;
//
//	/**
//	 * 한명이라도 반려시 '반려'로 변경한다.
//	 * @param docsNo, id
//	 * @throws ModifyException
//	 */
//	void documentRefuse(String document_no,String id) throws UpdateException;
}
