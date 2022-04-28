package lab9;

/**
 * 
 * COMP 3021
 * 
This is a class that prints the maximum value of a given array of 90 elements

This is a single threaded version.

Create a multi-thread version with 3 threads:

one thread finds the max among the cells [0,29] 
another thread the max among the cells [30,59] 
another thread the max among the cells [60,89]

Compare the results of the three threads and print at console the max value.

 * 
 * @author valerio
 *
 */
public class FindMax {
	// this is an array of 90 elements
	// the max value of this array is 9999
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };

	public static void main(String[] args) {
		new FindMax().printMax();
	}

	public void printMax() {
		// this is a single threaded version
		// int max = findMax(0, array.length - 1);
		// System.out.println("the max value is " + max);
		
		// multi-thread version
		
		class FindMaxInRange implements Runnable{
			private int begin;
			private int end;
			private int max;
			
			FindMaxInRange(int begin, int end) {
				this.begin = begin;
				this.end = end;
			}

			@Override
			public void run() {
				this.max = findMax(begin, end);
			}
			
			public int getMax() {
				return max;
			}
		}
		
		FindMaxInRange[] m = {new FindMaxInRange(0, 29), new FindMaxInRange(30, 59), new FindMaxInRange(60, 89)};
		
		Thread t1 = new Thread(m[0]);
		Thread t2 = new Thread(m[1]);
		Thread t3 = new Thread(m[2]);
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int max = Math.max(m[0].getMax(), m[1].getMax());
		max = Math.max(max, m[2].getMax());
		
		System.out.println("the max value is " + max);
	}

	/**
	 * returns the max value in the array within a give range [begin,range]
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
}
