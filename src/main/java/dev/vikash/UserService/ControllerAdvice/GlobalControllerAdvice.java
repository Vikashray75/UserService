package dev.vikash.UserService.ControllerAdvice;

import dev.vikash.UserService.DTO.ErrorResponseDto;
import dev.vikash.UserService.Exception.InvalidCredential;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
@ExceptionHandler(value = InvalidCredential.class)
public ResponseEntity<ErrorResponseDto>handleInvalidCredentialException(Exception Ex)
{
    ErrorResponseDto errorResponseDto=new ErrorResponseDto();
    errorResponseDto.setMessage(Ex.getMessage());
    errorResponseDto.setMessageCode(404);
    return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
}
}
