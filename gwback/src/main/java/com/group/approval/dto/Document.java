package com.group.approval.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group.employee.dto.Employee;

public class Document {
	
	private String state;//결재서류,기안서류 구분용
	//문서 관련 변수 
	private String documentNo;
	private String documentTitle;
	private String documentContent;
	@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
	private Date draftDate;
	private DocumentType documentStatus;//db랑 has는 이름 다르게 하기 
	
	private Employee employee;
	private Agreement agreement;
	private Reference reference;
	private Approval approval;
	
	

	public Document(Reference reference) {
		super();
		this.reference = reference;
	}



	public Document(Approval approval) {
		super();
		this.approval = approval;
	}



	public Document(String documentNo, String documentTitle, String documentContent, Date draftDate,
			DocumentType documentStatus, Employee employee, Agreement agreement, Reference reference,
			Approval approval) {
		super();
		this.documentNo = documentNo;
		this.documentTitle = documentTitle;
		this.documentContent = documentContent;
		this.draftDate = draftDate;
		this.documentStatus = documentStatus;
		this.employee = employee;
		this.agreement = agreement;
		this.reference = reference;
		this.approval = approval;
	}

	private List<Approval> approvals;
	 	/*
	 * --Domcument의 department_no, department_id, department_title,document_content ,draft_date
--employee의 id, name
--approval의 apdate,id(employee-name),aptype = 단, apstep=0랑 관
--approval의 apdate,id(employee-name),aptype = 단, apstep=1
--approval의 apdate,name,aptype = 단, apstep2
--approval의 apdate,name,aptype = 단, apstep3
--agreement의 name,agdate,agtype
--reference의 name,redate
	 */
	public Document() {}

	

	public Document(String state, String documentNo,String documentTitle, String documentContent,
			Date draftDate, DocumentType documentStatus, Employee employee, Agreement agreement, Reference reference,
			Approval approval, List<Approval> approvals) {
		super();
		this.state = state;
		this.documentNo = documentNo;
		this.documentTitle = documentTitle;
		this.documentContent = documentContent;
		this.draftDate = draftDate;
		this.documentStatus = documentStatus;
		this.employee = employee;
		this.agreement = agreement;
		this.reference = reference;
		this.approval = approval;
		this.approvals = approvals;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(String documentContent) {
		this.documentContent = documentContent;
	}

	public Date getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(Date draftDate) {
		this.draftDate = draftDate;
	}

	public DocumentType getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(DocumentType documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Employee getEmployeeD() {
		return employee;
	}

	public void setEmployeeD(Employee employee) {
		this.employee = employee;
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public Approval getApproval() {
		return approval;
	}

	public void setApproval(Approval approval) {
		this.approval = approval;
	}

	public List<Approval> getApprovals() {
		return approvals;
	}

	public void setApprovals(List<Approval> approvals) {
		this.approvals = approvals;
	}

	@Override
	public String toString() {
		return "Document [state=" + state + ", documentNo=" + documentNo + ", documentTitle="
				+ documentTitle + ", documentContent=" + documentContent + ", draftDate=" + draftDate
				+ ", documentStatus=" + documentStatus + ", employee=" + employee + ", agreement=" + agreement
				+ ", reference=" + reference + ", approval=" + approval + ", approvals=" + approvals + "]";
	}

}