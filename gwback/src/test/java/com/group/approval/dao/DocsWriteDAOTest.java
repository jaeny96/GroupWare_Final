package com.group.approval.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Ref;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
class DocsWriteDAOTest {

	@Autowired
	private DocsWriteDAO dao;  
	private Logger log = Logger.getLogger(DocsWriteDAOTest.class.getName());
	String docsNo ="test33";
	//기안하기 
	@Test
	@Transactional(rollbackFor=AddException.class)
	void testDraft() throws AddException {

		//문서등록
		Document d = new Document();
		d.setDocumentNo(docsNo);
		DocumentType dt = new DocumentType();
		dt.setDocumentType("지출");
		d.setDocumentStatus(dt);
		Employee ed = new Employee();
		ed.setEmployeeId("DEV001");
		d.setEmployeeD(ed);
		d.setDocumentTitle(docsNo);
		d.setDocumentContent("test");
		System.out.println(d);
		dao.draft(d);
		//참조자 
		Reference r = new Reference();
		r.setDocumentNo(docsNo);
		Employee er = new Employee();
		er.setEmployeeId("DEV002");
		r.setEmployee(er);
		dao.draftRe(r);
	}
	
	@Test
	void rr() throws AddException{
		//결재자
//		Approval ap = new Approval();
//		ap.setDocumentNo(docsNo);
//		Employee e = new Employee();
//		e.setEmployeeId("DEV001");//본인
//		ap.setEmployee(e);
//		ApprovalStatus aps = new ApprovalStatus();
//		aps.setApType("승인");
//		ap.setApStatus(aps);
//		ap.setApStep(0);
		
//		Approval ap1 = new Approval();
//		ap1.setDocumentNo(docsNo);
//		Employee e1 = new Employee();
//		e1.setEmployeeId("DEV002");
//		ap1.setEmployee(e1);
//		ApprovalStatus aps1 = new ApprovalStatus();
//		aps1.setApType("승인");
//		ap1.setApStatus(aps1);
//		ap1.setApStep(1);
			
//		dao.draftAp(ap);
//		dao.draftAp(ap1);
		//합의자 
//		Agreement ag = new Agreement();
//		ag.setDocumentNo(docsNo);
//		Employee eg = new Employee();
//		eg.setEmployeeId("DEV003");
//		ag.setEmployee(eg);
//		dao.draftAg(ag);

	}
	
	


	//해당 문서타입, 최대숫자가지고오기 
//	@Test
//	void testSelectByCountMax() throws FindException {
//		String id = "DEV001";
//		int cntAll = dao.chkMaxNum("회람");//DB검색 결과
//		System.out.println(id+"의 전체 문서 개수 : "+ cntAll);
//	}
	



}
