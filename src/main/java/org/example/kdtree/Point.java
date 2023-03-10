package org.example.kdtree;

public class Point{

	private final double x, y;
	private final long offset;

	public Point(double x, double y, long offset) {
		this.x = x;
		this.y = y;
		this.offset = offset;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.offset = 0;
	}

	public long getOffset() {
		return offset;
	}


	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}


	public double distance(Point p) {
		double ac = Math.abs(p.getX() - x);
		double cb = Math.abs(p.getY() - y);

		return Math.hypot(ac, cb);
	}
}
