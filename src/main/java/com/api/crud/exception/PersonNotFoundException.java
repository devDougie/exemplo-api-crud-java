package com.api.crud.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(Long id) {
        super("Pessoa com ID " + id + " não encontrada.");
    }
}