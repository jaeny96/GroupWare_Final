package com.group.approval.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group.employee.dto.Employee;

public class Document {
	
	private String state;//결재서류,기안서류 구분용
	private String documentNo;
	private String documentTitle;
	private String documentContent;
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Date draftDate;
	private DocumentType documentStatus;
	
	private Employee employee;
	private DocumentType documentType;
	public DocumentType getDocumentType() {
		return documentType;
	}



	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}
	private Approval approval;
	private Agreement agreement;
	private Reference reference;
	private List<Approval> approvals;
	
	public Document() {}
	


	public Document(String state, String documentNo, String documentTitle, String documentContent, Date draftDate,
			DocumentType documentStatus, Employee employee, DocumentType documentType, Approval approval,
			Agreement agreement, Reference reference, List<Approval> approvals) {
		super();
		this.state = state;
		this.documentNo = documentNo;
		this.documentTitle = documentTitle;
		this.documentContent = documentContent;
		this.draftDate = draftDate;
		this.documentStatus = documentStatus;
		this.employee = employee;
		this.documentType = documentType;
		this.approval = approval;
		this.agreement = agreement;
		this.reference = reference;
		this.approvals = approvals;
	}



	public Document(String documentNo, String documentTitle,Employee employee,Employee employee1,Date draftDate,
			DocumentType documentStatus,Approval approval) {
		super();
		this.documentNo = documentNo;
		this.documentTitle = documentTitle;
		this.employee = employee;
		this.employee = employee1;
		this.draftDate = draftDate;
		this.documentStatus = documentStatus;
		this.approval=approval;
	
	}

	
	public List<Approval> getApprovals() {
		return approvals;
	}
	public void setApprovals(List<Approval> approvals) {
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


	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public Approval getApproval() {
		return approval;
	}
	public void setApproval(Approval approval) {
		this.approval = approval;
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
	};
	@Override
	public String toString() {
		return "Document [state=" + state + ", documentNo=" + documentNo + ", documentTitle=" + documentTitle
				+ ", documentContent=" + documentContent + ", draftDate=" + draftDate + ", documentStatus="
				+ documentStatus + ", employee=" + employee + ", documentType=" + documentType + ", approval="
				+ approval + ", agreement=" + agreement + ", reference=" + reference + ", approvals=" + approvals + "]";
	}

	
	
}
