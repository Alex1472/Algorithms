using System;
using System.IO;
using System.Collections.Generic;

namespace test {
	public class MainClass {
		public static void Main (string[] args)
		{
			StreamReader sr = new StreamReader ("input.txt");
			string[] buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
			int n = int.Parse (buf [0]);
			int windowWidth = int.Parse (buf [1]);
			int[] numbers = new int[n];
			int curSize = 0;
			while (curSize != n) {
				buf = sr.ReadLine ().Split (new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
				for (int i = 0; i < buf.Length; ++i) {
					numbers [curSize] = int.Parse (buf [i]);
					++curSize;
				}
			}    
		    foreach (int x in CalculateMinOnSegment(numbers, windowWidth))
			    Console.Write(x + " "); 
		}
                //в очереди будем хранить некоторые элементы из текущего окна
                //элементы в очереди упорядочены по возрастанию, т. е. минимальный в голове
                //при переходе в следующему элементу
                   //1) удаляем элемент, который перестает быть в текущем окне
                      //для этого сравниваем его с головным, если совпадают, то удаляем головной, иначе ничего не делаем
                   //2) добавляем новый элемент
                      //для этого смотрим на последний элемент очереди, если он больше нового, то удаляет последний элемент
                      //делаем так пока последний элемент не станет <= нового
                      //добавляем в конец новый
                   //3)очередной минимальный элемент находится в голове очереди           
		
		public static List<int> CalculateMinOnSegment (int[] numbers, int windowWidth) {
			List<int> result = new List<int> ();
			MyQueue q = new MyQueue ();
		    
			for (int i = 0; i < windowWidth; ++i)
				q.AddToTail (numbers [i]);
		    result.Add(q.First);
		    
			for (int i = windowWidth; i < numbers.Length; ++i) {
			    if (numbers[i - windowWidth] == q.First)
			        q.Dequeue();
			    q.AddToTail(numbers[i]);
			    result.Add(q.First);
			}
		    return result;   
		}
        
		
		
	}
	
        //делаю собственную очередь т. к. в C# нет дека, а надо удалять из хвоста
	public class MyQueue {
	    const int queueSize = 160000;
	    int[] numbers = new int[160000];
	    int head = 0;
	    int tail = 0;
	    
	    public int First {
			get {
			    return numbers[head];
			}
	    }
	    
	    public int Last {
			get {
			   return numbers[tail - 1];
			}
	    }
	    
	    public bool Empty () {
		    return tail == head;
		}
		
		public void Enqueue (int x) {
		    numbers[tail] = x;
		    ++tail;
		}
		
		public int Dequeue () {
		    int tmp = numbers[head];
		    ++head;
		    return tmp;
		}
		
		public void DeleteLast () {
		    --tail;
		}
		
		public void AddToTail(int x) {
		    while ((!Empty()) && (Last > x))
		        DeleteLast();
		    Enqueue(x);
		} 
	}
}
