//Дана строка, состоящая из N символов. Требуется вывести все перестановки символов данной строки. 
// 122 => 122 212 221
//Сначала создаем массив из Symbol. Symbol содержит сам символ и количество его вхождений.
//В переборе идем по этому массиву и смотрим, если элемент можно использовать => используем
//GenerateRotations(int k) k - номер генерируемого символа
//в начале нужно запуститься GenerateRotations(0)

using System;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace MergeSort {
	
	class MainClass {
	    public static String st;
	    public static char[] stringArray;
	    public static Symbol[] symbols = new Symbol[10];
	    public static int n = 0;
	    public static char[] rotation;
		
		public static int Main (string[] args) {
			StreamReader sr = new StreamReader (@"INPUT.TXT");
			st = sr.ReadLine();
			stringArray = st.ToCharArray();
			rotation = new char[st.Length];
			
			Array.Sort(stringArray);
			CountSymbols(stringArray);
			GenerateRotations(0);
			return 0;
		}
		
		public static void GenerateRotations (int k) {
			if (k == st.Length) {
				PrintRotation ();
				return;
			}
			
			for (int i = 0; i < n; ++i) {
				if (symbols [i].num > 0) {
				    rotation[k] = symbols[i].c;
				    --symbols[i].num;
				    GenerateRotations(k + 1);
				    ++symbols[i].num;
				}
			}   
		}
		
		public static void PrintRotation () {
		    for (int i = 0; i < st.Length; ++i) 
		        Console.Write(rotation[i]);
		    Console.WriteLine("");
		}
		
		public static void CountSymbols (char[] str) {
			int numSymbol = 1;
			for (int i = 1; i < str.Length; ++i) {
				if (str [i] == str [i - 1])
					++numSymbol;
				else {
				    symbols[n] = new Symbol(str[i - 1], numSymbol);
				    ++n;
				    numSymbol = 1;
				}
			}
			symbols[n] = new Symbol(str[str.Length - 1], numSymbol);
		    ++n;
		}
	}
	
	public class Symbol {
	    public char c;
	    public int num;
	    
	    public Symbol (char c, int num) {
			this.c = c;
			this.num = num;
		}
	}
}











