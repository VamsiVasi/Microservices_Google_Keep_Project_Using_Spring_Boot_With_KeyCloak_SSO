package com.albanero.User_Microservice.Response;

import com.albanero.User_Microservice.Request.Notes;

public class Microservice_Notes_Response {
	private Notes note;
	private String Status;

	public Microservice_Notes_Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Microservice_Notes_Response(Notes note, String status) {
		super();
		this.note = note;
		Status = status;
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
