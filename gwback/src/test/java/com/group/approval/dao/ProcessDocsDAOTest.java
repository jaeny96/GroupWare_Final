package com.group.approval.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

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
import com.group.exception.UpdateException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
class ProcessDocsDAOTest {

	@Autowired
	private ProcessDocsDAO dao;  
	private Logger log = Logger.getLogger(ProcessDocsDAOTest.class.getName());

	@Test
	void testSelectByDep() throws FindException{
		String depTitle = "경영지원실";
		List<Employee> emp = new ArrayList<>();
		emp = dao.searchByDep(depTitle);
		System.out.println(emp);
	}
	
	@Test
	void testUpdateRe() throws UpdateException{
		dao.updateReference("test1", "DEV002");
	}

	@Test
	void testUpdateAp() throws UpdateException{
		Approval ap = new Approval();
		ap.setApComment("SESESES");
		ap.setDocumentNo("AC-품의-20210627-0002");
		ApprovalStatus aps = new ApprovalStatus();
		aps.setApType("반려");
		ap.setApStatus(aps);
		Employee e = new Employee();
		e.setEmployeeId("DEV001");
		ap.setEmployee(e);
		dao.updateApproval(ap);
	}

}
