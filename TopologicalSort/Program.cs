using System;
using System.Collections.Generic;
using System.IO;

namespace simple {
	class MainClass {
	    public static int n, m;
	    public static List<int>[] edges;
	    public static int[] mark, mark1;
	    public static int[] topSort;
	    public static int curElem;
	    
	        //граф хранится списком смежности, вершины от 1 до n 
		public static void Main (string[] args)
		{
			StreamReader sr = new StreamReader (@"INPUT.TXT");
			String[] buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
			n = Convert.ToInt32 (buf [0]);
			m = Convert.ToInt32 (buf [1]);
			edges = new List<int>[n + 1];
			for (int i = 0; i <= n; ++i)
				edges [i] = new List<int> ();
			mark = new int[n + 1];
			mark1 = new int[n + 1];
			topSort = new int[n];
			curElem = n - 1;
			
			for (int i = 0; i < m; ++i) {
				buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
				edges [Convert.ToInt32 (buf [1])].Add (Convert.ToInt32 (buf [0]));
			}
			
			
			TopologicalSort();
			 
		}
		//Если в вершине еще не были, то запускаем Explore 

		public static void TopologicalSort() {
		    for (int i = 1; i <= n; ++i) 
		        if (mark1[i] == 0)
		             Explore1(i);
		}
                //При выходе из вершины добавляем эту вершину в конец массива с топологической сортировкой 
                //и ствигаем конец массива влево
		
		public static void Explore1 (int x) {
		    mark1[x] = 1;
		    foreach (int i in edges[x])
		        if (mark1[i] == 0)
		            Explore1(i);
		    topSort[curElem] = x;
		    --curElem;
		}
	}
}
