package com.group.employee.dto;

public class Job {
	private String jobId;
	private String jobTitle;
	
	public Job() {
		
	}
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Job(String jobId, String jobTitle) {
		super();
		this.jobId = jobId;
		this.jobTitle = jobTitle;
	}

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", jobTitle=" + jobTitle + "]";
	}
	
	
	
	
}
