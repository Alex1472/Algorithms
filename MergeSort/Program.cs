using System;
using System.IO;
using System.Collections.Generic;

namespace MergeSort {
	
	class MainClass {
		public const long size = 1000000;
		public const long infinity = 10000000000; 
		public static long[] arr = new long[size];
		
		public static void Main (string[] args) {
			StreamReader sr = new StreamReader (@"input.txt");
			string[] buf;
			long n = Convert.ToInt64 (sr.ReadLine ());
			long numElem = 0;
			
			while (numElem != n) {
				buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
				for (int i = 0; i < buf.Length; ++i) {
					arr [numElem] = Convert.ToInt64 (buf [i]);
					++numElem;
				}
			}
			
                        //сначала нужно сделать массив размером 2^k, 
			//и заполнить фективные элементы +бесконечностями

			long minPower = minPowerOfTwo (n);
			for (long i = n; i < minPower; ++i) {
				arr [i] = infinity;
			}
			
			arr = MergeSort(arr, minPower);
			for (long i = 0; i < n; ++i) {
			    Console.Write(arr[i] + " ");
			}
		}

                //принимает массив и его размер (2^k) 
		//возвращает отсортированный массив
		
		public static long[] MergeSort(long[] arr, long minPower) {
		    Queue<long[]> queue = new Queue<long[]> ();
			for (int i = 0; i < minPower; ++i) {
				queue.Enqueue (new long[] {arr[i]});
			}
			
			while (queue.Count != 1) {
				long[] a = queue.Dequeue ();
				long[] b = queue.Dequeue ();
				queue.Enqueue (Merge (a, b));
			}
			return queue.Dequeue();
		} 
		
		public static long minPowerOfTwo (long x) {
			long res = 1;
			while (res < x) {
			    res *= 2;
			}
			return res;
		}
		
		public static long[] Merge (long[] a, long[] b) {
			long n = a.Length;
			long[] buf = new long[2 * n];
			long curPos = 0;
		    
			long aPointer = 0, bPointer = 0;
			while ((aPointer < n) && (bPointer < n)) {
				if (a[aPointer] < b[bPointer]) {
					buf [curPos] = a [aPointer];
					++aPointer;
				} else {
					buf [curPos] = b[bPointer];
					++bPointer;
				}
				++curPos;
			}
			
			if (aPointer < n) {
				for (long i = aPointer; i < n; ++i) {
					buf [curPos] = a [aPointer];
					++aPointer;
					++curPos;
				}
			}
			
			if (bPointer < n) {
				for (long i = bPointer; i < n; ++i) {
					buf [curPos] = b[bPointer];
					++bPointer;
					++curPos;
				}
			}
			return buf;
		}
	}
}











