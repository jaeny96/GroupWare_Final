package com.group.board.control;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.board.service.BoardCommentService;

@RestController
@RequestMapping("/boardcomment/")
public class BoardCommentController {
	private Logger log = Logger.getLogger(BoardCommentController.class.getClass());

	@Autowired
	private BoardCommentService service;
}
