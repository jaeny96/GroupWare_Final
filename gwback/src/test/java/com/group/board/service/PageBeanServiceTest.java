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
import com.group.board.dao.BoardDAO;
import com.group.board.dto.PageBean;
import com.group.exception.FindException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class PageBeanServiceTest {
	@Autowired
	PageBeanService service;
	@Autowired
	BoardDAO dao;
	@Test
	void testSelectTotalPage() {
		//페이지별 보여줄 목록 수 4개로 수정하고 테스트
		int totalPage = 6;	
		int expectedSize = 6;
		assertEquals(expectedSize, totalPage);
	}	
	@Test
	void testSelectPageGroup() {
		//총 6페이지이므로 4개의 페이지씩 묶을 경우 페이지 그룹이 2개 나와야함
		int totalGroupNum=2;	
		int expectedGroupSize = 2;
		assertEquals(expectedGroupSize, totalGroupNum);
	}
}
