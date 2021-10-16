package com.acme.polygon;
import javax.persistence.*;

@Entity
@Table(name="coordinate")
public class Coordinate implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="x")
	private int x;

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
