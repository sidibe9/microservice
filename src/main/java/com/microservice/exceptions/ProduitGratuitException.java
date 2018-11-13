package com.microservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by sidibe0091 on 13/11/2018.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProduitGratuitException extends RuntimeException {
    public ProduitGratuitException(String message) {
        super(message);
    }

}
