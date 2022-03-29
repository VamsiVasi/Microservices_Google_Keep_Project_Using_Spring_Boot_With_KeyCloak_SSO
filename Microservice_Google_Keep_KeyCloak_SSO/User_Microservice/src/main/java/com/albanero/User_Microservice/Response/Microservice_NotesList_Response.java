package com.albanero.User_Microservice.Response;

import java.util.List;

import com.albanero.User_Microservice.Request.Notes;

public class Microservice_NotesList_Response {

	private List<Notes> note;
	private String Status;

	public Microservice_NotesList_Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Microservice_NotesList_Response(List<Notes> note, String status) {
		super();
		this.note = note;
		Status = status;
	}

	public List<Notes> getNote() {
		return note;
	}

	public void setNote(List<Notes> note) {
		this.note = note;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

}
