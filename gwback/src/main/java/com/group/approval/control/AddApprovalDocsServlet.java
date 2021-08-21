package com.group.approval.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.approval.service.DocsWriteService;
import com.group.employee.dto.Employee;

/**
 * Servlet implementation class AddApprovalDocsServlet
 */
public class AddApprovalDocsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//받아온 데이터 설정
		String addApDocsType = request.getParameter("addApDocsType").trim();//문서유형
		String addApWriterId = request.getParameter("addApWriterId");//문서아이디
		String addApDocsTitle = request.getParameter("addApDocsTitle");//문서 제목
		String addApDocsContent = request.getParameter("addApDocsContent");//문서 내용
		
		String[] addApLineEmpIdArr = request.getParameterValues("addApLineEmpId[]");//결재 관련 id들
		String[] addApLineStepArr = request.getParameterValues("addApLineStep[]");//결재 관련 단계들
		
		String addAgLineEmpId = request.getParameter("addAgLineEmpId");//합의자 id
		String addReLineEmpId = request.getParameter("addReLineEmpId");//참조자 id

		DocsWriteService service;
		ServletContext sc = getServletContext();
		DocsWriteService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = DocsWriteService.getInstance();
		String docTypeCode = chkDocTypeCode(addApDocsType);//문서  타입관련 코드를 확인한다. 

		try {
			int docNum = service.chkMaxNum(addApDocsType)+1;//마지막 기안한 번호를 들고온다. -> 서비스 처리
			String docNumber = modiDocNumber(docNum);//번호를 수정한다 
			System.out.println(docTypeCode+"-"+addApDocsType+"-"+today()+"-"+docNumber);//BC-연락-20210627-0004
			String addApDocsNo=docTypeCode+"-"+addApDocsType+"-"+today()+"-"+docNumber;//진짜 문서번호를 생성한다. : 문서타입(영어) +문서타입(한글)+ 날짜+ 생성번호   

			//문서내용 셋팅하기 
			Document document = new Document();
			document.setDocument_no(addApDocsNo);//위에서 만든 문서내용 셋팅
			document.setDocument_title(addApDocsTitle);//문서 제목
			document.setDocument_content(addApDocsContent);//문서 내용
			DocumentType dtype = new DocumentType();
			dtype.setDocument_type(addApDocsType);//받아왔던 문서타입
			document.setDocument_type(dtype);
			Employee emp = new Employee();
			emp.setEmployee_id(addApWriterId);//작성자 id -> 로그인 id
			document.setEmployee(emp);
			
			service.complete(document);//기안하기 -> 서비스

			for (int i = 0; i < addApLineEmpIdArr.length; i++) {//결재 길이 설정동안,,,
				if("staffOne".equals(addApLineEmpIdArr[i]) || "staffTwo".equals(addApLineEmpIdArr[i]) ||"staffThree".equals(addApLineEmpIdArr[i])) {
					System.out.println(i+"번째 결재자가 없습니다");
				}else {
					//결재자 
					Approval approval = new Approval();
					Document apDocNo = new Document();
					apDocNo.setDocument_no(addApDocsNo);//만든 문서번호 저장
					approval.setDocument_no(apDocNo);
					Employee apEmp = new Employee();
					apEmp.setEmployee_id(addApLineEmpIdArr[i]);//결재자 관련 id넣기 
					approval.setEmployee_id(apEmp);
					approval.setAp_step(Integer.parseInt(addApLineStepArr[i]));//결재 단계 넣기 
					if(i==0) {//0번째 결쟂는
						System.out.println(i+"로 들어옴");
						ApprovalStatus as = new ApprovalStatus();
						as.setApStatus_type("승인");//승인처리
						approval.setAp_type(as);
					}
					service.completeApRegister(approval);//결재자 처리 
				}
			}

			//
			if (addAgLineEmpId != null && !"".equals(addAgLineEmpId) && !"agreementBoxBtn".equals(addAgLineEmpId)) {
				Agreement agreement = new Agreement();
				Document agDocNo = new Document();
				agDocNo.setDocument_no(addApDocsNo);
				agreement.setDocument_no(agDocNo);
				Employee agEmp = new Employee();
				agEmp.setEmployee_id(addAgLineEmpId);
				agreement.setEmployee_id(agEmp);

				service.completeAgRegister(agreement);//합의자 처리 
			}

			//
			if (addReLineEmpId != null && !"".equals(addReLineEmpId) && !"referenceBoxBtn".equals(addReLineEmpId)) {
				Reference reference = new Reference();
				Document reDocNo = new Document();
				reDocNo.setDocument_no(addApDocsNo);
				reference.setDocument_no(reDocNo);
				Employee reEmp = new Employee();
				reEmp.setEmployee_id(addReLineEmpId);
				reference.setEmployee_id(reEmp);

				service.completeReRegister(reference);//참조자 처리 
			}
		} catch (AddException | FindException e) {
			e.printStackTrace();
		}

	}

	public String chkDocTypeCode(String document_type) {//코드 설정
//		String[] strArr = {"SR", "CR", "BC", "AC", "LE"};
		String docType = document_type.trim();
		String result = "";
		if ("지출".equals(docType)) {
			result = "SR";
		} else if ("회람".equals(docType)) {
			result = "CR";
		} else if ("품의".equals(docType)) {
			result = "AC";
		} else if ("휴가".equals(docType)) {
			result = "LE";
		} else {
			result = "BC";
		}
		return result;
	}

	public String today() {//오늘날짜 셋팅
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();

		Date dateObj = calendar.getTime();
		String formattedDate = sdf.format(dateObj);
		return formattedDate;
	}
	
	public String modiDocNumber(int docNum){
		String result="";
		if(docNum <10) {
			result = "000"+docNum;
		}else if(docNum<100) {
			result = "00"+docNum;
		}else if(docNum<1000) {
			result = "0"+docNum;
			
		}else {
			result = ""+docNum;			
		}
		return result;
	}

}