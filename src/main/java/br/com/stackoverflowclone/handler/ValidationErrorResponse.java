package br.com.stackoverflowclone.handler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {

    private List<Error> errors = new ArrayList<>();
}
