package com.msantos.starwars.exception;

public class RemoteRequestException extends UnprocessableEntityException {

	private static final long serialVersionUID = 3800750222971399712L;

	public RemoteRequestException(String message) {
		super(message);
	}

}
