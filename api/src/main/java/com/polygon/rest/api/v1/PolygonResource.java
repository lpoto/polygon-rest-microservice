package com.polygon.rest.api.v1;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.security.annotations.Secure;
import com.polygon.rest.services.PolygonBean;
import com.polygon.rest.models.Polygon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import java.util.List;


/**
 * PolygonResource class
 *
 * @author Luka Potočnik
 * @since 1.0.0
 */
@RequestScoped
@Path("polygons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure
@SecurityRequirement(name="Authentication")
public class PolygonResource {

	@Inject
	private PolygonBean polygonBean;

	@Context
	UriInfo uriInfo;

	@GET
	@Operation(
		summary = "Get polygons list",
		tags = {"polygons"},
		description = "Returns a list of polygons")
	@ApiResponses({
		@ApiResponse(
			description = "List of polygons",
			responseCode = "200",
			content = @Content(
				array = @ArraySchema(
					schema = @Schema(implementation = Polygon.class)))
		)
	})
	@RolesAllowed({"user", "admin"})
	public Response getAllPolygons() {
		List<Polygon> polygons = polygonBean.getPolygons(createQuery());
		return Response.ok(polygons).build();
	}

	@GET
	@Path("{polygonId}")
	@Operation(
		summary = "Get polygon's details",
		tags = {"polygons"},
		description = "Returns details about the polygon with the given id")
	@ApiResponses({
		@ApiResponse(
			description = "Polygon details",
			responseCode = "200",
			content = @Content(
				schema = @Schema(implementation = Polygon.class))),
		@ApiResponse(
			description = "Polygon not found",
			responseCode = "404")
	})
	@RolesAllowed({"user", "admin"})
	public Response getPolygon(@PathParam("polygonId") Integer polygonId) {
		Polygon polygon = polygonBean.getPolygon(polygonId);
		return polygon != null
			   ? Response.ok(polygon).build()
			   : Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("count")
	@Operation(
		summary = "Get polygons count",
		tags = {"polygons"},
		description = "Returns a total number of polygons")
	@ApiResponses({
		@ApiResponse(
			description = "Polygons count",
			responseCode = "200",
			content = @Content(
				schema = @Schema(implementation = Integer.class)))
	})
	@RolesAllowed({"user", "admin"})
	public Response getCount() {
		Long count = polygonBean.getPolygonCount(createQuery());
		return Response.ok(count).build();
	}

	@POST
	@Operation(
		summary = "Add a new polygon",
		tags = {"polygons"},
		description = "Adds a new polygon")
	@ApiResponses({
		@ApiResponse(
			description = "Polygon added",
			responseCode = "200"),
		@ApiResponse(
			description = "Conflict - duplicate id",
			responseCode = "409")
	})
	@RolesAllowed({"user", "admin"})
	public Response addPolygon(Polygon polygon) {
		boolean saved = polygonBean.savePolygon(polygon);
		return saved
			   ? Response.ok(polygon).build()
			   : Response.status(Response.Status.CONFLICT).build();
	}

	@DELETE
	@Path("{polygonId}")
	@Operation(
		summary = "Delete polygon",
		tags = {"polygons"},
		description = "Deletes polygon with the given id")
	@RolesAllowed({"user", "admin"})
	public Response deletePolygon(@PathParam("polygonId") Integer polygonId) {
		polygonBean.deletePolygon(polygonId);
		return Response.noContent().build();
	}

	private QueryParameters createQuery() {
		return QueryParameters.query(uriInfo.getRequestUri().getQuery())
			   .defaultOffset(0)
			   .defaultLimit(10)
			   .build();
	}
}
