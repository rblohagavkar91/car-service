package com.rahul.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "User is Offline....")
public class UserIsOfflineException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public UserIsOfflineException(String message)
    {
        super(message);
    }

}
