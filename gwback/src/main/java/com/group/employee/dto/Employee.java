package com.group.employee.dto;

import java.util.Date;

public class Employee {
	private String employeeId;
	private String name;
	private Department department;
	private Job job;
	private Position position;
	private String phoneNumber;
	private String email;
	private Date hireDate;
	private int enabled;
	private String password;

	public Employee() {
	}

	public Employee(String employeeId) {
		this(employeeId, null, null, null, null, null, null, null, 1, null);
	}
	
	public Employee(String employeeId,Department department) {
		this(employeeId, null, department, null, null, null, null, null, 1, null);
	}
	public Employee(String employeeId, int enabled, String password) {
		this(employeeId, null, null, null, null, null, null, null, 1, password);
	}

	public Employee(String employeeId, String phoneNumber, int enabled) {
		this(employeeId, null, null, null, null, phoneNumber, null, null, 1, null);
	}

	public Employee(String employeeId, String name, Department department, Job job, Position position) {
		this(employeeId, name, department, job, position, null, null, null, 1, null);
	}

	public Employee(String employeeId, String name, Department department, Job job, Position position,
			String phoneNumber, String email, String password) {
		this(employeeId, name, department, job, position, phoneNumber, email, null, 1, password);
	}

	public Employee(String employeeId, String name, Department department, Job job, Position position,
			String phoneNumber, String email, Date hireDate, String password) {
		this(employeeId, name, department, job, position, phoneNumber, email, hireDate, 1, password);

	}

	public Employee(String employeeId, String name, Department department, Job job, Position position,
			String phoneNumber, String email, Date hireDate, int enabled, String password) {
		this.employeeId = employeeId;
		this.name = name;
		this.department = department;
		this.job = job;
		this.position = position;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.hireDate = hireDate;
		this.enabled = enabled;
		this.password = password;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public int getenabled() {
		return enabled;
	}

	public void setenabled(int enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", department=" + department + ", job=" + job
				+ ", position=" + position + ", phoneNumber=" + phoneNumber + ", email=" + email + ", hireDate="
				+ hireDate + ", enabled=" + enabled + ", password=" + password + "]";
	}

}
