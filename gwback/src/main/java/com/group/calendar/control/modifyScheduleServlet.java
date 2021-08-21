package com.group.calendar.control;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group.calendar.dto.Schedule;
import com.group.calendar.dto.ScheduleType;
import com.group.calendar.service.ScheduleService;
import com.group.employee.dto.Employee;
import com.group.exception.ModifyException;

/**
 * Servlet implementation class modifyScheduleServlet
 */
public class modifyScheduleServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;       
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      HttpSession session = request.getSession();
      String targetId = session.getAttribute("id").toString();
      String skdUpdateType = request.getParameter("calendarType");
      String skdUpdateTitle = request.getParameter("title");
      Timestamp skdUpdateStart= Timestamp.valueOf(request.getParameter("start")+":00");
      Timestamp skdUpdateEnd = Timestamp.valueOf(request.getParameter("end")+":00");
      String skdUpdateContent = request.getParameter("content");
      String skdUpdateShare = request.getParameter("teamOrPersonal");
      int skdUpdateNo = Integer.parseInt(request.getParameter("skd_no"));

      System.out.println("targetId :"+targetId);
      System.out.println("type: "+skdUpdateType);
      System.out.println("title : "+skdUpdateTitle);
      System.out.println("start : "+request.getParameter("start")+":00");
      System.out.println("end : "+request.getParameter("end")+":00");
      System.out.println("content : "+ skdUpdateContent);
      System.out.println("share : "+skdUpdateShare);
      System.out.println("skd_no :"+skdUpdateNo);
      
      ScheduleService service;
      ServletContext sc = getServletContext();
      ScheduleService.envProp = sc.getRealPath(sc.getInitParameter("env"));
      service = ScheduleService.getInstance();
      

      try {
         Schedule s = new Schedule();
         Employee emp = new Employee();
         ScheduleType st = new ScheduleType();
         emp.setEmployeeId(targetId);
         st.setSkdType(skdUpdateType);
         
         s.setSkdId(emp);
         s.setSkdType(st);
         s.setSkdTitle(skdUpdateTitle);
         s.setSkdStartDate(skdUpdateStart);
         s.setSkdEndDate(skdUpdateEnd);
         s.setSkdContent(skdUpdateContent);
         s.setSkdShare(skdUpdateShare);
         s.setSkdNo(skdUpdateNo);
         service.modifySkd(s);
         System.out.println("modifyScheduleServlet 일정이 변경되었습니다 ");
      } catch (ModifyException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   

}