package org.example;

import org.example.kdtree.Point;
import org.example.solver.Solver;

import java.util.Scanner;

public class Main {

	private final static String QUIT_COMMAND = "!exit";

	public static void main(String[] args) {
		System.out.println("Подождите немного. Была начата подготовка к работе.");
		Solver solver = new Solver();
		System.out.println("Подготовка закончена.");
		Scanner scanner = new Scanner(System.in);

		String line;
		while(true) {
			try {
				System.out.println("Введите широту: ");
				line = scanner.nextLine();
				if (line.equals(QUIT_COMMAND)) break;
				double x = Double.parseDouble(line);

				System.out.println("Введите долготу: ");
				line = scanner.nextLine();
				if (line.equals(QUIT_COMMAND)) break;
				double y = Double.parseDouble(line);
				long startTime = System.nanoTime();
				solver.findNearestKNeighbors(new Point(x, y));
				long endTime = System.nanoTime();
				System.out.println("Затраченное время :  " + (endTime - startTime) / 1000000 + " мс\n");
			} catch (NumberFormatException exception) {
				System.out.println("Долгота и широта должны задаваться целыми числами" +
						" либо как число с плавающей запятой, разделенное точкой.");
			}
		}

		solver.closeFile();
	}
}