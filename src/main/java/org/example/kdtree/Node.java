package org.example.kdtree;

public class Node{
	private final Point point;
	private Node left, right;

	public Node(Point point, Node left, Node right) {
		this.point = point;
		this.left = left;
		this.right = right;
	}

	public Node(Point point) {
		this.point = point;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Point getPoint() {
		return point;
	}


}
