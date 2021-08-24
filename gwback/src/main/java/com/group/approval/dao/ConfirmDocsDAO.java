package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.exception.FindException;
import com.group.exception.SearchException;
import com.group.employee.dto.Employee;

public interface ConfirmDocsDAO {
	
	/**
	 *  사용자는 결재 문서를 선택했을 때, 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
	 * @param docsNo
	 * @throws FindException
	 */
	Document selectByDocsDetail(String docsNo) throws FindException;

	/**
	 * (전체)사용자는 확인/미확인 문서를 선택해서 볼 수 있다.
	 * @return 확인/미확인한 문서 목록
	 * @param id,check
	 * @throws FindException
	 */
	List<Document> selectByCheckAll(String id,String check) throws FindException;
	
	/**
	 * (대기/승인/반려)사용자는 확인/미확인 문서를 선택해서 볼 수 있다.
	 * @return 확인/미확인한 문서 목록
	 * @param id,check,status
	 * @throws FindException
	 */
	List<Document> selectByCheckStatus(String id,String check,String status) throws FindException;
	
	/**
	 * 사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
	 * @param id,docsNo
	 * @throws FindException
	 */
	List<Approval> selectByMyClick(String id, String docsNo) throws FindException;




	/**
	 * 사용자는 결재 문서를 선택했을 때, 해당 문서의 코멘트 정보를 확인할 수 있다.
	 * @param docsNo
	 * @throws FindException
	 */
	 List<Approval> selectByComments(String docsNo) throws FindException;
	

	/**
	* (전체)문서에 대해 제목/내용으로 검색할 수 있다.
	* @param id
	* @param searchType : title / content
	* @param mySearch 내가 검색하는 값
	* @return 검색 내용
	* @throws SearchException
	*/
	List<Document> selectBySearchAll(String id, String searchType,String mySearch) throws SearchException;

	/**
	 * (대기/승인/반려)문서에 대해 제목/내용으로 검색할 수 있다.
	 * @param id
	 * @param searchType : title / content
	 * @param mySearch 내가 검색하는 값
	 * @param status 문서 상태값 
	 * @return 검색 내용
	 * @throws SearchException
	 */
	List<Document> selectBySearchStatus(String id, String searchType,String mySearch,String status) throws SearchException;


}
