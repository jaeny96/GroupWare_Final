package com.group.approval.dao;

import static org.junit.jupiter.api.Assertions.*;


import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.ContextConfiguration;

import com.group.exception.FindException;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
class SideDocsDAOOracleTest {

	@Autowired
	private SideDocsDAO dao;  
	private Logger log = Logger.getLogger(SideDocsDAOOracleTest.class.getName());
	
	
	@Test
	void testSelectByCountAll() throws FindException {
		
		log.error("testSelectByNo메서드호출 error");
		String id = "DEV001";
		int cntAll = dao.selectByCountAll(id);//DB검색 결과
		System.out.println(id+"의 전체 문서 개수 : "+ cntAll);
		int expectedCntAll =6;//예상

		assertEquals(expectedCntAll, cntAll);
	}
	
	


}
