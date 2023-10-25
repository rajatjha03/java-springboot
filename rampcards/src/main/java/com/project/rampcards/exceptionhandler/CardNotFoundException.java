package com.project.rampcards.exceptionhandler;

import javax.security.auth.login.CredentialNotFoundException;

public class CardNotFoundException extends Throwable{
   public CardNotFoundException(String message)
    {
        super(message);
    }
}
