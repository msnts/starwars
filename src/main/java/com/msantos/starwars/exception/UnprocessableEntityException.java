package com.msantos.starwars.exception;

public class UnprocessableEntityException extends RuntimeException {

	private static final long serialVersionUID = -9061721187217817248L;

	public UnprocessableEntityException(String message) {
		super(message);
	}

}
