package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public interface DocsWriteDAO {
	/**
	 * 해당 문서 종류의 최고 숫자를 출력한다
	 * @param document_type 문서 종류
	 * @return 해당 문서 종류의 최고 숫자
	 * @throws FindException
	 */
	int chkMaxNum(String document_type) throws FindException;
	/**
	 *  사용자는 작성한 문서를 기안한다
	 * @param d
	 * @throws AddException
	 */
	void draft(Document d) throws AddException;
	/**
	 *  문서 기안 시 결재자를 등록한다
	 * @param ap
	 * @throws AddException
	 */
	void draftAp(Approval ap) throws AddException;
	/**
	 * 문서 기안 시 합의자를 등록한다
	 * @param ag
	 * @throws AddException
	 */
	void draftAg(Agreement ag) throws AddException;
	/**
	 * 문서 기안 시 참조자를 등록한다
	 * @param re
	 * @throws AddException
	 */
	void draftRe(Reference re) throws AddException;
}
