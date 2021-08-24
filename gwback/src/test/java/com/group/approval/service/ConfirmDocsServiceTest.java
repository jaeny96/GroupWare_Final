package com.group.approval.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.exception.FindException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
class ConfirmDocsServiceTest {

	@Autowired
	SideDocsService service;
	
	@Test
	void testFindCntAll() throws FindException{
		assertNotNull(service);
		int cntAll = service.findCntAll("DEV001");
		assertEquals(6, cntAll);
				
	}

}
