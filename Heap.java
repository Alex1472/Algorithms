import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class NimGame {
    static PrintWriter pw;

    static {
        try {
            pw = new PrintWriter(new FileOutputStream(new File("output.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Heap. В корне хранится масимум
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(new File("input.txt")));
        int n = scanner.nextInt();
        int[] numbers = new int[n + 1];
        for (int i = 1; i <= n; ++i)
            numbers[i] = scanner.nextInt();

        Heap heap = new Heap(numbers);

        int[] result = new int[n + 1];

        for (int i = n; i >= 1; --i) {
            result[i] = heap.extractMax();

        }
        for (int i = 1; i <= n; ++i)
            pw.print(result[i] + " ");
        pw.close();
    }
}
class Heap {
    public int[] heap;
    public int free;
    public Heap(int n) {
        this.heap = new int[n + 1];
        this.free = 1;
    }
    void buildHeap() {
        for (int i = free - 1; i >= 1; --i)
            siftDawn(i);
    }

    public Heap(int[] heap) {
        this.heap = heap;
        this.free = heap.length;
        buildHeap();
    }

    public void  siftUp(int index) {
        if (heap[index / 2] < heap[index]) {
            int tmp = heap[index / 2];
            heap[index / 2] = heap[index];
            heap[index] = tmp;
            siftUp(index / 2);
        }
    }

    public void siftDawn(int index) {

        int maxIndex = index;
        if ((2*index < free ) && (heap[2 *index] > heap[maxIndex]))
            maxIndex = 2*index;
        if ((2*index + 1 < free) && (heap[2 *index + 1] > heap[maxIndex]))
            maxIndex = 2*index + 1;

        if (index == maxIndex)
            return;

        int tmp = heap[index];
        heap[index] = heap[maxIndex];
        heap[maxIndex] = tmp;
        siftDawn(maxIndex);
    }

    public int extractMax() {
        int value = heap[1];
        --free;
        if (free == 1) {
            return value;
        }
        heap[1] = heap[free];
        siftDawn(1);
        return value;
    }

    public void add(int x) throws Exception {
        if (free == heap.length)
            throw new Exception("Limit heap memory");
        heap[free] = x;
        ++free;
        siftUp(free - 1);
    }

    public void printHeap() {
        if (free == 1)
            return;
        for (int i = 1; i < free; ++i)
            NimGame.pw.print(heap[i] + " ");
        NimGame.pw.println();
    }
}




