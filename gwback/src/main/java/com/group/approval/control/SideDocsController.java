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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.approval.dto.Document;
import com.group.approval.service.SideDocsService;


@RestController
@RequestMapping("/apboard/*")
public class SideDocsController {

	@Autowired
	private SideDocsService service;
	
	private Logger log = Logger.getLogger(SideDocsController.class);

	@GetMapping("/sidebar")
	public List<Integer> sideBarCnt(HttpSession session) {
		List<Integer> apCntList=new ArrayList<Integer>();
		try {
			//String id= session.getAttribute("id").toString();//나중에 login받아오는거 보고 바꾸기
			String id="DEV001";
			apCntList.add(0, service.findCntAll(id));
			apCntList.add(1, service.findCntWait(id));
			apCntList.add(2, service.findCntOk(id));
			apCntList.add(3, service.findCntNo(id));
			log.error("-------------------------------"+apCntList.toString());
		
		} catch (Exception e) {
			log.error(e.getMessage());
		}		
		return apCntList;	
	}
	
	@GetMapping(value={"/selectdocs","/selectdocs/{status}"})
	public List<Document> sideBarList(@PathVariable (name="status") Optional<String> optStatus,HttpSession session) {
		
		Map<String,Object> result= new HashMap<String, Object>();
		List<Document> list=new ArrayList<Document>();
		String id="DEV001";
		try {
			if(optStatus.isPresent()) {
				list =service.findDocsStatus(id,optStatus.get());
				System.out.println(list);
			}else {
				list =service.findDocs(id);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}		
		return list;	
	}
	

}
