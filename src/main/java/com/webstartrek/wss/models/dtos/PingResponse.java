package com.webstartrek.wss.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PingResponse {
    @Schema(description = "Status message of the response", example = "Success")
    private String statusMessage;

    @Schema(description = "HTTP status code", example = "200")
    private int statusCode;

}