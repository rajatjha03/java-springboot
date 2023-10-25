package com.project.rampcards.exceptionhandler;

public class TransactionNotFoundException extends Throwable{
    public TransactionNotFoundException(String message)
    {
        super(message);
    }
}
