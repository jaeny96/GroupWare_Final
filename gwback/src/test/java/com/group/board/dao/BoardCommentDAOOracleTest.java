package com.group.board.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.board.dto.Board;
import com.group.board.dto.BoardComment;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
	      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class BoardCommentDAOOracleTest {

	@Autowired
	private BoardCommentDAOOracle dao;
	private Logger log = Logger.getLogger(BoardCommentDAOOracleTest.class.getName());	

	@Test
	void testSelectAll() throws FindException {
		String bdNo = "BD1";
		List<BoardComment> cmList = dao.selectAll(bdNo);
		int expectedSize = 3;
		log.info(cmList.size());
		assertTrue(expectedSize == cmList.size());
	}
	@Test
	void testInsert() throws Exception {
		String bdNo = "BD1";
		//int cmNo = 4;
		String id = "MSD002";
		String cmContent = "뭐지?";
		
		BoardComment cm = new BoardComment();
		cm.setBdNo(bdNo);
		//cm.setCmNo(cmNo);
		Employee e = new Employee();
		e.setEmployeeId(id);
		cm.setCmWriter(e);
		cm.setCmContent(cmContent);
		dao.insert(cm);
	}
	@Test
	void testDelete() throws RemoveException {
		String bdNo = "BD2";
		int cmNo = 4;
		String id = "MSD002";
		
		BoardComment cm = new BoardComment();
		cm.setBdNo(bdNo);
		cm.setCmNo(cmNo);		
		Employee e = new Employee();
		e.setEmployeeId(id);
		cm.setCmWriter(e);
		dao.delete(cm);
				
	}
}
