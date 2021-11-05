package com.polygon.rest.models;

import java.util.List;
import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Polygon class
 *
 * @author Luka Potočnik
 * @since 1.0.0
 */
@Entity
@Table(name="polygon")
public class Polygon implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(hidden=true)
	private Integer id;

	@Column(name="name", nullable=false)
	@Schema(example="New polygon", required=true)
	private String name;

	@Column(name="length")
	@Schema(hidden=true)
	private Integer length;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="polygon_id")
	private List<Coordinate> coordinates;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
		setLength(null);
	}

	public Integer getLength() {
		return length;
	}

	private void setLength(Integer x) {
		this.length = Integer.valueOf(0);
		if (x != null) return;
		int coordCount = this.getCoordinates().size();
		if (coordCount < 2) return;
		for (int i = 1; i < coordCount; i++) {
			this.length += coordDiff(
					this.coordinates.get(i),
					this.coordinates.get(i-1));
		}
	}

	private int coordDiff(Coordinate a, Coordinate b) {
		return (int)Math.sqrt(
				Math.pow((a.getX() - b.getX()), 2) +
				Math.pow((a.getY() - b.getY()), 2));

	}
}
