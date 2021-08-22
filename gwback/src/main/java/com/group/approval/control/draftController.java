package com.group.approval.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.service.DocsWriteService;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.main.control.MainController;

@RestController
@RequestMapping("/approval/draft/*")
public class draftController {
	private Logger log = Logger.getLogger(MainController.class.getClass());

	@Autowired
	private DocsWriteService service;

	@PostMapping(value = "/{type}")
	public Map<String, Object> draft(@PathVariable String type, @RequestBody Document d) {
		Map<String, Object> map = new HashMap<>();
		try {
			int docNum = service.chkMaxNum(d.getDocumentStatus().getDocumentType())+1;
			String docNumber = modiDocNumber(docNum);
			String docsNo = type+"-"+d.getDocumentStatus().getDocumentType()+"-"+today()+"-"+docNumber;
			
			d.setDocumentNo(docsNo);
			for(Approval ap : d.getApprovals()) {
				ap.setDocumentNo(docsNo);
			}
			if(d.getAgreement()!=null) {
				d.getAgreement().setDocumentNo(docsNo);				
			}
			if(d.getReference()!=null) {
				d.getReference().setDocumentNo(docsNo);
			}
			
			System.out.println(d);
			service.complete(d);
			map.put("status", 1);
		} catch (FindException | AddException e ) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	public String today() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();

		Date dateObj = calendar.getTime();
		String formattedDate = sdf.format(dateObj);
		return formattedDate;
	}

	public String modiDocNumber(int docNum) {
		String result = "";
		if (docNum < 10) {
			result = "000" + docNum;
		} else if (docNum < 100) {
			result = "00" + docNum;
		} else if (docNum < 1000) {
			result = "0" + docNum;

		} else {
			result = "" + docNum;
		}
		return result;
	}
	
	@GetMapping("/apLine")
	public Object showDept(){
		Map<String, Object> map = new HashMap<>();
		try {
			List<Department> deptList = service.deptList();
			return deptList;
		} catch (FindException e ) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
			return map;
		}
	}

	@GetMapping(value="/apLine/{deptId}")
	public Object showEmpByDept(@PathVariable String deptId){
		Map<String, Object> map = new HashMap<>();
		try {
			List<Employee> empList = service.empList(deptId);
			return empList;
		} catch (FindException e ) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
			return map;
		}
	}
}
