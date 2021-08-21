package com.group.approval.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.service.ConfirmDocsService;
import com.group.employee.dto.Employee;


@RestController
@RequestMapping("/apboard/*")
public class ConfirmDocsController {
	
	@Autowired
	private ConfirmDocsService service;
	
	private Logger log = Logger.getLogger(SideDocsController.class);

	//확인, 미확인 구분 
	@GetMapping(value={"/selectcheck/{check}","/selectcheck/{check}/{status}"})
	public List<Document> sekectCheckList(@PathVariable (name="check") Optional<String> optCheck,
			@PathVariable (name="status") Optional<String> optStatus,HttpSession session) {
		
		Map<String,Object> result= new HashMap<String, Object>();
		List<Document> list=new ArrayList<Document>();
		String id="DEV002";
		System.out.println(optCheck.get());
		//System.out.println(optStatus.get());
		try {
			if(optStatus.isPresent()) {
				list =service.findCheckDocs(id,optStatus.get(),optCheck.get());
			}else {
				String status = "";
				list =service.findCheckDocs(id,status,optCheck.get());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}		
		return list;	
	}
	
	//자신의 승인요망 확인 부분
	@GetMapping(value={"/selectmyclick/{docsNo}"})
	public List<Approval> sekectCheckList(@PathVariable (name="docsNo") Optional<String> optDocsNo,HttpSession session) {
		Map<String,Object> result= new HashMap<String, Object>();
		List<Approval> list=new ArrayList<Approval>();
		String id="DEV001";
		try {
			if(optDocsNo.isPresent()) {
				list =service.findDocsMyCheck(id,optDocsNo.get());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}		
		return list;	
	}
	//결재 문서 상세 내용 확인
	
	//코멘트 내용 확인
	@GetMapping(value={"/selectcomments/{docsNo}"})
	public List<Approval> sekectComments(@PathVariable (name="docsNo") Optional<String> optDocsNo,HttpSession session) {
		Map<String,Object> result= new HashMap<String, Object>();
		List<Approval> list=new ArrayList<Approval>();
		try {
			if(optDocsNo.isPresent()) {
				list =service.findByComments(optDocsNo.get());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}		
		return list;	
	}
	
	
}
