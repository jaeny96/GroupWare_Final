package com.group.calendar.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.calendar.dto.Schedule;
import com.group.calendar.dto.ScheduleType;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class ScheduleServiceTest {
	@Autowired
	ScheduleService service;
	@Test
	void findSkdAllTest() throws FindException {
		String skd_id = "MSD003";
		Department dpt = new Department();
		dpt.setDepartmentId("MSD");
		Employee em = new Employee(skd_id, null, dpt, null, null, null, null, null, 1, null);
		List<Schedule> list = service.findSkdAll(em);
		int expectedListSize = 12;
		assertEquals(expectedListSize, list.size());
	}
	
	@Test
	void findSkdTeamTest() throws FindException {
		String dpt = "MSD";
		List<Schedule> list = service.findSkdTeam(dpt);
		int expectedListSize = 9;
		assertEquals(expectedListSize, list.size());
	}
	
	@Test
	void findSkdPersonalTest() throws FindException {
		String skd_id = "MSD003";
		List<Schedule> list = service.findSkdPersonal(skd_id);
		int expectedListSize = 3;
		assertEquals(expectedListSize, list.size());
	}
	
	@Test
	//내용검색이 안됨... why..?
	void findByContentTest() throws FindException {
		String skd_title = "회의";
		String skd_content = "회의";
		Employee em = new Employee();
		em.setEmployeeId("MSD003");
		Schedule s = new Schedule(em, skd_title, skd_content);
		List<Schedule> list = service.findByContent(s);
		int expectedListSize = 3;
		assertEquals(expectedListSize, list.size());
	}
	@Test
	void findByDetailTest() throws FindException {
		int skd_no = 9;
		Schedule s = service.findByDetail(skd_no);
		String expectedSkdTitle = "보고서 작성";
		assertEquals(expectedSkdTitle, s.getSkdTitle());
		
	}
	@Test
	void findByDateTest() throws FindException {
		String skd_id = "SEC002";
		String sdate = "2021-06-01";
		String edate = "2021-06-30";
		Department dpt = new Department();
		dpt.setDepartmentId("SEC");
		Employee em = new Employee(skd_id, null, dpt, null, null, null, null, null, 1, null);
		List<Schedule> list = service.findByDate(em, sdate, edate);
		int expectedListSize = 3;
		assertEquals(expectedListSize, list.size());
		
	}
	@Test
	void addSkdTest() throws AddException, DuplicatedException {
      Schedule s = new Schedule();
      ScheduleType st = new ScheduleType();
      Employee emp = new Employee();
      String start = "2021-07-03 17:00:00";
      String end = "2021-07-03 18:00:00";
      Timestamp skd_start_date = Timestamp.valueOf(start);
      Timestamp skd_end_date = Timestamp.valueOf(end);

		  s.setSkdType(st);
	      s.setSkdId(emp);
	      st.setSkdType("업무");
	      emp.setEmployeeId("CEO001");
	      s.setSkdTitle("서비스테스트");
	      s.setSkdContent("서비스");
	      s.setSkdStartDate(skd_start_date);
	      s.setSkdEndDate(skd_end_date);
	      s.setSkdShare("p");
		  service.addSkd(s);
	}
	@Test
	void modifySkdTest() throws ModifyException {
		 Schedule s = new Schedule();
	     ScheduleType st = new ScheduleType();
	     Employee emp = new Employee();
	     String start = "2021-08-03 17:00:00";
	     String end = "2021-08-03 18:00:00";
	     Timestamp skd_start_date = Timestamp.valueOf(start);
	     Timestamp skd_end_date = Timestamp.valueOf(end);

		 st.setSkdType("업무");
		 s.setSkdType(st);
		 emp.setEmployeeId("CEO001");
		 s.setSkdId(emp);
		 s.setSkdNo(226);
		 s.setSkdTitle("수정테스트");
		 s.setSkdContent("서비스수정");
		 s.setSkdStartDate(skd_start_date);
		 s.setSkdEndDate(skd_end_date);
		 s.setSkdShare("p");
		 service.modifySkd(s);
	}
	@Test
	void deleteSkdTest() throws RemoveException {
		Schedule s = new Schedule();
		Employee emp = new Employee();
        emp.setEmployeeId("CEO001");
		s.setSkdId(emp);
        s.setSkdNo(226);
        service.deleteSkd(s);
	}
}
