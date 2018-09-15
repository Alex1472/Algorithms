using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
 
namespace simple {
    class MainClass {
        public static ulong[] powers = new ulong[100000];
        public static ulong x = 177; 
         
        public static void Main (string[] args) {
            StreamReader sr = new StreamReader (@"INPUT.TXT");
            String s = sr.ReadLine();
            String sub = sr.ReadLine();
            CalculatePowers();
             
            ulong[] SsuffixHush = CalculateSuffixHush(s);
            ulong[] SubSuffixHush = CalculateSuffixHush(sub);
             
            ulong substringHush = CalculateHush(0, sub.Length - 1, SubSuffixHush);
            for (int i = 0; i + sub.Length - 1 < s.Length; ++i) 
                if (CalculateHush(i, i + sub.Length - 1, SsuffixHush) == substringHush)
                    Console.Write(i + " ");
        }
        //считает степени числа ч = 177, необходимые при хэшировании 
         
        public static void CalculatePowers () {
            powers[0] = 1;
            for (int i = 1; i < 100000; ++i) 
                powers[i] = powers[i - 1] * x;
        }

        //считает хэши всех суффиксов строки s 
         
        public static ulong[] CalculateSuffixHush (String s) {
            ulong[] result = new ulong[s.Length + 1];
            result[s.Length - 1] = (ulong)s[s.Length - 1];
             
            for (int i = s.Length - 1; i >= 0; --i) 
                result[i] = result[i + 1]*x + (ulong) s[i];
            return result;
        } 
        
        //по массиву суффиксов SsuffixHush, считает хэш подстроки с i-ого по j-ый элемент включительно 
         
        public static ulong CalculateHush (int i, int j, ulong[] SsuffixHush) {
            return SsuffixHush[i] - powers[j - i +1]*SsuffixHush[j + 1];
        }
    }
}
