package com.acme.polygon;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="polygon")
public class Polygon implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name", nullable=false)
	private String name;

	@Column(name="length")
	private Integer length;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="polygon_id")
	private List<Coordinate> coordinates;

	public Integer getId() {
		return this.id;
	}

	public Integer setId(Integer newId) {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public List<Coordinate> getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(List<Coordinate> newCoordinates) {
		this.coordinates = newCoordinates;
		setLength(null);
	}

	public Integer getLength() {
		return this.length;
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
