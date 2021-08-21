package com.group.board.control;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.board.service.BoardService;

@RestController
@RequestMapping("/board/")
public class BoardController {
	private Logger log = Logger.getLogger(BoardController.class.getClass());

	@Autowired
	private BoardService service;
	
	
}
