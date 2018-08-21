package com.hrms.model;

import java.util.List;

public class CreateProjectModel {

	private List<Employee> projectManagers;
	private List<Employee> techLeaders;
	private List<Employee> teamLeaders;
	private List<Employee> softwareEngineers;
	private List<Employee> trainees;

	public List<Employee> getProjectManagers() {
		return projectManagers;
	}

	public void setProjectManagers(List<Employee> projectManagers) {
		this.projectManagers = projectManagers;
	}

	public List<Employee> getTechLeaders() {
		return techLeaders;
	}

	public void setTechLeaders(List<Employee> techLeaders) {
		this.techLeaders = techLeaders;
	}

	public List<Employee> getTeamLeaders() {
		return teamLeaders;
	}

	public void setTeamLeaders(List<Employee> teamLeaders) {
		this.teamLeaders = teamLeaders;
	}

	public List<Employee> getSoftwareEngineers() {
		return softwareEngineers;
	}

	public void setSoftwareEngineers(List<Employee> softwareEngineers) {
		this.softwareEngineers = softwareEngineers;
	}

	public List<Employee> getTrainees() {
		return trainees;
	}

	public void setTrainees(List<Employee> trainees) {
		this.trainees = trainees;
	}

}
