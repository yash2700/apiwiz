
import java.util.Arrays;

public class MyClass {

	static boolean isFeasible(int mid, int arr[], int n,
							int k)
	{
		int pos = arr[0];

		int elements = 1;


		for (int i = 1; i < n; i++) {
			if (arr[i] - pos >= mid) {
			
				pos = arr[i];
				elements++;

			
				if (elements == k)
					return true;
			}
		}
		return false;
	}


	static int largestMinDist(int arr[], int n, int k)
	{
		// Sort the positions
		Arrays.sort(arr);

		// Initialize result.
		int res = -1;

		// Consider the maximum possible points
		int left = 1, right = arr[n - 1];



		// Do binary search for largest
		// minimum distance
		while (left < right) {
			int mid = (left + right) / 2;

		
			if (isFeasible(mid, arr, n, k)) {
	
				res = Math.max(res, mid);
				left = mid + 1;
			}


			else
				right = mid;
		}

		return res;
	}

	public static void main(String[] args)
	{
		int arr[] = { 1,3,1 };
		int n = arr.length;
		int k = 2;
		System.out.print(largestMinDist(arr, n, k));
	}
}

//Yaswanth Reddy