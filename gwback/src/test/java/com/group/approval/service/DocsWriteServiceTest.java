package com.group.approval.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class DocsWriteServiceTest {
	@Autowired
	DocsWriteService service;

	@Test
	void draftTest() throws AddException{
		String docsNo="test3";
		//문서등록
		Document d = new Document();
		d.setDocumentNo(docsNo);
		DocumentType dt = new DocumentType();
		dt.setDocumentType("지출");
		d.setDocumentStatus(dt);
		Employee ed = new Employee();
		ed.setEmployeeId("DEV001");
		d.setEmployee(ed);
		d.setDocumentTitle(docsNo);
		d.setDocumentContent("test");
		
		//결재자
		List<Approval> approvals = new ArrayList<>();
		Approval ap = new Approval();
		ap.setDocumentNo(docsNo);
		Employee e = new Employee();
		e.setEmployeeId("DEV001");//본인
		ap.setEmployee(e);
		ap.setApStep(0);
		
		Approval ap1 = new Approval();
		ap1.setDocumentNo(docsNo);
		Employee e1 = new Employee();
		e1.setEmployeeId("DEV002");
		ap1.setEmployee(e1);
		ap1.setApStep(1);
			
		approvals.add(ap);
		approvals.add(ap1);
		
		d.setApprovals(approvals);
		//합의자 
		Agreement ag = new Agreement();
		ag.setDocumentNo(docsNo);
		Employee eg = new Employee();
		eg.setEmployeeId("DEV003");
		ag.setEmployee(eg);

		d.setAgreement(ag);
		//참조자 
		Reference r = new Reference();
		r.setDocumentNo(docsNo);
		Employee er = new Employee();
		er.setEmployeeId("DEV002");
		r.setEmployee(er);
		
		d.setReference(r);
		
		service.complete(d);
	}

}
