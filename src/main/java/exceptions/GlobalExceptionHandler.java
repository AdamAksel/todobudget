package exceptions;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.example.utils.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        ApiResponse response = ApiResponse.builder().message("Validation error").errors(errorMessages).build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().message(e.getMessage()).build());
    }
    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ApiResponse> handleUserNotAuthorizedException(UserNotAuthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder().message(e.getMessage()).build());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotAuthorizedException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).build());


        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse("Validation error");
        ApiResponse apiResponse = ApiResponse.builder().message(errorMessage).build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponse> handleDateTimeParseException(DateTimeParseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().message("Invalid due date format. Please use the format yyyy-MM-dd").build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Optional.ofNullable(e.getCause())
                .filter(cause -> cause instanceof InvalidFormatException)
                .map(cause -> (InvalidFormatException) cause)
                .filter(invalidFormatException -> invalidFormatException.getCause() instanceof DateTimeParseException)
                .map(invalidFormatException -> ResponseEntity.badRequest().body(
                        ApiResponse.builder()
                                .message("Invalid date format. Please use the format yyyy-MM-dd")
                                .build()))
                .orElse(ResponseEntity.badRequest().body(
                        ApiResponse.builder()
                                .message(e.getMessage())
                                .build()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiResponse> handleInvalidFormatException(InvalidFormatException e) {
        System.out.println(e.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        System.out.println(e.getCause());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Internal server error").build());
    }
}
