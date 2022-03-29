package com.albanero.User_Microservice.Response;

import com.albanero.User_Microservice.Model.Users;

public class Microservice_User_Response {

	private Users user;
	private String Status;

	public Microservice_User_Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Microservice_User_Response(Users user, String status) {
		super();
		this.user = user;
		Status = status;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

}
