package com.group.approval.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.SearchException;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
class ConfirmDocsDAOTest {

	@Autowired
	private ConfirmDocsDAO dao;  
	private Logger log = Logger.getLogger(ConfirmDocsDAOTest.class.getName());
	
//	@Test
//	void testSelectByCheck() throws FindException {
//		log.error("testSelectByNo메서드호출 error");
//		String id = "DEV001";
//		String status="승인";
//		List<Document> list = dao.selectByCheck(id,"승인","check");//DB검색 결과
//		System.out.println(id+"의 "+status+": "+ list);
//		System.out.println(list.size());
//	}
//	
//
//	@Test
//	void testSelectByMyClick() throws FindException {
//		log.error("testSelectByNo메서드호출 error");
//		String id = "DEV001";
//		String docsNo="LE-휴가-20210624-0001";
//		List<Approval> list = dao.selectByMyClick(id,docsNo);//DB검색 결과
//		System.out.println(id+"의 "+ list);
//		System.out.println(list.size());
//	}
//	

//	@Test
//	void testSelectByDocsDetail() throws FindException {
//		log.error("testSelectByNo메서드호출 error");
//		String docsNo="LE-휴가-20210624-0001";
//		Document d = dao.selectByDocsDetail(docsNo);//DB검색 결과
//		List<Approval> apps = d.getApprovals();
//		int expectedAppsSize = 4;
//		assertTrue(expectedAppsSize==apps.size());
//		
//		for(Approval ap: apps) {
//		Employee id = new Employee();
//		id.setEmployeeId(ap.getEmployee().getEmployeeId());
//		String apId =id.getEmployeeId();
//		String apName =ap.getEmployee().getName();
//		String apType=ap.getApStatus().getApType();
//		int apStep = ap.getApStep();
//		Date apDate = ap.getApDate();
//		//자동 매핑되려면 기본 db의 컬럼값과 같게해야된다
//		//collection-> assertion은  has-a관계설정
//		//봐야할 것 !! dto수정하기 
//		System.out.println(apId+"/"+apName+"/"+apType+"/"+apStep+"/"+apDate);
//		}
//		
//	}
//	
//	@Test
//	void testSelectByComments() throws FindException {
//		log.error("selectByComments메서드호출 error");
//		String docsNo="LE-휴가-20210624-0001";
//		List<Approval> list = dao.selectByComments(docsNo);//DB검색 결과
//		System.out.println(docsNo+"의 "+ list);
//		System.out.println(list.size());
//	}
//	

	
	@Test
	void testSelectBySearch() throws SearchException {
		log.error("testSelectBySearch메서드호출 error");
		//id,status,search
		String id = "DEV001";
		List<Document> list = dao.selectBySearchAll(id,"content","휴가");//DB검색 결과
		System.out.println(id+"의 검색값 : "+ list);
		System.out.println(list.size());
	}
}
