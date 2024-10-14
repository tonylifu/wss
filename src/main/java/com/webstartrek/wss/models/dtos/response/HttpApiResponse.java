package com.webstartrek.wss.models.dtos.response;

import lombok.Builder;

import java.net.http.HttpHeaders;

@Builder
public record HttpApiResponse(String responseBody, int statusCode, HttpHeaders headers) { }
