package com.group.approval.dao;

import static org.junit.jupiter.api.Assertions.*;


import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.ContextConfiguration;

import com.group.exception.FindException;


@ExtendWith(SpringExtension.class)//을 하면 ?  WebApplicationContext 객체(=스프링 웹 컨테이너)가 생성이 된다. 
//웹 스프링 컨테이너 시작 !
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})//얘네들을 지정해주면
//이젠 스프링 웹 컨테이너가 얘네들을 이용해서 bean객체가 관리가 됨
//이젠 서버 없이도 스프링을 테스트할 수 있음
class SideDocsDAOOracleTest {

	@Autowired
	private SideDocsDAO dao; //이곳에 static을 하면 nullpointException이 남 
	private Logger log = Logger.getLogger(SideDocsDAOOracleTest.class.getName());
	//import org.apache.log4j.Logger; 로 import하기 
	
	
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
