package com.eazybytes.accounts.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information."
)
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "Api path invoke by client."
    )
    private String apiPath;

    @Schema(
            description = "Eror code representing the error happened."
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Eror message representing the error happened."
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when the error happened."
    )
    private LocalDateTime errorTime;
}
