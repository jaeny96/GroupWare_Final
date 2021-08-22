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
import com.group.approval.service.ProcessDocsService;
import com.group.employee.dto.Employee;
import com.group.exception.UpdateException;

@RestController
@RequestMapping("/approval/*")
public class ProcessDocsController {
	@Autowired
	private ProcessDocsService service;
	
	private Logger log = Logger.getLogger(ProcessDocsController.class);

	
	//해당 부서 사원 모두 들고온다. 
	@GetMapping("/selectbydep/{depTitle}")
	public List<Employee> selectByDep(@PathVariable (name="depTitle") Optional<String> optDepTitle) {
		List<Employee> list=new ArrayList<>();
		try {
			if(optDepTitle.isPresent()) {
				list = service.findByEmpDep(optDepTitle.get());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}		
		return list;	
	}
	
	//사원 모두 들고온다. 
	@GetMapping("/searchapline")
	public List<Employee> selectAllemp() {
		List<Employee> list=new ArrayList<>();
		try {
			list = service.showAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}		
		return list;	
	}
	
	//참조자는 참조를 승인한다. 
	@PutMapping("/updatere/{docsNo}")
	public Map<String, Object> updateRe(HttpSession session,@PathVariable(name="docsNo") Optional<String> docsNoOpt) {
		Map<String, Object> result = new HashMap<>();
		String id = "DEV002";
		try {
			if(docsNoOpt.isPresent()) {
			service.decisionMyRe(docsNoOpt.get(),id);
			result.put("status", 1);
			}
		} catch (UpdateException e) {
			e.printStackTrace();
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	//결재자 승인한다. 
	@PutMapping("/updateap")
	public Map<String, Object> updateAp(@RequestBody Approval ap) {
		
		Map<String, Object> result = new HashMap<>();
		try {
			service.decisionMyAp(ap);
			result.put("status", 1);
		} catch (UpdateException e) {
			e.printStackTrace();
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	//합의자 승인한다. 
	@PutMapping("/updateag")
	public Map<String, Object> updateAg(@RequestBody Agreement ag) {
		
		Map<String, Object> result = new HashMap<>();
		try {
			service.decisionMyAg(ag);
			result.put("status", 1);
		} catch (UpdateException e) {
			e.printStackTrace();
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
}
