package org.example.solver;

import org.example.kdtree.FixedSizeHeap;
import org.example.kdtree.KDtree;
import org.example.kdtree.Pair;
import org.example.kdtree.Point;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Solver {

	private final static int NUMBER_OF_AIRPORTS = 5;
	private static final String fileName = "airports.dat";

	private static final int latitudeColumn  = 6;

	private static final int longitudeColumn = 7;

	private static final int airportNameColumn = 1;

	private KDtree tree;

	private final List<Point> points;

	private final File csvFile;

	private RandomAccessFile randomAccessFile;

	public Solver() {
		this.points = new ArrayList<>();
		this.csvFile = new File(fileName);
		try {
			randomAccessFile = new RandomAccessFile(csvFile, "r");
		} catch (IOException e) {
			System.out.println("File not found : " + csvFile.getAbsolutePath());
			System.exit(1);
		}
		init();
	}
	private void init() {
		createPoints();
		this.tree = new KDtree(points);
	}

	private void createPoints() {
		try {
			while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
				long offset = randomAccessFile.getFilePointer();
				String line = randomAccessFile.readLine();
				createPoint(line, offset);
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	private void createPoint(String line, long offset) {
		try {
			String[] columns = CsvParser.parseCsvString(line);

			double x = Double.parseDouble(columns[latitudeColumn]);
			double y = Double.parseDouble(columns[longitudeColumn]);

			points.add(new Point(x, y, offset));
		}catch(NumberFormatException e){
			return;
		}
	}


	public void findNearestKNeighbors(Point search) {
		FixedSizeHeap<Pair<Double, Point>> answer = tree.findNearestKNeighbors(search, NUMBER_OF_AIRPORTS);
		try {
			for (Pair<Double, Point> pair : answer.getMaxHeap()) {
				randomAccessFile.seek(pair.getSecond().getOffset());
				String line = randomAccessFile.readLine();
				String[] columns = CsvParser.parseCsvString(line);
				System.out.printf("%s, Координаты:%s, %s\n",
						columns[airportNameColumn], columns[latitudeColumn], columns[longitudeColumn]);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public void closeFile() {
		try {
			randomAccessFile.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
