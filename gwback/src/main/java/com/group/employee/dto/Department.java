package com.group.employee.dto;

public class Department {
	private String departmentId;
	private String departmentTitle;
	private String manager_id;
	private int count;
	
	public Department() {
	}

	
	public Department(String departmentId, String departmentTitle, String manager_id, int count) {
		super();
		this.departmentId = departmentId;
		this.departmentTitle = departmentTitle;
		this.manager_id = manager_id;
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

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", departmentTitle=" + departmentTitle + ", manager_id="
				+ manager_id + ", count=" + count + "]";
	}

	
}
