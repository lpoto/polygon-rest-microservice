package com.polygon.rest.api.v1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.Set;
import java.util.HashSet;

/**
 * PolygonApplication class
 *
 * @author Luka Potočnik
 * @since 1.0.0
 */
@ApplicationPath("v1")
@DeclareRoles({"user", "admin"})
@OpenAPIDefinition(
	info=@Info(
			 title="PlygonApi",
			 version="v1.0.0",
			 contact=@Contact(email="luka.potochnik@gmail.com"),
			 license=@License(name="MIT")),
	servers=@Server(url="http://localhost:8080/v1"))
public class PolygonApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

		classes.add(PolygonResource.class);
		classes.add(AuthResource.class);

		return classes;
	}

}
