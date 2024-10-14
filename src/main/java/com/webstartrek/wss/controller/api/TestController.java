package com.webstartrek.wss.controller.api;

import com.webstartrek.wss.models.dtos.PingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.media.Schema;

@Path("test/v1")
@Tag(name = "Test API", description = "API to test")
public class TestController {

    @Operation(summary = "Test the API",
            description = "Returns a JSON response with status information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PingResponse.class)))
            })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ping")
    public Response ping() {
        PingResponse response = new PingResponse();
        response.setStatusMessage("Success");
        response.setStatusCode(200);
        return Response.ok(response).build();
    }
}
