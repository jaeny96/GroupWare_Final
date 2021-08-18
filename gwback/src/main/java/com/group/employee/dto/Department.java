package com.group.employee.dto;

public class Department {
	private String departmentId;
	private String departmentTitle;
	private String managerId;
	private int count;
	
	public Department() {
	}

	public Department(String departmentId,String departmentTitle,int count) {
		this(departmentId,departmentTitle,null,count);
	}
	
	public Department(String departmentId, String departmentTitle, String managerId, int count) {
		this.departmentId = departmentId;
		this.departmentTitle = departmentTitle;
		this.managerId = managerId;
		this.count = count;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentTitle() {
		return departmentTitle;
	}

	public void setDepartmentTitle(String departmentTitle) {
		this.departmentTitle = departmentTitle;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", departmentTitle=" + departmentTitle + ", managerId="
				+ managerId + ", count=" + count + "]";
	}	
}
