package com.group.approval.dao;

import java.util.HashMap;
import java.util.List;

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
	 * 결재선을 설정하는 과정에서 참여시킬 사원의 조직을 검색한다
	 * @param depTitle
	 * @throws FindException
	 */
	List<Employee> searchByDep(String depTitle) throws FindException;
	/**
	 *  전체 사원의 이름, 부서 정보 갖고오기
	 * @return 조회한 사원들
	 */
	public List<Employee> searchApLineStaff() throws FindException;

	/**
	 *  참조자는 참조를 승인한다.
	 * @param documentId,documentNo
	 * @throws UpdateException
	 */
	void updateReference(String documentNo, String documentId) throws UpdateException;
	
	/**
	 *  사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (결재승인테이블)
	 * 
	 * @param ap
	 * @throws UpdateException
	 */
	void updateApproval(Approval ap) throws UpdateException;

	/**
	 *  사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (합의승인테이블)
	 * 
	 * @param ag
	 * @throws UpdateException
	 */
	void updateAgreement(Agreement ag) throws UpdateException;
//
//	/**
//	 * 10. 모두 승인처리를 내리면,최종 문서 상태의 값을 '승인'으로 바꾼다.
//	 * @param document_no, String id
//	 * @throws ModifyException
//	 */
//	void documentAudmit(String document_no,String id) throws ModifyException;
//
//	/**
//	 * 11.한명이라도 반려시 '반려'로 변경한다.
//	 * @param document_no, String id
//	 * @throws ModifyException
//	 */
//	void documentRefuse(String document_no,String id) throws ModifyException;
}
