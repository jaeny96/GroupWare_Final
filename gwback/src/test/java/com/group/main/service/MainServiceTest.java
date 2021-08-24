package com.group.main.service;

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

class MainServiceTest {
	@Autowired
	MainService service;
	@Test
	void showProfileTest() throws FindException{
		String id = "DEV001";
		Employee emp = service.showProfile(id);
		String expectedPwd = "1";
		assertEquals(expectedPwd, emp.getPassword());
	}

	@Test
	void showLeaveTest() throws FindException{
		String id = "DEV001";
		Leave leave = service.showLeave(id);
		int expectedRemainDays = 10;
		assertEquals(expectedRemainDays, leave.getRemainDays());
	}
	
	@Test
	void selectExpectedDocTest() throws FindException{
		String id = "MSD002";
		List<Document> docList = service.showDocExpected(id);
		int expectedBdListSize = 5;
		assertEquals(expectedBdListSize, docList.size());
	}
	
	@Test
	void showRecentBoardListTest() throws FindException{
		List<Board> bdList = service.showRecentBd();
		int expectedBdListSize = 5;
		assertEquals(expectedBdListSize, bdList.size());
	}
	
	@Test
	void showTodaySkdTest() throws FindException{
		Employee emp = new Employee();
		Department dept = new Department();
		dept.setDepartmentId("MSD");
		emp.setEmployeeId("MSD002");
		emp.setDepartment(dept);
		
		List<Schedule> skdList = service.showTodaySkd(emp);
		int expectedBdListSize = 0;
		assertEquals(expectedBdListSize, skdList.size());
	}
	
	@Test
	void loginTest() throws FindException{
		String id ="CEO001";
		String pwd = "1234";
		
		Employee emp = service.login(id, pwd);
		
		String expectedName = "권보아";
		assertEquals(expectedName, emp.getName());
	}

}
