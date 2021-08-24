package com.group.board.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.group.board.dto.Board;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
	      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class BoardDAOOracleTest {
   //private Logger log = Logger.getLogger(ProductController.class.getName());

	@Autowired
	//@Qualifier("boardDAO")
	private BoardDAOOracle dao;
	private Logger log = Logger.getLogger(BoardDAOOracleTest.class.getName());	
	@Test
	void testSelectAll() throws Exception {
		int expectedSize = 4;
		List<Board> bdlist = dao.selectAll();
		log.info(bdlist.size());
		assertTrue(expectedSize == bdlist.size());
	}

	@Test
	void testSelectAllPage() throws Exception {
		int currentPage = 1;
		int expectedSize = 4;
		List<Board> bdlist = dao.selectAll(currentPage);
		log.info(bdlist.size());
		assertTrue(expectedSize == bdlist.size());
	}
	
	@Test
	void testSelectByWord() throws Exception {
		int expectedSize = 2;
		String category = "name";
		String word = "권보아";
		List<Board> bdlist = dao.selectByWord(category, word);
		log.info(bdlist.size());
		assertTrue(expectedSize == bdlist.size());
		
	}
	
	@Test
	void testSelectBdInfo() throws FindException {
		String bdNo = "BD1";
		Board bd = dao.selectBdInfo(bdNo);		
		String expectedTitleName = "수정해보자";
		
		assertEquals(expectedTitleName, bd.getBdTitle());

	}
	@Test
	void testInsert() throws Exception {
		String id = "DEV005";
		String bdTitle = "Oracle테스트";
		String bdContent = "Oracle테스트입니다";
		
		Board bd = new Board();
		Employee e = new Employee();
		e.setEmployeeId(id);
		bd.setWriter(e);
		bd.setBdTitle(bdTitle);
		bd.setBdContent(bdContent);
		dao.insert(bd);
	}

	@Test
	void testUpdate() throws ModifyException {
		String bdNo = "BD3";
		String id = "DEV005";
		//String bdTitle = "수정테스트";
		//String bdContent = "수정테스트본문";
		
		Employee e = new Employee();
		e.setEmployeeId(id);
		Board bd = new Board();
		bd.setBdNo(bdNo);
		//bd.setBdTitle(bdTitle);
		//bd.setBdContent(bdContent);
		bd.setWriter(e);
		dao.update(bd);
	}
	@Test
	void testDelete() throws Exception {
		String bdNo = "BD6";
		String id = "CEO001";
		
		Employee e = new Employee();
		e.setEmployeeId(id);
		Board bd = new Board();
		bd.setBdNo(bdNo);
		bd.setWriter(e); //writer에 id값 set해줌
		dao.delete(bd);
		
	}
}
