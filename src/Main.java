import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Author: Liudmila Kachurina (https://github.com/gracenotegracenote)
 * Date: 19-Oct-16
 */
public class Main {
	private static char[][] stars;
	private static char[][] stars2 = {
			{' ', ' ', '*'},
			{' ', ' ', '*'},
			{' ', '*', '*'},
			{'*', '*', '*'}
	};

	public static void main(String[] args) {
		double[] data = {1,2,3};
		double[] data1 = {1.1, 1.9, 2.2, 3.0, 5.1, 5.2, 4.3, 0.1, 4.5, 5.1};
		double[] data2 = {8.0, 6.0, 4.0, 1.0, 2.0, 3.0, 4.0, 9.0};

		printHistogram(data);

		//printStars(stars);

		System.out.println();
		System.out.println("Max area: " + findRectangle(stars));
	}

	private static void printStars(char[][] array) {
		if (array != null) {
			System.out.println();

			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[i].length; j++) {
					System.out.print(array[i][j]);
				}
				System.out.println();
			}
		}
	}

	private static int findRectangle(char[][] array) {
		//TODO: null pointer exception

		Deque<Integer> stack = new ArrayDeque<Integer>();

		//first iteration
		stack.push(0);
		int top = 0;
		int area;
		int maxArea = 0;

		//further iterations
		for (int i = 1; i <= array[0].length; i++) {
			int currentHigh = 0;
			if (i < array[0].length) {
				currentHigh = getHighOfColumn(array, i);
			}

			int highToCompare = getHighOfColumn(array, stack.peek());

			if (currentHigh >= highToCompare) {
				stack.push(i);
			} else {
				while (currentHigh < highToCompare) {
					stack.remove();

					if (stack.isEmpty()) {
						area = highToCompare * i;
					} else {
						area = highToCompare * (i - stack.peek() - 1);
					}

					if (area > maxArea) {
						maxArea = area;
					}

					if (i == array[0].length) return maxArea;

					if (stack.isEmpty()) {
						stack.push(i);
						break; //break of while?
					} else {
						highToCompare = getHighOfColumn(array, stack.peek());
					}
				}

				if (currentHigh >= highToCompare) {
					stack.push(i);
				}
			}
		}

		return maxArea;
	}

	private static int getHighOfColumn(char[][] array, int column) {
		int high = 0;
		for (int i = array.length - 1; i >= 0; i--) {
			if (array[i][column] == '*') {
				high++;
			} else break;
		}

		return high;
	}

	private static void printHistogram(double[] data) {
		int dataLength = data.length;

		//find min and max elements
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < data.length; i++) {
			if (data[i] > max) {
				max = (int) Math.round(data[i]);
			}

			if (data[i] < min) {
				min = (int) Math.round(data[i]);
			}
		}

		//print rows
		char[][] array = new char[max][dataLength];
		int i2 = 0;

		for (int i = max; i >= min; i--) {
			if (i <= 0) break;

			System.out.print(i + "|");

			for (int index = 0; index < data.length; index++) {
				double elemRounded = Math.round(data[index]);
				if (elemRounded >= i) {
					array[i2][index] = '*';
					System.out.print("*");
				} else {
					array[i2][index] = ' ';
					System.out.print(" ");
				}
			}
			i2++;
			System.out.println();
		}

		stars = array;

		//print abscissa
		System.out.print("0+");
		for (int i = 0; i < data.length; i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.print(" ");
		for (int i = 0; i <= data.length; i++) {
			System.out.print(i);
		}
	}
}
