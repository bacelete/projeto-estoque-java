package com.myapp.estoque.exception;

public class EmptyObjectException extends RuntimeException{
    public EmptyObjectException(String message) { super(message); }
    public EmptyObjectException() { super(); }
}
