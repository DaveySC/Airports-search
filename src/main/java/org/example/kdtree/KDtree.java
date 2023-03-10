package org.example.kdtree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class KDtree {

	private FixedSizeHeap<Pair<Double, Point>> kNearestNeighbors;
	private HashMap<Integer, Comparator<Point>> pointCompareMap;
	private Node root;

	public KDtree(List<Point> points) {
		pointCompareMap = new HashMap<>();
		pointCompareMap.put(0, Comparator.comparingDouble(Point::getX));
		pointCompareMap.put(1, Comparator.comparingDouble(Point::getY));
		root = createTree(points, 0);
	}

	private Node createTree(List<Point> points, int depth) {
		if (points == null || points.size() == 0) return null;

		int axis = depth % pointCompareMap.size();

		points.sort(pointCompareMap.get(axis));

		int median = points.size() / 2;

		Node node = new Node(points.get(median));

		node.setLeft(createTree(points.subList(0, median), depth + 1));
		node.setRight(createTree(points.subList(median + 1, points.size()), depth + 1));

		return node;
	}

	public FixedSizeHeap<Pair<Double, Point>> findNearestKNeighbors(Point search, final int k) {
		Comparator<Pair<Double, Point>> comparator = (o1, o2) -> Double.compare(o2.getFirst(), o1.getFirst());
		kNearestNeighbors = new FixedSizeHeap<>(k, comparator);
		findNearestKNeighbors(search, root, 0, k);
		return kNearestNeighbors;
	}

	void findNearestKNeighbors(Point search, Node currentNode, int depth, final int k) {
		if (currentNode == null) return;

		double distance = search.distance(currentNode.getPoint());

		if (kNearestNeighbors.size() < k
				|| Double.compare(distance, kNearestNeighbors.peek().getFirst()) < 0) {
			kNearestNeighbors.add(new Pair<>(distance, currentNode.getPoint()));
		}

		double searchAxisVal = (depth % 2 == 0) ? search.getX() : search.getY();
		double currentAxisVal = (depth % 2 == 0) ? currentNode.getPoint().getX() : currentNode.getPoint().getY();
		double currentProbablyBestDistance = Math.abs(searchAxisVal - currentAxisVal);

		if (Double.compare(searchAxisVal, currentAxisVal) < 0) {
			findNearestKNeighbors(search, currentNode.getLeft(), depth + 1, k);
			if (kNearestNeighbors.size() < k
					|| Double.compare(currentProbablyBestDistance, kNearestNeighbors.peek().getFirst()) < 0)
				findNearestKNeighbors(search, currentNode.getRight(), depth + 1, k);
		} else {
			findNearestKNeighbors(search, currentNode.getRight(), depth + 1, k);
			if (kNearestNeighbors.size() < k
					|| Double.compare(currentProbablyBestDistance, kNearestNeighbors.peek().getFirst()) < 0)
				findNearestKNeighbors(search, currentNode.getLeft(), depth + 1, k);
		}
	}
}
