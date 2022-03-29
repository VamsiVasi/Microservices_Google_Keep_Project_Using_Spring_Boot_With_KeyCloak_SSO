package com.albanero.User_Microservice.Exceptions;

public class Error_Details_ {
	
	private String message;
	private String status;
	public Error_Details_(String message, String status) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public String getStatus() {
		return status;
	}

	
}