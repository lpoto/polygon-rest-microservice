package com.polygon.rest.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import com.polygon.rest.models.Polygon;
import com.polygon.rest.models.Coordinate;

import javax.enterprise.context.RequestScoped;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;


/**
 * PolygonBean class
 *
 * @author Luka Potočnik
 * @since 1.0.0
 */
@RequestScoped
public class PolygonBean {

	@PersistenceContext(unitName = "polygons")
	private EntityManager em;

	public Polygon getPolygon(Integer polygonId) {
		return em.find(Polygon.class, polygonId);
	}

	public List<Polygon> getPolygons(QueryParameters query) {
		List<Polygon> polygons = JPAUtils
			.queryEntities(em, Polygon.class, query);
		return polygons;
	}

	public Long getPolygonCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, Polygon.class, query);
        return count;
    }
	public List<Coordinate> getCoordinates(QueryParameters query) {
		List<Coordinate> coordinates = JPAUtils.
			queryEntities(em, Coordinate.class, query);
		return coordinates;
	}

	@Transactional
	public boolean savePolygon(Polygon polygon) {
		if (polygon != null && !em.contains(polygon)) {
			em.persist(polygon);
			return true;
		}
		return false;
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void deletePolygon(Integer polygonId) {
		Polygon polygon = em.find(Polygon.class, polygonId);
		if (polygon != null) {
			em.remove(polygon);
		}
	}
}

