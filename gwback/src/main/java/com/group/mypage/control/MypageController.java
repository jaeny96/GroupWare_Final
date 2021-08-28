package com.group.mypage.control;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dto.EmployeeLeave;
import com.group.mypage.service.EmployeeLeaveService;

@RestController
@RequestMapping("/mypage/*")
public class MypageController {

	@Autowired
	private EmployeeLeaveService service;
	
	@Autowired
	private ServletContext servletContext;

	private Logger log = Logger.getLogger(MypageController.class);
	
	//자신의 모든 정보 조회
	@GetMapping("/detail")
	public Object showEmpDetail(HttpSession session){
		Map<String, Object> map = new HashMap<>();
		String id = (String) session.getAttribute("id");		
	try {
		EmployeeLeave empLeave = service.showDetail(id);
		map.put("responseData", empLeave);
		return map;
	} catch (FindException e) {
		map.put("status", -1);
		e.printStackTrace();
		return map;
	}	
	
	
}

	//핸드폰번호 변경
	@PutMapping("/updatePhone")
	public Map<String, Object> modifyEmp(HttpSession session, 
			@RequestBody Employee emp){
		String id = (String) session.getAttribute("id");
		Employee empToData = new Employee();
		Map<String, Object> map = new HashMap<>();
		try {

			String modiPhone = emp.getPhoneNumber();
			empToData.setEmployeeId(id);
			empToData.setPhoneNumber(modiPhone);
			System.out.println(modiPhone);
		
			service.modify(empToData);
			map.put("status", 1);
			return map;
		} catch (ModifyException e) {
			map.put("status", -1);
			e.printStackTrace();
			return map;
		}
	}
	
	//핸드폰번호, 비밀번호 변경
	@PutMapping("/updatePwd")
	public Map<String, Object> modifyPwd(HttpSession session, 
			@RequestBody Employee emp){
		String id = (String) session.getAttribute("id");
		String modiPwd = emp.getPassword();
		System.out.println(modiPwd);
		Map<String, Object> map = new HashMap<>();
		try {
			Employee empToData = new Employee();

			empToData.setEmployeeId(id);
			empToData.setPassword(modiPwd);
			service.modify(empToData);
			map.put("status", 1);
		
			return map;
		} catch (ModifyException e) {
			map.put("status", -1);
			e.printStackTrace();
			return map;
		}
	}
	
	@RequestMapping("/a")
	public void test() {
		log.info("a");
	}
	@PostMapping("/updateProfile")
	public void profileMapping(
			@RequestPart MultipartFile profileFile,
			HttpSession session
			) {
		//업로드되는 프로필 사진의 이름은 id와 같도록 한다. 
		String fileName = (String) session.getAttribute("id");
		System.out.println("id"+fileName);
		//String fileName = "1";
		log.error("profileFile"+profileFile.getSize());
		//이게 있나? 
		String uploadPath = servletContext.getRealPath("upload");
		
		log.error("업로드 실제경로"+uploadPath);
		
		//파일 경로가 존재하지 않다면
		if(! new File(uploadPath).exists()) {
			log.error("업로드 실제 경로 생성" + uploadPath);
			new File(uploadPath).mkdir();
		}
		//받아온 파일이 null이 아니라면
		if(profileFile!=null) {
			//오리지널 이름 
			String profileFileName = profileFile.getOriginalFilename();
			
			//확장자 따로 떼어내기
			String[] extensionArray= profileFileName.split("[.]");
			String extension = extensionArray[1];			
					
			
			//System.out.println("파일의 오리지널 이름"+profileFileName);
//			System.out.println(profileFileName);
//			System.out.println(extensionArray.length +" extensionArray.length");
			
			//확장자 불러오기 
			//System.out.println("extension"+extension);
			
			//파일 이름이 ""가 아니고, 바이트 사이즈가 0이 아니라면
			if(!"".equals(profileFileName)&&profileFile.getSize()!=0) {
				System.out.println("파일이름"+profileFileName + " 사이즈: "+profileFile.getSize());
			
				//파일의 경로와 파일의 이름 
				File file = new File(uploadPath, fileName+"."+extension);
				log.error("uploadPath "+uploadPath+ "fileName" +"." + extension);
				//이런식으로 별도 파일(upload)에다가 하나 더 파일을 복사해놓고, javascript에서 계속 사진을 받아오는 방법이 있다 
				//File backup = new File("C:\\Programming_kms_C\\GroupWare_Final\\gwback\\src\\main\\webapp\\upload",fileName);
					
				
				try {
					FileCopyUtils.copy(profileFile.getBytes(), file);
				//	FileCopyUtils.copy(profileFile.getBytes(), backup);
					log.error("파일 업로드 완료");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
