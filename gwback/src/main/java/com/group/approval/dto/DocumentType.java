package com.group.approval.dto;

public class DocumentType {
	private String documentType;
	
	public DocumentType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentType(String documentType) {
		super();
		this.documentType = documentType;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@Override
	public String toString() {
		return "DocumentType [documentType=" + documentType + "]";
	}
   
	
}