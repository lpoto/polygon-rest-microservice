package com.acme.polygon;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

@SecurityScheme(
name = "openid-connect",
type = SecuritySchemeType.OPENIDCONNECT,
openIdConnectUrl = "http://auth-server-url/.well-known/openid-configuration")
@ApplicationPath("v1")
@OpenAPIDefinition(
info = @Info(
	title = "PolygonApi",
	version = "v1.0.0",
	contact = @Contact(),
	license = @License()),
servers = @Server(
	url = "http://localhost:8080/v1"),
security= @SecurityRequirement(
	name = "openid-connect"))
public class PolygonApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

		classes.add(PolygonResource.class);

		return classes;
	}

}
