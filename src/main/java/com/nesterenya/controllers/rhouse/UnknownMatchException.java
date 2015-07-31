package com.nesterenya.controllers.rhouse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Bad arguments")
public class UnknownMatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnknownMatchException(String matchId) {
        super("Unknown match: " + matchId);   
        
    }
}