import java.io.*;

public class NimGame {

    public static void main(String[] args) throws IOException {

        Vertex root = new Vertex();
        BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("output.txt")));

        while (true) {
            String[] tmp = br.readLine().split(" ");
            if (tmp[0].equals("#"))
                break;
            if (tmp[0].equals("+"))
                root.add(tmp[1]);
            if (tmp[0].equals("?"))
                if (root.find(tmp[1]))
                    bw.write("YES\n");
                else
                    bw.write("NO\n");
            if (tmp[0].equals("-"))
                root.delete(tmp[1]);
        }
        br.close();
        bw.close();
    }
}

class Vertex {
    public boolean flag;
    public Vertex[] next = new Vertex[26];
    //Храним реьра в массиве next, еслт ребра нет, то соотв. ссылка - null
    //Данная реализация для строчных латинских букв 

    public boolean find(String s) {
        Vertex t = this;
        for (int i = 0; i< s.length(); ++i) {
            if (t.next[s.charAt(i) - 'a'] == null)
                return false;
            t = t.next[s.charAt(i) - 'a'];
        }
        return t.flag;
    }

    public void add(String s) {
        Vertex t = this;
        for (int i = 0; i < s.length(); ++i) {
            if (t.next[s.charAt(i) - 'a'] == null)
                t.next[s.charAt(i) - 'a'] = new Vertex();
            t =  t.next[s.charAt(i) - 'a'];
        }
        t.flag = true;
    }

    public void delete(String s) {
        Vertex t = this;
        for (int i = 0; i < s.length(); ++i) {
            if (t.next[s.charAt(i) - 'a'] == null)
                return;
            t = t.next[s.charAt(i) - 'a'];
        }
        t.flag = false;
    }
}

