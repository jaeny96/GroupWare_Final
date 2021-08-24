package com.group.approval.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.group.approval.dao.ConfirmDocsDAO;
import com.group.approval.dao.DocsWriteDAO;
import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public class DocsWriteService {
	private DocsWriteDAO dao;
	private static DocsWriteService service;
//	private static DocsWriteService service=new DocsWriteService();
//	private static String envProp="classes.prop";	//back에서만 테스트용
	public static String envProp;	//front테스트용

	private DocsWriteService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
//			env.load(new FileInputStream("classes.prop"));
			String className = env.getProperty("DocsWriteDAO");
			System.out.println(className);
			Class c = Class.forName(className);
			dao = (DocsWriteDAO) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DocsWriteService getInstance() {
		if (service == null) {
			service = new DocsWriteService();
		}
		return service;
	}

	/**
	 * 결재문서 기안
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void complete(Document d) throws AddException {
		dao.draft(d);
	}
	/**
	 * 결재문서의 결재자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void completeApRegister(Approval ap) throws AddException {
		dao.draftAp(ap);
	}
	/**
	 * 결재문서의 합의자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void completeAgRegister(Agreement ag) throws AddException {
		dao.draftAg(ag);
	}
	/**
	 * 결재문서의 참조자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void completeReRegister(Reference re) throws AddException {
		dao.draftRe(re);
	}

	/**
	 * 사원이름 리스트 조회
	 * 
	 * @param name
	 * @throws FindException
	 */
	public List<Employee> staff(String word) throws FindException {
		return dao.searchByName(word);
	}

	/**
	 * 사원 전체를 조회한다
	 * @return 사원 전체 목록
	 * @throws FindException
	 */
	public List<Employee> showAll() throws FindException {
		return dao.searchApLineStaff();
	}
	
	public int chkMaxNum(String document_type) throws FindException{
		return dao.chkMaxNum(document_type);
	}
	
	public static void main(String[] args) {
		   DocsWriteService service = DocsWriteService.getInstance();

	}
}
