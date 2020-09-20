package br.com.stackoverflowclone.handler;

import br.com.stackoverflowclone.exceptions.QuestionNotFoundException;
import br.com.stackoverflowclone.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
           error.getErrors().add(new Error(fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return error;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onUserNotFoundException(UserNotFoundException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getErrors().add(new Error("userId", "userId [" + e.getUserId() + "] não encontrado"));
        return error;
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onQuestionNotFoundException(QuestionNotFoundException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getErrors().add(new Error("questionId", "questionId [" + e.getQuestionId() + "] não encontrado"));
        return error;
    }
}
