package dev.vikash.UserService.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorResponseDto {

    String message;
    int messageCode;
}
