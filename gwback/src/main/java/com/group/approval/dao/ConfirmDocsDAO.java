package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.exception.FindException;
import com.group.exception.SearchException;
import com.group.employee.dto.Employee;

public interface ConfirmDocsDAO {

	/**
	 * (전체/대기/반려/승인)사용자는 확인/미확인 문서를 선택해서 볼 수 있다.
	 * @return 확인/미확인한 전체 문서 목록
	 * @param id,check,status
	 * @throws FindException
	 */
	List<Document> selectByCheck(String id,String status,String check) throws FindException;
	
	/**
	 * 사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
	 * 
	 * @param employee_id,document_no
	 * @throws FindException
	 */
	List<Approval> selectByMyClick(String employee_id, String document_no) throws FindException;

	/**
	 *  사용자는 결재 문서를 선택했을 때, 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
	 * 
	 * @param document_no
	 * @throws FindException
	 */
	Document selectByDocsDetail(String document_no) throws FindException;

	/**
	 * 사용자는 결재 문서를 선택했을 때, 해당 문서의 코멘트 정보를 확인할 수 있다.
	 * 
	 * @param document_no
	 * @throws FindException
	 */
	 List<Approval> selectByComments(String document_no) throws FindException;
	

	/**
	 * (전체)문서에 대해 제목으로 검색할 수 있다.
	 * @param employee_id
	 * @param title
	 * @return 검색 내용
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearch(String employee_id, String title) throws FindException, SearchException;

//	/**
//	 * (전체)문서에 대해 내용으로 검색할 수 있다.
//	 * @param employee_id
//	 * @param content
//	 * @return title
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	List<Document> selectBySearchContent(String employee_id, String content) throws FindException, SearchException;
//	
//	/**
//	 * (대기)문서에 대해 제목으로 검색할 수 있다.
//	 * @param employee_id
//	 * @param title
//	 * @return 검색 내용
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	List<Document> selectBySearchTitleWait(String employee_id, String title) throws FindException, SearchException;
//
//	/**
//	 * (대기)문서에 대해 내용으로 검색할 수 있다.
//	 * @param employee_id
//	 * @param content
//	 * @return title
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	List<Document> selectBySearchContentWait(String employee_id, String content) throws FindException, SearchException;
//
//	/**
//	 * (반려)문서에 대해 제목으로 검색할 수 있다.
//	 * @param employee_id
//	 * @param title
//	 * @return 검색 내용
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	List<Document> selectBySearchTitleNo(String employee_id, String title) throws FindException, SearchException;
//
//	/**
//	 * (반려)문서에 대해 내용으로 검색할 수 있다.
//	 * @param employee_id
//	 * @param content
//	 * @return title
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	List<Document> selectBySearchContentNo(String employee_id, String content) throws FindException, SearchException;
//
//	/**
//	 * (승인)문서에 대해 제목으로 검색할 수 있다.
//	 * @param employee_id
//	 * @param title
//	 * @return 검색 내용
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	List<Document> selectBySearchTitleOk(String employee_id, String title) throws FindException, SearchException;
//
//	/**
//	 * (승인)문서에 대해 내용으로 검색할 수 있다.
//	 * @param employee_id
//	 * @param content
//	 * @return title
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	List<Document> selectBySearchContentOk(String employee_id, String content) throws FindException, SearchException;

}
