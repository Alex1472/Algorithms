using System;
using System.IO;
using System.Collections.Generic;

namespace MergeSort {
	
	class MainClass {
		public static int n;
		public static int[,] arr;
		public static int[] dist;
		public static int[] mark;
		
		public static int Main (string[] args) {
			StreamReader sr = new StreamReader (@"INPUT.TXT");
			String[] buf;
			n = Convert.ToInt32 (sr.ReadLine ());
			arr = new int[n, n];
			dist = new int[n];
			mark = new int[n];
			
			for (int i = 0; i < n; ++i) {
				buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
				for (int j = 0; j < n; ++j) {
					arr [i, j] = Convert.ToInt32 (buf [j]);
				}
			}
			buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
			//вершины нумеруются с единицы
                        int start = Convert.ToInt32 (buf [0]);
			int finish = Convert.ToInt32 (buf [1]); 
			
			bfs (start - 1);
			
                        // выводим -1, если пути нет, иначе кратчайшее растояние 
                        if (mark [finish - 1] == 0) {
			    Console.WriteLine("-1");
			    return 0;
			}
			Console.WriteLine(dist[finish - 1]);
			return 0;
		}
		
		public static void bfs (int start) {
			dist [start] = 0;
			mark [start] = 1;
			Queue<int> queue = new Queue<int> ();
			queue.Enqueue (start);
			while (queue.Count != 0) {
				int x = queue.Dequeue ();
				for (int i = 0; i < n; ++i) {
					if ((mark [i] == 0) && (arr [x, i] == 1)) {
					    mark[i] = 1;
					    dist[i] = dist[x] + 1;
					    queue.Enqueue(i);
					}
				} 
			}
		}
		
		
	}
}











