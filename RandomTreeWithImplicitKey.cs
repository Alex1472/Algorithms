using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace RightSequence {
    public class Program {
        static Random rand = new Random();

        static void Main(string[] args) {
            string[] buf =  Console.ReadLine().Split(new char[] {' '}, StringSplitOptions.RemoveEmptyEntries);
            int n = int.Parse(buf[0]);
            int m = int.Parse(buf[1]);

            buf = Console.ReadLine().Split(new char[] {' '}, StringSplitOptions.RemoveEmptyEntries);
            Vertex root = null;
            for (int i = 0; i < n; ++i)
                root = Merge(root, new Vertex(int.Parse(buf[i])));

            for (int i = 0; i < m; ++i) {
                buf = Console.ReadLine().Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
                if (buf[0] == "1")
                    root = ReverseSegment(root, int.Parse(buf[1]), int.Parse(buf[2]));
                if (buf[0] == "2") {
                    int min = 0;
                    root = Min(root, int.Parse(buf[1]), int.Parse(buf[2]), ref min);
                    Console.WriteLine(min);
                }
                if (buf[0] == "*")
                    PrintTree(root);
            }
            Console.ReadKey();
        }

        //случайное дерево по неявному ключу 
        //по интерфейсу массив с поддержкой добавления и даления в произвольное место
        //за O(logN), а также некоторые другие операции

        //разделение дерева на два, в первом из которых key элементов 
        static Vertex[] Split(Vertex root, long key) {
            if (root == null)
                return new Vertex[] { null, null };
            Push(root); //проталкивание переворотов вниз по дереву

            int leftSize = root.left == null ? 0 : root.left.size;

            if (leftSize >= key) {
                Vertex[] splittedTree = Split(root.left, key);
                root.left = splittedTree[1];
                Recalc(root);
                return new Vertex[] { splittedTree[0], root };
            } else {
                Vertex[] splittedTree = Split(root.right, key - leftSize - 1);
                root.right = splittedTree[0];
                Recalc(root);
                return new Vertex[] { root, splittedTree[1] }; 
            }
        }

        static Vertex Merge(Vertex less, Vertex more) {
            if (less == null)
                return more;
            if (more == null)
                return less;
            Push(less); //проталкивание переворотов
            Push(more);

            int x = rand.Next() % (less.size + more.size);
            if (x < less.size) {
                less.right = Merge(less.right, more);
                Recalc(less);
                return less;
            } else {
                more.left = Merge(less, more.left);
                Recalc(more);
                return more;
            }
        }

        static void Recalc(Vertex v) {
            if (v == null) return;

            int size = 1;
            size += v.left == null ? 0 : v.left.size;
            size += v.right == null ? 0 : v.right.size;
            int min = v.number; //перессчет минимума в поддереве с ключом root. Считаем, что в поддеревьях
            //уже всё пересчитано
            min = Math.Min(min, v.left == null ? min : v.left.min);
            min = Math.Min(min, v.right == null ? min : v.right.min);
            v.size = size;
            v.min = min;
        }

        static Vertex ToStart(Vertex root, int left, int right) { //перемещение элементов с left по right
            // "в начало массива"
            Vertex[] fSplit = Split(root, right);
            Vertex[] sSplit = Split(fSplit[0], left - 1);
            Vertex result = Merge(Merge(sSplit[1], sSplit[0]), fSplit[1]);
            return result;
        }

        static Vertex Add(Vertex root, int x, int position) { //добавление элемента x на индекс position
            Vertex[] splittedTree = Split(root, position);
            return Merge(Merge(splittedTree[0], new Vertex(x)), splittedTree[1]);
        }

        static Vertex Min(Vertex root, int left, int right, ref int min) { //подсчет минимума на соотв. отрезке
            //для этого отрезаем соотв. поддерево и смотрим min в корне
            Vertex[] fSplit = Split(root, right);
            Vertex[] sSplit = Split(fSplit[0], left - 1);
            min = sSplit[1].min;
            root = Merge(Merge(sSplit[0], sSplit[1]), fSplit[1]);
            return root;
        }

        static void Push(Vertex root) { //проталкивание переворота вниз по дереву
            if (root == null || !root.reversed) return;

            if (root.left != null)
                root.left.reversed ^= true; //меняем на противоположное значение
            if (root.right != null)
                root.right.reversed ^= true;

            root.reversed = false;
            Vertex tmp = root.left;
            root.left = root.right;
            root.right = tmp;
        }

        static Vertex ReverseSegment(Vertex root, int left, int right) { //переворачиваем соотв. отрезок
            Vertex[] fSplit = Split(root, right);
            Vertex[] sSplit = Split(fSplit[0], left - 1);
            sSplit[1].reversed ^= true;
            return Merge(Merge(sSplit[0], sSplit[1]), fSplit[1]);
        }

        static void PrintTree(Vertex root) {
            if (root == null) return;
            if (!root.reversed) {
                PrintTree(root.left);
                Console.Write(root.number + " ");
                PrintTree(root.right);
            } else {
                PrintTree(root.right);
                Console.Write(root.number + " ");
                PrintTree(root.left);
            }
        }
    }

    public class Vertex {
        public Vertex left;
        public Vertex right;
        public int size;
        public int number;
        public int min; //минимум в дереве
        public bool reversed; //нужно ли перевернуть данное дерево

        public Vertex(int number) {
            this.left = null;
            this.right = null;
            this.size = 1;
            this.number = number;
            this.min = number;
            this.reversed = false;
        }
    }
}
