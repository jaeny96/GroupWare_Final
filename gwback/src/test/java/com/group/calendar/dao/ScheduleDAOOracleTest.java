package com.group.calendar.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.calendar.dao.ScheduleDAO;
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
class ScheduleDAOOracleTest {
	@Autowired
	ScheduleDAO dao;
	@Test
	void SkdListTest() throws FindException{
		
		String skd_id = "MSD002";
		Department dpt = new Department();
		dpt.setDepartmentId("MSD");
		Employee em = new Employee(skd_id, null, dpt, null, null, null, null, null, 1, null);
		List<Schedule>list = dao.skdList(em);
		int expectedSize = 17;
		assertTrue(expectedSize==list.size());
	}
	@Test
	void skdPeriodList() throws FindException{
		String skd_id = "SEC002";
		String sdate = "2021-06-01";
		String edate = "2021-06-30";
		Department dpt = new Department();
		dpt.setDepartmentId("SEC");
		Employee em = new Employee(skd_id, null, dpt, null, null, null, null, null, 1, null);
		List<Schedule> list = dao.skdPeriodList(em, sdate, edate);
		int expectedSize =3;
		assertTrue(expectedSize==list.size());
	}
	@Test
	void skdByTeam() throws FindException{
		String skd_dpt_id = "MSD";
		List<Schedule> list = dao.skdByTeam(skd_dpt_id);
		int expectedSize =9;
		assertTrue(expectedSize==list.size());
	}
	@Test
	void skdPersonal() throws FindException{
		String skd_id = "MSD003";
		List<Schedule> list = dao.skdPersonal(skd_id);
		int expectedSize =3;
		assertTrue(expectedSize==list.size());
	}
	@Test
	void skdByContent() throws FindException{
		String skd_title = "회의";
		String skd_content = "회의";
		Employee em = new Employee();
		em.setEmployeeId("MSD002");
		Schedule sc = new Schedule(em, skd_title, skd_content);
		List<Schedule> list = dao.skdByContent(sc);
		int expectedSize =3;
		assertTrue(expectedSize==list.size());
	}
	@Test
	void skdDetail() throws FindException{
		int skd_no = 1;
		Schedule s = dao.skdDetail(skd_no);
		String expectedContentName = "부산출장";
		assertEquals(expectedContentName, s.getSkdTitle());
	}
	
	@Test
	void insert() throws FindException, AddException, DuplicatedException{
	  String type="출장";
      String id = "MSD002";

      Employee emp = new Employee();
      emp.setEmployeeId(id);
      
      String title = "포천출장";
      String content ="포천회의";
      String start = "2021-08-09 09:00:00";
      Timestamp start_date=Timestamp.valueOf(start);
      String end="2021-08-09 18:00:00";
      Timestamp end_date=Timestamp.valueOf(end);
      String share="p";
      
      Schedule skd = new Schedule();
      ScheduleType st = new ScheduleType();
      st.setSkdType(type);
      skd.setSkdType(st);
      skd.setSkdId(emp);
      skd.setSkdTitle(title);
      skd.setSkdContent(content);
      skd.setSkdStartDate(start_date);
      skd.setSkdEndDate(end_date);
      skd.setSkdShare(share);
      dao.insert(skd);

	}
	@Test
	void update() throws ModifyException {
		  Employee emp = new Employee(); 
	      ScheduleType st = new ScheduleType();
	      int skd_no = 181; 
	      String skd_title = "업데이트테스트4";
	      String skd_skd_content = "";
	      String start = "2021-07-22 15:00:00";
	      String end = "2021-07-22 16:00:00";
	      String skd_share = "p";
	      Timestamp skd_start_date = Timestamp.valueOf(start);
	      Timestamp skd_end_date = Timestamp.valueOf(end);
	
	      String skd_id = "MSD002"; 
	      emp.setEmployeeId(skd_id);
	      String type ="출장";
	      st.setSkdType(type);
	      Schedule s = new Schedule();
	      s.setSkdId(emp); 
	      s.setSkdNo(skd_no);    
	      s.setSkdType(st); 
	      s.setSkdTitle(skd_title);
	      s.setSkdContent(skd_skd_content);
	      s.setSkdStartDate(skd_start_date);
	      s.setSkdEndDate(skd_end_date);
	      s.setSkdShare(skd_share);
	      dao.update(s);


		
	}
	@Test
	void delete() throws FindException, RemoveException{
	Employee emp = new Employee();
	Schedule s = new Schedule();
	  emp.setEmployeeId("MSD002");
	  s.setSkdId(emp);
      s.setSkdNo(225);
      dao.delete(s);

	}
	
}
