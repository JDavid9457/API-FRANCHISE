package com.project.nequi.franchises.infrastructure.exceptions;

public class FranchiseNotFoundException extends RuntimeException {
    public FranchiseNotFoundException(String message) {
        super(message);
    }
}
