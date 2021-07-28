package Sort.priority_queue;

import edu.princeton.cs.algs4.IndexMinPQ;

public class MaxPQ<Key extends Comparable<Key>> {     /** 这里看不明白*/
    private Key[] pq;    // 基于堆的完全二叉树
    private int N = 0;  // PQ索引范围为[1,N], 0不使用

    public MaxPQ(int maxN){    // Constructor
        pq = (Key[]) new Comparable[maxN+1];
    }

    public boolean isEmpty(){
        return N==0;
    }

    public int size(){
        return N;
    }

    public void insert(Key v){
        pq[++N] = v;
        swim(N);   //让新插入的元素浮动到他应该在的位置
    }

    public Key delMax(){
        Key max = pq[1];  // 根结点最大
        exch(1,N--); // 把最大元素交换末尾，再删掉
        pq[N+1] = null;  // 防止对象游离
        sink(1); // 让被交换的元素回到应该的位置
        return max;
    }

    private boolean less(int i, int j){
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j){
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private void swim(int k){
        while(k>1 && less(k/2, k)){
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k){
        while(2*k <= N){  // k存在子结点
            int j = 2*k;  // j为目标子结点，初始时为左子结点
            // 选出左右子结点中较大的那个
            if(j!=N && less(j,j+1))  j++;
            if(!less(k, j)) break; //循环终止条件
            exch(k,j);
            k = j;
        }
    }
}
