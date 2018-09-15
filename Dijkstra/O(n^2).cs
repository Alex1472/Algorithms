using System;
using System.IO;
using System.Collections.Generic;


namespace MergeSort {
	
	class MainClass {
		public static int n;
		public static int[,] arr;
		public static int[] dist;
		public static int[] mark;
		
		public static int Main (string[] args)
		{
			StreamReader sr = new StreamReader (@"INPUT.TXT");
			String[] buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
			n = Convert.ToInt32 (buf [0]);
			arr = new int[n, n];
			dist = new int[n];
			mark = new int[n];
			for (int i = 0; i < n; ++i) {
				dist [i] = 1000000;
			}
			//вершины во входном файле нумеруются с 1
			int start = Convert.ToInt32 (buf [1]);
			int finish = Convert.ToInt32 (buf [2]);
			
			for (int i = 0; i < n; ++i) {
				buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
				for (int j = 0; j < n; ++j) {
					arr [i, j] = Convert.ToInt32 (buf [j]);
				}
			}
			Dijkstra (start-1);
			//если пути нет - выводим -1, иначе - длину кратчайшего пути 
                        if (mark [finish-1] == 0) {
			    Console.WriteLine("-1");
			    return 0;
			}
			Console.WriteLine(dist[finish-1]);
			
			return 0;
		}
		
		public static void Dijkstra (int start){
			dist [start] = 0;
			mark [start] = 0;
			while (true) {
				int minDist = 1000000;
				int minInd = -1;
				for (int i = 0; i < n; ++i) {
					if ((mark [i] == 0) && (dist [i] < minDist)) {
						minDist = dist [i];
						minInd = i;
					}
				}
				if (minInd == -1) {
					break;
				}
				mark [minInd] = 1;
				for (int i = 0; i < n; ++i) {
					if ((arr [minInd, i] != -1) && (mark [i] == 0) && (dist [minInd] + arr [minInd, i] < dist [i])) {
					    dist[i] = dist [minInd] + arr [minInd, i];
					}
				}  
			}
		}
	}
}











