package com.polygon.rest.models;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Coordinate class
 *
 * @author Luka Potočnik
 * @since 1.0.0
 */
@Entity
@Table(name="coordinate")
public class Coordinate implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(hidden=true)
	private Integer id;

	@Column(name="x")
	@Schema(example="1200")
	private int x;

	@Schema(example="80")
	@Column(name="y")
	private int y;

	public  int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}
}
