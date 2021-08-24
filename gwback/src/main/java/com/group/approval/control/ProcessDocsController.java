package com.group.approval.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Reference;
import com.group.approval.service.ProcessDocsService;
import com.group.employee.dto.Employee;
import com.group.exception.UpdateException;

@RestController
@RequestMapping("/approval/*")
public class ProcessDocsController {
	@Autowired
	private ProcessDocsService service;
	
	private Logger log = Logger.getLogger(ProcessDocsController.class);

	
	//참조자는 참조를 승인한다. 
	@PutMapping("/updatere")
	public Map<String, Object> updateRe(@RequestBody Reference re) {
		Map<String, Object> result = new HashMap<>();
		try {
			service.decisionMyRe(re);
			result.put("status", 1);
		} catch (UpdateException e) {
			e.printStackTrace();
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	//결재자 승인한다. 
	@PutMapping("/updateap/{status}")
	public Map<String, Object> updateAp(@RequestBody Approval ap,@PathVariable(name="status") Optional<String> statusOpt) {
		
		Map<String, Object> result = new HashMap<>();
		try {
			if(statusOpt.isPresent()) {
				service.decisionMyAp(ap,statusOpt.get());
				result.put("status", 1);
			}
		} catch (UpdateException e) {
			e.printStackTrace();
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	//합의자 승인한다. 
	@PutMapping("/updateag/{status}")
	public Map<String, Object> updateAg(@RequestBody Agreement ag,@PathVariable(name="status") Optional<String> statusOpt) {
		
		Map<String, Object> result = new HashMap<>();
		try {
		System.out.println("합의자 "+ag);
		System.out.println(statusOpt.get());
			if(statusOpt.isPresent()) {
				service.decisionMyAg(ag,statusOpt.get());
				result.put("status", 1);
			}
		} catch (UpdateException e) {
			e.printStackTrace();
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	
}
