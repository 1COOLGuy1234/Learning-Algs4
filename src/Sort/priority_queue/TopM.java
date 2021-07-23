package Sort.priority_queue;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Transaction;

public class TopM {
    public static void main(String[] args){
        //打印输入流中的最大的M行
        int M = Integer.parseInt(args[0]);  //Integer.parseInt的作用是将String转化为Int
        MinPQ<Transaction> pq = new MinPQ<Transaction>(M+1);  // M+1的+1是那个要被删除的元素
        while (StdIn.hasNextLine()){   // 为下一行输入创建一个元素并放入优先队列中
            String line = StdIn.readLine();
            Transaction transaction = new Transaction(line);
            pq.insert(transaction);
            if (pq.size() > M) pq.delMin();  // 如果优先队列中存在M+1个元素，则删除其中最小的元素，保持在插入前队列中永远只有M个元素
        }
        // while循环结束后，最大的M个元素都在优先队列中
        // 接下来，将这M个元素从小到大压入栈内
        Stack<Transaction> stack = new Stack<Transaction>();
        while (!pq.isEmpty()) {
            stack.push(pq.delMin());
        }
        for (Transaction t: stack){
            StdOut.println(t);  // 将栈内元素打印出来, 入栈是从小到大，所以出栈是从大到小
        }

    }
}

