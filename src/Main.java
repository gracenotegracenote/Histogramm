import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Author: Liudmila Kachurina (https://github.com/gracenotegracenote)
 * Date: 19-Oct-16
 */
public class Main {
	private static int starIndex = -1; //rename to endIndex
	private static int initIndex = -1;


	public static void main(String[] args) {
		double[] data = {1,2,3};
		double[] data1 = {1.1, 1.9, 2.2, 3.0, 5.1, 5.2, 4.3, 0.1, 4.5, 5.1};
		double[] data2 = {8.0, 6.0, 4.0, 1.0, 2.0, 3.0, 4.0, 9.0};

		//TODO: remove starIndex from field
		char[][] hist = getHistogram(data1);
		printHistogram(hist);
		System.out.println("Max area: " + getMaxRectangleArea(hist));
		System.out.println("Begin index: " + initIndex);
		System.out.println("End index: " + starIndex);


		System.out.println();
		char[][] newHist = changeHistogram(hist, initIndex, starIndex);
		printHistogram(newHist);
	}

	private static char[][] changeHistogram(char[][] hist, int initIndex, int endIndex) {
		//TODO: startIndex = -1, null pointer exception

		char[][] newHist = hist;
		int area = getMaxRectangleArea(newHist);
		int horizontalSideLength = endIndex - initIndex + 1;
		int verticalSideLength = area / horizontalSideLength;

		for (int i = hist.length - 1; i > hist.length - 1 - verticalSideLength; i--) {
			for (int j = initIndex; j < initIndex + horizontalSideLength; j++) {
				newHist[i][j] = 'O';
			}
		}




		/*char[][] newHist = hist;
		int high = getHighOfColumn(hist, startIndex);
		int shortestSide = (startIndex <= high) ? startIndex : high;
		int longestSide = getMaxRectangleArea(hist) / shortestSide;
		boolean highLonger = (startIndex <= high);
		int startInd = startIndex;
		int area = getMaxRectangleArea(hist);


		if high longer -> go horizontal, otherwise -> go vertical
		if (!highLonger) {
			for (int i = hist.length - 1; i > hist.length - 1 - high; i--) {
				for (int j = startInd; j > startInd - (area / high); j--) {
					newHist[i][j] = 'O';
				}
			}
		} else {
			//METHOD 1: doesnt work
			/*for (int i = startInd; i > 0; i--) {
				for (int j = hist.length - 1; j > hist.length - 1 - longestSide; j--) {
 					hist[i][j] = 'O';
				}
			}

			//METHOD 2: doesnt work
			int newHigh = high;
			while (newHigh > startInd) {
				newHigh--;
			}

			for (int i = hist.length - 1; i > hist.length - 1 - newHigh; i--) {
				for (int j = startInd; j > startInd - (area / newHigh); j--) {
					newHist[i][j] = 'O';
				}
			}
		}*/

		return newHist;
	}

	private static void printHistogram(char[][] hist) {
		//TODO: null pointer exception

		//print rows
		int i2 = 0;

		for (int i = hist.length; i > 0 ; i--) {
			System.out.print(i + "|");

			for (int j = 0; j < hist[0].length; j++) {
				System.out.print(hist[i2][j]);
			}
			i2++;
			System.out.println();
		}

		//print abscissa
		System.out.print("0+");
		for (int i = 0; i < hist[0].length; i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.print(" ");
		for (int i = 0; i <= hist[0].length; i++) {
			System.out.print(i);
		}
		System.out.println();
	}

	private static void printStars(char[][] array) {
		//TODO: null pointer exception

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}

	private static int getMaxRectangleArea(char[][] array) {
		//TODO: null pointer exception

		Deque<Integer> stack = new ArrayDeque<>();

		initIndex = 0;

		//first iteration
		stack.push(0);
		int area;
		int maxArea = 0;

		//further iterations
		int n = array.length - 1; //last row index
		for (int i = 1; i <= array[n].length; i++) {
			int currentHigh = 0;
			if (i < array[0].length) {
				currentHigh = getHighOfColumn(array, i);
			}

			int highToCompare = getHighOfColumn(array, stack.peek());

			if (currentHigh >= highToCompare) {
				stack.push(i);
			} else {
				while (currentHigh < highToCompare) {
					int lastJ = initIndex;
					int tempJ = stack.remove();
					//System.out.println("tempJ = " + tempJ);

					if (stack.isEmpty()) {
						area = highToCompare * i;
					} else {
						area = highToCompare * (i - stack.peek() - 1);
					}

					if (area > maxArea) {
						maxArea = area;
						starIndex = i - 1;

						initIndex = tempJ;
						if (initIndex == starIndex) initIndex = lastJ;
						//System.out.println("Begin index = " + initIndex);
					}

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

	private static void printHist(double[] data) {
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

		//stars = array;

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

	private static char[][] getHistogram(double[] data) {
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

		char[][] array = new char[max][dataLength];
		int i2 = 0;

		for (int i = max; i >= min; i--) {
			if (i <= 0) break;

			for (int index = 0; index < data.length; index++) {
				double elemRounded = Math.round(data[index]);
				if (elemRounded >= i) {
					array[i2][index] = '*';
				} else {
					array[i2][index] = ' ';
				}
			}
			i2++;
		}

		return array;
	}
}
