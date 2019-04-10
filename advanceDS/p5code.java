package com.cube;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static int total = 500;
    public static int heapSize = 0;
    public static int charNum;      // the number of the character
    static class TrieNode
    {
        public char val;
        public boolean isWord;
        public boolean isChar;
        public TrieNode[] children = new TrieNode[3];
        public TrieNode()
        {

        }
        TrieNode(char c){
            TrieNode node = new TrieNode();
            node.val = c;
            isChar = true;
        }

    }

    public static TrieNode root = new TrieNode();

    public static boolean insert(String word) {
        TrieNode ws = root;
        boolean isPrefixFlag = false;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(ws.isWord && i < word.length() - 1)//is others' prefix
            {
                isPrefixFlag = true;
            }

            if (ws.children[c - '0'] == null) {
                ws.children[c - '0'] = new TrieNode(c);
                ws = ws.children[c - '0'];
            }

            if(((ws.children[0] != null && ws.children[0].isChar)  ||(ws.children[i] != null && ws.children[1].isChar)) && i == word.length() - 1)
            {
                isPrefixFlag = true;
            }
        }
        ws.isWord = true;
        return isPrefixFlag;
    }


    public static int ComputeWPL(int heap[])
    {
        int minHeap[] = new int[total];
        heapSize = 1;

        for (int i = 1; i < charNum; i++)
            minHeap[heapSize++] = heap[i];
        minHeap[heapSize] = heap[charNum];
        MinHeap(minHeap);

        int WPL = 0;
        for (int i = 1; i < charNum; i++) {
            int leftWeight = DeleteMinNode(minHeap);
            int rightWeight = DeleteMinNode(minHeap);
            int rootWeight = leftWeight + rightWeight;
            WPL += rootWeight;
            MinHeapInsert(minHeap, rootWeight);
        }
        return WPL;
    }
    public static void HeapPercolateDown(int heap[], int parent)
    {
        // percolate down
        int temp = heap[parent];
        int i, child;

        for (i = parent; i*2 <= heapSize; i = child){
            child = 2 * i;
            if (child != heapSize && heap[child+1] < heap[child])
                child++;
            if (temp > heap[child])
                heap[i] = heap[child];  //percolate down
            else
                break;
        }
        heap[i] = temp;
    }
    static void MinHeap(int heap[])
    {
        for (int i = heapSize / 2; i > 0; i--)
            HeapPercolateDown(heap, i);
    }
    static int DeleteMinNode(int heap[])
    {
        int minElem = heap[1];
        heap[1] = heap[heapSize];
        heapSize--;
        HeapPercolateDown(heap, 1);
        return minElem;
    }

    static void MinHeapInsert(int heap[], int weight)
    {
        int i;
        for (i = ++heapSize; i > 0 && heap[i/2] > weight; i /= 2)
            heap[i] = heap[i/2];
        heap[i] = weight;
    }
    static int WeightMulLength(int f[], String[] code)
    {
        int WPL = 0;
        for (int i = 1; i <= charNum; i++)
            WPL += f[i] * code[i].length();  //the weight multiply the length
        return WPL;
    }
    static boolean IsUniquePrefix(String []code)
    {
        for (int i = 1; i <= charNum; i++)    // O(n^2)
        {
            if(insert(code[i]))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isalpha(char c)
    {
        return (c <= 'Z' && c >= 'A') || (c <= 'z' && c >= 'a');
    }

    public static boolean isdigit(char c)
    {
        return c <= '9' && c >= '0';
    }
    public static void main(String[] args) {
        root.val = ' ';
        // write your code here
        int caseNum = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //System.out.println("caseNum : " + caseNum);
            caseNum++;
            char ch;
            String[] code = new String[total];
            int frequency[] = new int[total];
            charNum = scanner.nextInt();
            //System.out.println("n : " + n);
            if(charNum == 0)  // exit the input when read 0
                break;
            for (int i = 1; i <= charNum; i++) {
                ch = scanner.next().charAt(0);
                if (isalpha(ch) || isdigit(ch) || ch == '_') {
                    frequency[i] = scanner.nextInt();
                    //System.out.println("frequency : " + frequency[i]);
                }
            }
            int minWPL = ComputeWPL(frequency); // get the min WPL

            int stusNum;
            stusNum = scanner.nextInt();
            System.out.println("Case "+ caseNum +" :");
            for (int i = 0; i < stusNum; i++) {
                for (int j = 1; j <= charNum; j++) {
                    ch = scanner.next().charAt(0);
                    if (isalpha(ch) || isdigit(ch) || ch == '_') {
                        code[j] = scanner.next();
                    }

                }
                int thisWPL = WeightMulLength(frequency, code);
                //System.out.println("minWPL : " + minWPL + " thisWPL : " + thisWPL);
                //System.out.println(" prefix : " + IsUniquePrefix(code) + " equal WPL : " + (thisWPL == minWPL));
                if (thisWPL == minWPL && IsUniquePrefix(code))
                    System.out.println("Yes");
                else
                    System.out.println("No");
            }

        }
        return;
    }
}
