package com.hrms.model;

import java.util.List;
import java.util.Map;

public class ResponseModel {
	private String status;
	private String message;
	private Map<String, List> modelMap;
	private List modelList;

	public List getModelList() {
		return modelList;
	}

	public void setModelList(List modelList) {
		this.modelList = modelList;
	}

	public Map<String, List> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<String, List> modelMap) {
		this.modelMap = modelMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
