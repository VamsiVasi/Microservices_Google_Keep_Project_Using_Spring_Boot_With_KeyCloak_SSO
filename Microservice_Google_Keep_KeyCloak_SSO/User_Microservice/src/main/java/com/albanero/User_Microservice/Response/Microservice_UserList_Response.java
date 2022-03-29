package com.albanero.User_Microservice.Response;

import java.util.List;

import com.albanero.User_Microservice.Model.Users;

public class Microservice_UserList_Response {

	private List<Users> user;
	private String Status;
	
	public Microservice_UserList_Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Microservice_UserList_Response(List<Users> user, String status) {
		super();
		this.user = user;
		Status = status;
	}

	public List<Users> getUser() {
		return user;
	}

	public void setUser(List<Users> user) {
		this.user = user;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
}
