package org.example.kdtree;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class FixedSizeHeap<T> {
	private final int maxSize;

	private final Queue<T> maxHeap;

	public FixedSizeHeap(int size, Comparator<T> comparator) {
		this.maxSize = size;
		this.maxHeap = new PriorityQueue<>(comparator);
	}

	public void add(T object) {
		maxHeap.add(object);
		while (maxHeap.size() > maxSize) maxHeap.poll();
	}

	public int getMaxSize() {
		return maxSize;
	}

	public int size() {
		return maxHeap.size();
	}

	public Queue<T> getMaxHeap() {
		return maxHeap;
	}

	public T peek() {
		return maxHeap.peek();
	}
}
