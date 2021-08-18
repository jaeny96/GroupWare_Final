package com.group.main.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class MainDAOOracleTest {
	@Autowired
	MainDAO dao;
	
	@Test
	void selectByIdTest() throws FindException{
		Employee emp = dao.selectById("MSD002");
		String expectedPwd = "MSD0021234";
		assertEquals(expectedPwd, emp.getPassword());
	}
	
	@Test
	void selectLeaveTest() throws FindException{
		Leave leave = dao.selectLeave("MSD002");
		int expectedRemainDays = 10;
		assertEquals(expectedRemainDays, leave.getRemainDays());
	}

	@Test
	void selectExpectedDocTest() throws FindException{
		String id = "MSD002";
		List<Document> docList = dao.selectDocument(id);
		int expectedBdListSize = 5;
		assertEquals(expectedBdListSize, docList.size());
	}

	@Test
	void selectRecentBoardTest() throws FindException{
		List<Board> bdList = dao.selectBoard();
		int expectedBdListSize = 5;
		assertEquals(expectedBdListSize, bdList.size());
	}

	@Test
	void selectTodaySkdTest() throws FindException{
		Employee emp = new Employee();
		Department dept = new Department();
		dept.setDepartmentId("MSD");
		emp.setEmployeeId("MSD002");
		emp.setDepartment(dept);
		
		List<Schedule> skdList = dao.selectSchedule(emp);
		int expectedBdListSize = 0;
		assertEquals(expectedBdListSize, skdList.size());
	}

}
