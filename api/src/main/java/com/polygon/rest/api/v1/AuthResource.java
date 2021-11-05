package com.polygon.rest.api.v1;

import com.polygon.rest.services.ConfigProperties;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 * AuthResource class
 *
 * @author Luka Potočnik
 * @since 1.0.0
 */
@Path("auth")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(
	name="Authentication", scheme="bearer", bearerFormat="JWT",
	type=SecuritySchemeType.HTTP, in=SecuritySchemeIn.HEADER)
public class AuthResource {
	@Inject
	private ConfigProperties properties;

	@POST
	@Operation(
		summary="Get a bearer token",
		description="Create a new token for gaining authenticated " +
					"access to resources",
		tags= {"auth"})
	@ApiResponses({
		@ApiResponse(
			description="Sucessful authentication",
			responseCode = "200",
			content = @Content(
				schema=@Schema(implementation=String.class))
		),
		@ApiResponse(
			description="Missing credentials",
			responseCode = "400"),
		@ApiResponse(
			description="Authentication failed",
			responseCode = "401")
	})
	public Response login(
		@QueryParam("username") String username,
		@QueryParam("password") String password) {
		try {

			if (username == null || password == null)
				return Response.status(Response.Status.BAD_REQUEST).build();
			HttpURLConnection con = null;
			try {
				URL url = new URL(properties.getAuthConnectUrl());
				con = (HttpURLConnection) url.openConnection();
				String data = ("grant_type=password&client_id=fitness-app&" +
							   "username=" + username +
							   "&password=" + password
							  );
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty(
					"content-type",
					"application/x-www-form-urlencoded"
				);
				DataOutputStream wr = new DataOutputStream(
					con.getOutputStream());
				wr.writeBytes(data);
				wr.close();
			} catch (Exception e) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			if (con.getResponseCode() != 200) throw new IOException();
			while ((line=br.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			br.close();
			return Response.ok(response.toString()).build();
		} catch (IOException io) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
