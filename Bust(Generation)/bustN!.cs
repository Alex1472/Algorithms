using System;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace MergeSort {
	
	class MainClass {
		public static int n;
		public static int[] mark;
		public static int[] rotation;
		public static String st;

		public static int Main (string[] args) {
			StreamReader sr = new StreamReader (@"INPUT.TXT");
			st = sr.ReadLine();
			n = st.Length;
	        mark = new int[n];
	        rotation = new int[n];
	        Generate(0);
			return 0;
		}
                //Генерируем все перестановки мн-ва {0, 1, 2 .. n-1}
                //их n!
                //когда сгенерировали(n == k), вызываем PrintRotation
                //при запуске нужно передать 0(Generate(0)) 

		public static void Generate (int k) {
			if (k == n) {
				PrintRotation ();
			    return;
			}
				
			for (int i = 0; i < n; ++i) {
				if (mark [i] == 0) {
				    mark[i] = 1;
				    rotation[k] = i;
				    Generate(k+1);
				    mark[i] = 0;
				}
			}
		}
		
		public static void PrintRotation () {
			StringBuilder sb = new StringBuilder ();
			for (int i = 0; i < n; ++i) {
			    sb.Append(st[rotation[i]]);
			}
			Console.WriteLine(sb); 
		}
		
	}
}











