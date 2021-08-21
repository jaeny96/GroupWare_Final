package com.group.approval.control;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.service.ConfirmDocsService;
import com.group.approval.service.DocsWriteService;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;

@RestController
@RequestMapping("/apboard/*")
public class DocsWriteController {

	@Autowired
	private DocsWriteService service;
	
	private Logger log = Logger.getLogger(SideDocsController.class);

	@PostMapping("/draft")
	public Map<String, Object> write(@RequestBody Document d) {
		Map<String, Object> result = new HashMap<>();
		try {
			//-->session의 loginInfo속성으로 차후 변경 시작
	
//			d.setDocumentNo("test");
			DocumentType dt = new DocumentType();
//			dt.setDocumentType("지출");
//			d.setDocumentStatus(dt);
			
			Employee e = new Employee();
			e.setEmployeeId("DEV001");//loginInfo로 저장하기 
			d.setEmployeeD(e);
			
//			d.setDocumentTitle("test");
//			d.setDocumentContent("test");
//			
			//-->session의 loginInfo속성으로 차후 변경 끝
			
			service.complete(d);
			result.put("status", 1); 
		} catch (AddException e) {
			e.printStackTrace();
			result.put("status", 0); //실패
			result.put("msg", e.getMessage());
		}
		return result;
	}
}
