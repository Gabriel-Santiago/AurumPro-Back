package com.AurumPro.configurations;

import com.AurumPro.dtos.erro.ErrorResponseDTO;
import com.AurumPro.exceptions.BadRequestException;
import com.AurumPro.exceptions.ConflictException;
import com.AurumPro.exceptions.ForbiddenException;
import com.AurumPro.exceptions.NotFoundException;
import com.AurumPro.exceptions.UnauthorizedException;
import com.AurumPro.exceptions.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
            NotFoundException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                false,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(
            BadRequestException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                false,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(
            ConflictException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                false,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnprocessableEntity(
            UnprocessableEntityException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                false,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(
            UnauthorizedException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                false,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbidden(
            ForbiddenException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                false,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}
