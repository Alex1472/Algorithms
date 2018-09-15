using System;
using System.Collections.Generic;
using System.IO;

//Проверяет наличие цикла в орграфе
//Вершины от 1 до n. Граф задан списком смежности. 

namespace simple {
	class MainClass {
	    public static int n, m;
	    public static List<int>[] edges;
	    public static int[] mark;
	    
		public static void Main (string[] args) {
			StreamReader sr = new StreamReader (@"INPUT.TXT");
			String[] buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
			n = Convert.ToInt32 (buf [0]);
			m = Convert.ToInt32 (buf [1]);
			edges = new List<int>[n + 1];
			for (int i = 0; i <= n; ++i)
			    edges[i] = new List<int>();
			mark = new int[n + 1];
			
			for (int i = 0; i < m; ++i) {
			     buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
			     edges[Convert.ToInt32(buf[1])].Add(Convert.ToInt32(buf[0]));
			}
			
			if (Dfs())
			    Console.WriteLine("Yes");
			else
			    Console.WriteLine("No");
		}
		
		public static bool Dfs () {
			for (int i = 1; i <= n; ++i) 
			    if ((mark[i] == 0) && (!Explore(i)))
			        return false;
			return true;            
		}
		
                //При входе в вершину красим её серым цветом(1), при выходе - черным(2).
                //Если пытаемся пойти в серую вершину => есть цикл.
 
		public static bool Explore (int x) {
			bool result = true; 
			mark [x] = 1;
			foreach (int i in edges[x]) {
				if (mark [i] == 1)
					result = false;
				if (mark [i] == 0) 
				    if (!Explore(i))
				        result = false;
			}
			mark[x] = 2;
			return result;
		}
	}
}
