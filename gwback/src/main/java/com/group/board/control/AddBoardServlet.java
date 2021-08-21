//package com.group.board.control;
//
//import java.io.IOException;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.group.board.dto.Board;
//import com.group.board.service.BoardService;
//import com.group.employee.dto.Employee;
//import com.group.exception.AddException;
//
///**
// * Servlet implementation class AddBoardServlet
// */
//public class AddBoardServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	//게시글 등록
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String addBdWriter = request.getParameter("addBdWriter");
//		String addBdWriterId = request.getParameter("addBdWriterId");
//		String addBdTitle = request.getParameter("addBdTitle");
//		String addBdContent = request.getParameter("addBdContent");
//		BoardService service;
//		ServletContext sc = getServletContext();
//		BoardService.envProp = sc.getRealPath(sc.getInitParameter("env"));
//		service = BoardService.getInstance();
//
//		try {
//			Employee emp = new Employee();
//			emp.setEmployeeId(addBdWriterId);
//			emp.setName(addBdWriter);
//			Board bd = new Board();
//			bd.setBdTitle(addBdTitle);
//			bd.setBdContent(addBdContent);
//			bd.setWriter(emp);
//			service.addBd(bd);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
//	}
//
//}