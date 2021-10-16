package com.acme.polygon;

import com.kumuluz.ee.rest.beans.QueryParameters;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
@Path("polygons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PolygonResource {
	@Inject
	private PolygonService polygonBean;

	@Context
	UriInfo uriInfo;

	@GET
	@Operation(
	summary = "Get polygons list.",
	tags = {"polygons"},
	description = "Returns a list of polygons.",
	responses = {
		@ApiResponse(
		description = "List of polygons.",
		responseCode = "200",
		content = @Content(
			array = @ArraySchema(
				schema = @Schema(implementation = Polygon.class)))
		)})
	public Response getAllPolygons() {
		List<Polygon> polygons = polygonBean.getPolygons(createQuery());
		return Response.ok(polygons).build();
	}

	@GET
	@Path("{polygonId}")
	@Operation(
	summary = "Get polygon's details.",
	tags = {"polygons"},
	description = "Returns polygon details.",
	responses = {
		@ApiResponse(
		description = "Polygon details.",
		responseCode = "200",
		content = @Content(
			schema = @Schema(implementation = Polygon.class))),
		@ApiResponse(
		description = "Polygon not found.",
		responseCode = "404")
	})
	public Response getPolygon(@PathParam("polygonId") Integer polygonId) {
		Polygon polygon = polygonBean.getPolygon(polygonId);
		return polygon != null 
			? Response.ok(polygon).build() 
			: Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("count")
	@Operation(
	summary = "Get polygons count.",
	tags = {"polygons"},
	description = "Returns polygons count.",
	responses = {
		@ApiResponse(
		description = "Polygons count.",
		responseCode = "200",
		content = @Content(
			schema = @Schema(implementation = Integer.class)))
	})
	public Response getCount() {
		Long count = polygonBean.getPolygonCount(createQuery());
		return Response.ok(count).build();
	}

	@POST
	@Operation(
	summary = "Add new polygon.",
	tags = {"polygons"},
	description = "Adds new polygon.",
	responses = {
		@ApiResponse(
		description = "Polygon added.",
		responseCode = "200"),
		@ApiResponse(
		description = "Conflict - duplicate id.",
		responseCode = "409")
	})
	public Response addPolygon(Polygon polygon) {
		boolean saved = polygonBean.savePolygon(polygon);
		return saved
			? Response.ok(polygon).build()
			: Response.status(Response.Status.CONFLICT).build();
	}

	@DELETE
	@Path("{polygonId}")
	@Operation(
	summary = "Delete polygon.",
	tags = {"polygons"},
	description = "Deletes polygon.")
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
