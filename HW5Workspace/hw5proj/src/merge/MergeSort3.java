package merge;

import java.util.*;

public class MergeSort3 
{
	private int[]			a;
	private long			nVisits;
	
	
	public MergeSort3(int[] a)
	{
		this.a = a;
	}
	
	
	// Multiple lines if a is bigger than 20 elements.
	public String toString()
	{
		return "MergeSort3:\n" + arrayToString(a);
	}
	
	
	// This might be useful for debugging.
	private static String arrayToString(int[] arr)
	{
		String s = "{ ";
		if (arr.length <= 20)
		{
			for (int i: arr)
				s += i + ",";
			s = s.substring(0, s.length()-1);		// delete last char (a comma) from s
			s += " }";
			return s;
		}
		
		else
		{
			for (int i: arr)
				s += "\n  " + i;
			s += "\n}";
			return s;
		}
	}
	
	
	// Complete this. It's ok to copy code you wrote in lab.
	private boolean isSorted(int[] arr)
	{
		for (int n=1; n<a.length; n++)
			if(a[n] > a[n-1])
				return false;
		return true;
	}
	
	
	public void sort()
	{
		nVisits = sortRecurse(a);
	}
	
	
	// Returns the number of visits.
	private long sortRecurse(int[] sortMe)
	{
		// Already sorted if length is 0 or 1.
		if (sortMe.length <= 1)
			return 1;				// 1 visit
		
		long nVisits = 0;
		
		// Copy values into 3 pieces.
		int pieceLength = sortMe.length / 3;
		if (pieceLength == 0)
			pieceLength = 1;
		int[] piece1 = new int[pieceLength];
		System.arraycopy(sortMe, 0, piece1, 0, pieceLength);
		int[] piece2 = new int[pieceLength];
		System.arraycopy(sortMe, pieceLength, piece2, 0, pieceLength);
		int lastPieceLength = sortMe.length - 2*pieceLength;
		int[] piece3 = new int[lastPieceLength];
		System.arraycopy(sortMe, 2*pieceLength, piece3, 0, lastPieceLength);
		nVisits += 2 * sortMe.length;
		
		// Sort the 3 pieces.
		nVisits += sortRecurse(piece1);
		assert isSorted(piece1)  :  "Not sorted";
		nVisits += sortRecurse(piece2);
		assert isSorted(piece2)  :  "Not sorted";
		nVisits += sortRecurse(piece3);
		assert isSorted(piece3)  :  "Not sorted";
		
		// Merges the 3 pieces in order
		ThreeArrayMerger merger = new ThreeArrayMerger(piece1, piece2, piece3, sortMe);
		merger.merge();
		nVisits +=  merger.getNVisits();
		assert isSorted(sortMe);
		
		return nVisits;
	}
	
	
	public long getNVisits()
	{
		return nVisits;
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("STARTING");
		
		// Test your algorithm on the tiny, already sorted, backward, and length=10
		// arrays provided by TestCaseMaker. When you are confident, comment out
		// the code or delete it.
		
//		MergeSort3 test = new MergeSort3(TestCaseMaker.getLength10());
//		test.sort();
//		for(int i: test.a)
//			System.out.println(i);
//		System.out.println(test.getNVisits());
		
		//Uncomment this code when you are confident your algorithm works.
		double timer = System.currentTimeMillis();
		int[] lengths = { 10_000, 50_000, 100_000, 250_000};
		for (int length: lengths)
		{
			int[] sortMe = TestCaseMaker.buildRandom(length, 1_000_000_000);
			MergeSort3 sorter = new MergeSort3(sortMe);
			sorter.sort();
			long nVisits = sorter.getNVisits();
			double nLogN = length * Math.log10(length);
			double k = nVisits / nLogN;
			System.out.println("Sorted " + length + " after " + nVisits + " visits ... k = " + k);
			timer = (timer - System.currentTimeMillis()) / 1000;
			System.out.println("Time elapsed: " + timer);
			
		}
		
		System.out.println("DONE");
	}
}
