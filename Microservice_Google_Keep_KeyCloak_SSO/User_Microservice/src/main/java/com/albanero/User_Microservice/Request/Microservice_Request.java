package com.albanero.User_Microservice.Request;

import com.albanero.User_Microservice.Model.Users;

public class Microservice_Request {

	private Users user;
	private Notes note;

	public Microservice_Request() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Microservice_Request(Users user, Notes note) {
		super();
		this.user = user;
		this.note = note;
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

	
}
