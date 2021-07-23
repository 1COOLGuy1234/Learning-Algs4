package Sort.priority_queue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Heap {

    // This class should not be instantiated.
    private Heap() { }

    public static void sort(Comparable[] pq) {
        int N = pq.length; // 堆的大小

        /** 构造二叉堆*/
        for (int k = N/2; k>=1; k--){  //找到第一个非叶子结点 最后一个叶子节点是N。那么他的父结点为N/2
            sink(pq, k, N);
        }
        /** 下沉排序*/
        while (N>1){
            exch(pq, 1, N--); //将最大元素和堆末尾交换，堆容量减1，这样最大元素就在最后面了
            sink(pq,1, N);  //恢复堆有序
        }

    }

    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/
    private static void sink(Comparable[] pq, int k, int N){
        while (2*k <= N){ // 如果k没有叶子结点，没地方sink
            int j = 2*k;  // j为k的左子结点
            if(j+1<=N && less(pq, j, j+1)){ // j+1<=N可以优化成：j<N
                j++;
            }
            if(!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j){
        return pq[i-1].compareTo(pq[j-1]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable temp = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = temp;
    }

    private static void show(Comparable[] a){
        //在单行中打印数组
        for (int i=0; i< a.length; i++){
            StdOut.print(a[i]+" ");
        }
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a){
        //测试数组是否有序
        for(int i=1; i < a.length; i++){
            if(less(a, i, i-1)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        String[] a = new In("tiny.txt").readAllStrings();
        show(a);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
