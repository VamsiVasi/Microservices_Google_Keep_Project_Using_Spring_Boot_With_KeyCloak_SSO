package com.albanero.User_Microservice.Response;

import com.albanero.User_Microservice.Model.Users;
import com.albanero.User_Microservice.Request.Notes;

public class Microservice_Response {

	private Users user;
	private Notes note;
	private String Status;

	public Microservice_Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Microservice_Response(Users user, Notes note, String status) {
		super();
		this.user = user;
		this.note = note;
		Status = status;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Notes getNote() {
		return note;
	}

	public void setNote(Notes note) {
		this.note = note;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	
}
