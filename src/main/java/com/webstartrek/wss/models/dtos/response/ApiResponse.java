package com.webstartrek.wss.models.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.ws.rs.core.HttpHeaders;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private String responseMessage;
    private String responseCode;
    private int httpStatusCode;
    private HttpHeaders httpHeaders;
    private boolean isError;
}
