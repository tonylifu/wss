package com.webstartrek.wss;

import com.webstartrek.wss.controller.api.TestController;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class AppRestConfiguration extends Application {

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();

        // Define the OpenAPI object
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .version("1.0")
                        .description("API for my Java EE application"))
                // Set the server URL to include the context root
                .servers(Arrays.asList(new Server().url("http://localhost:8080/wss/api")));

        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(openAPI)
                .prettyPrint(true);

        // Create an instance of OpenApiResource and set the configuration
        OpenApiResource openApiResource = new OpenApiResource();
        openApiResource.setOpenApiConfiguration(oasConfig);

        singletons.add(openApiResource);
        return singletons;
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(TestController.class);
        // Add other resource classes here
        return classes;
    }

}
