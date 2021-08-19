package com.group.approval.dto;

public class DocumentType {
	private String dType;
	
	public DocumentType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentType(String dType) {
		super();
		this.dType = dType;
	}

	public String getDocumentType() {
		return dType;
	}

	public void setDocumentType(String dType) {
		this.dType = dType;
	}

	@Override
	public String toString() {
		return "DocumentType [documentType=" + dType + "]";
	}
   
	
}