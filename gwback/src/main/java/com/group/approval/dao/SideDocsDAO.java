package com.group.approval.dao;

import java.util.List;
import java.util.Map;

import com.group.approval.dto.Document;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.employee.dto.Employee;

public interface SideDocsDAO {

	/**
	 * (전체)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @param id
	 * @throws FindException
	 * 
	 */
	int selectByCountAll(String employee_id) throws FindException;
	
	/**
	 * (진행)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @return 전체 사이드바 목록 개수 
	 * @param id
	 * @throws FindException
	 * 
	 */
	int selectByCountWait(String employee_id) throws FindException;
	
	/**
	 * (승인)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @return 승인 사이드바 목록 개수  
	 * @param id
	 * @throws FindException
	 * 
	 */
	int selectByCountOk(String employee_id) throws FindException;
	
	/**
	 * (반려)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @return 반려 사이드바 목록 개수 
	 * @param id
	 * @throws FindException
	 * 
	 */
	int selectByCountNo(String employee_id) throws FindException;
	
	 /**
	  * (전체)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	  * @return 전체 문서 목록 
	  * @param id
	  * @throws FindException
	  */
	 List<Document> selectByListAll(String employee_id) throws FindException;
	 
	 
	 /**
	  * (승인/대기/반려)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	  * @return 승인/대기/반려 문서 목록
	  * @param id,status
	  * @throws FindException
	  */
	 List<Document> selectByListStatus(String id,String status) throws FindException;
	 
	
}
