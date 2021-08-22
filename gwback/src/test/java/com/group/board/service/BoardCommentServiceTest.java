package com.group.board.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.group.board.dto.BoardComment;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class BoardCommentServiceTest {
	@Autowired
	BoardCommentService service;
	private Logger log = Logger.getLogger(BoardCommentServiceTest.class.getName());	

	@Test
	void testShowCm() throws Exception {
		String bdNo = "BD2";
		List<BoardComment> cmList = service.showCm(bdNo);
		int expectedSize = 3;
		assertTrue(expectedSize == cmList.size());
	}
	@Test
	void testAddCm() throws Exception {
		String bdNo = "BD2";
		//int cmNo = 4;
		String id = "IFR001";
		String cmContent = "추가테스트";
		
		BoardComment cm = new BoardComment();
		cm.setBdNo(bdNo);
		//cm.setCmNo(cmNo);
		Employee e = new Employee();
		e.setEmployeeId(id);
		cm.setCmWriter(e);
		cm.setCmContent(cmContent);
		service.addCm(cm);
	}
	@Test
	void testRemoveCm() throws Exception {
		String bdNo = "BD3";
		int cmNo = 3;
		String id = "SVC008";
		
		BoardComment cm = new BoardComment();
		cm.setBdNo(bdNo);
		cm.setCmNo(cmNo);		
		Employee e = new Employee();
		e.setEmployeeId(id);
		cm.setCmWriter(e);
		service.removeCm(cm);
	}
}
