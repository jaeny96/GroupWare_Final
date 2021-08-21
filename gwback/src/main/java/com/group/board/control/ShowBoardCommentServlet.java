//package com.group.board.control;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.group.board.dto.BoardComment;
//import com.group.board.service.BoardCommentService;
//import com.group.exception.FindException;
//
///**
// * Servlet implementation class ShowBoardCommentServlet
// */
//public class ShowBoardCommentServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	//해당 게시글의 댓글들 반환
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String bdNo = request.getParameter("bdNo");
//		BoardCommentService service;
//		ServletContext sc = getServletContext();
//		BoardCommentService.envProp = sc.getRealPath(sc.getInitParameter("env"));
//		service = BoardCommentService.getInstance();
//
//		try {
//			List<BoardComment> cmList = service.showCm(bdNo);
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
//			String jsonStr = mapper.writeValueAsString(cmList);
//			response.setContentType("application/json;charset=utf-8");
//			response.getWriter().print(jsonStr);
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
