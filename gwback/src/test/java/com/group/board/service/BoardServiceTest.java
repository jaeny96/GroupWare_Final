package com.group.board.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.board.dto.Board;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class BoardServiceTest {
	@Autowired
	BoardService service;

	@Test
	void testShowBdAll() throws Exception {
		int currentPage = 2;
		int expectedSize = 10;
		List<Board> bdlist = service.showBdAll(currentPage);
		assertEquals(expectedSize, bdlist.size());

	}

	@Test
	void testSearchBd() throws Exception {
		int expectedSize = 5;
		String category = "name";
		String word = "권보아";
		List<Board> bdlist = service.searchBd(category, word);
		assertTrue(expectedSize == bdlist.size());
	}

	@Test
	void testShowBdDetail() throws Exception {
		String bdNo = "BD1";
		Board bd = service.showBdDetail(bdNo);		
		String expectedTitleName = "안녕하세요";
		
		assertEquals(expectedTitleName, bd.getBdTitle());
	}

	@Test
	void testAddBd() throws Exception {
		//String bdNo = "BD13";
		String id = "DEV001";
		String bdTitle = "서비스테스트";
		String bdContent = "서비스테스트입니다";		
		Board bd = new Board();
		//bd.setBdNo(bdNo);
		Employee e = new Employee();
		e.setEmployeeId(id);
		bd.setWriter(e);
		bd.setBdTitle(bdTitle);
		bd.setBdContent(bdContent);
		service.addBd(bd);
	}

	@Test
	void testModifyBd() throws Exception {
		String bdNo = "BD8";
		String id = "CEO001";
		String bdTitle = "오늘저녁";
		String bdContent = "사과떡볶이";
		
		Employee e = new Employee();
		e.setEmployeeId(id);
		Board bd = new Board();
		bd.setBdNo(bdNo);
		bd.setBdTitle(bdTitle);
		bd.setBdContent(bdContent);
		bd.setWriter(e);
		service.modifyBd(bd);;		
	}

	@Test
	void removeBd() throws Exception {
		String bdNo = "BD12";
		String id = "CEO001";
		
		Employee e = new Employee();
		e.setEmployeeId(id);
		Board bd = new Board();
		bd.setBdNo(bdNo);
		bd.setWriter(e); //writer에 id값 set해줌
		service.removeBd(bd);
	}
}
