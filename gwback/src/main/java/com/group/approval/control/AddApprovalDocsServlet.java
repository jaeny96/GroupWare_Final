//package com.group.approval.control;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.group.approval.dto.Agreement;
//import com.group.approval.dto.Approval;
//import com.group.approval.dto.ApprovalStatus;
//import com.group.approval.dto.Document;
//import com.group.approval.dto.DocumentType;
//import com.group.approval.dto.Reference;
//import com.group.exception.AddException;
//import com.group.exception.FindException;
//import com.group.approval.service.DocsWriteService;
//import com.group.employee.dto.Employee;
//
///**
// * Servlet implementation class AddApprovalDocsServlet
// */
//public class AddApprovalDocsServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String addApDocsType = request.getParameter("addApDocsType").trim();
//		String addApWriterId = request.getParameter("addApWriterId");
//		String addApDocsTitle = request.getParameter("addApDocsTitle");
//		String addApDocsContent = request.getParameter("addApDocsContent");
//		String[] addApLineEmpIdArr = request.getParameterValues("addApLineEmpId[]");
//		String[] addApLineStepArr = request.getParameterValues("addApLineStep[]");
//		String addAgLineEmpId = request.getParameter("addAgLineEmpId");
//		String addReLineEmpId = request.getParameter("addReLineEmpId");
//		DocsWriteService service;
//		ServletContext sc = getServletContext();
//		DocsWriteService.envProp = sc.getRealPath(sc.getInitParameter("env"));
//		service = DocsWriteService.getInstance();
//		String docTypeCode = chkDocTypeCode(addApDocsType);
//
//		try {
//			int docNum = service.chkMaxNum(addApDocsType)+1;
//			String docNumber = modiDocNumber(docNum);
//			System.out.println(docTypeCode+"-"+addApDocsType+"-"+today()+"-"+docNumber);
//			String addApDocsNo=docTypeCode+"-"+addApDocsType+"-"+today()+"-"+docNumber;
//
//			Document document = new Document();
//			document.setDocumentNo(addApDocsNo);
//			document.setDocumentTitle(addApDocsTitle);
//			document.setDocumentContent(addApDocsContent);
//			DocumentType dtype = new DocumentType();
//			dtype.setDocumentType(addApDocsType);
//			document.setDocumentStatus(dtype);
//			Employee emp = new Employee();
//			emp.setEmployeeId(addApWriterId);
//			document.setEmployeeD(emp);
//			
//			service.complete(document);
//
//			for (int i = 0; i < addApLineEmpIdArr.length; i++) {
//				if("staffOne".equals(addApLineEmpIdArr[i]) || "staffTwo".equals(addApLineEmpIdArr[i]) ||"staffThree".equals(addApLineEmpIdArr[i])) {
//					System.out.println(i+"번째 결재자가 없습니다");
//				}else {
//					Approval approval = new Approval();
//					Document apDocNo = new Document();
//					approval.setDocumentNo(addApDocsNo);
//					Employee apEmp = new Employee();
//					apEmp.setEmployeeId(addApLineEmpIdArr[i]);
//					approval.setEmployee(apEmp);
//					approval.setApStep(Integer.parseInt(addApLineStepArr[i]));
//					if(i==0) {
//						System.out.println(i+"로 들어옴");
//						ApprovalStatus as = new ApprovalStatus();
//						as.setApType("승인");
//						approval.setApStatus(as);
//					}
//					service.completeApRegister(approval);
//				}
//			}
//
//			if (addAgLineEmpId != null && !"".equals(addAgLineEmpId) && !"agreementBoxBtn".equals(addAgLineEmpId)) {
//				Agreement agreement = new Agreement();
//				agreement.setDocumentNo(addApDocsNo);
//				Employee agEmp = new Employee();
//				agEmp.setEmployeeId(addAgLineEmpId);
//				agreement.setEmployee(agEmp);
//
//				service.completeAgRegister(agreement);
//			}
//
//			if (addReLineEmpId != null && !"".equals(addReLineEmpId) && !"referenceBoxBtn".equals(addReLineEmpId)) {
//				Reference reference = new Reference();
//				reference.setDocumentNo(addApDocsNo);
//				Employee reEmp = new Employee();
//				reEmp.setEmployeeId(addReLineEmpId);
//				reference.setEmployee(reEmp);
//
//				service.completeReRegister(reference);
//			}
//		} catch (AddException  e) {
//			e.printStackTrace();
//		} catch (FindException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	public String chkDocTypeCode(String document_type) {
////		String[] strArr = {"SR", "CR", "BC", "AC", "LE"};
//		String docType = document_type.trim();
//		String result = "";
//		if ("지출".equals(docType)) {
//			result = "SR";
//		} else if ("회람".equals(docType)) {
//			result = "CR";
//		} else if ("품의".equals(docType)) {
//			result = "AC";
//		} else if ("휴가".equals(docType)) {
//			result = "LE";
//		} else {
//			result = "BC";
//		}
//		return result;
//	}
//
//	public String today() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar calendar = Calendar.getInstance();
//
//		Date dateObj = calendar.getTime();
//		String formattedDate = sdf.format(dateObj);
//		return formattedDate;
//	}
//	
//	public String modiDocNumber(int docNum){
//		String result="";
//		if(docNum <10) {
//			result = "000"+docNum;
//		}else if(docNum<100) {
//			result = "00"+docNum;
//		}else if(docNum<1000) {
//			result = "0"+docNum;
//			
//		}else {
//			result = ""+docNum;			
//		}
//		return result;
//	}
//}
