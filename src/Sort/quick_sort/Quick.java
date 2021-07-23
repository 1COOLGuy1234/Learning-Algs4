package Sort.quick_sort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Quick {
    public static void sort(Comparable[] a){
        StdRandom.shuffle(a);    //打乱数组, 消除对输入的依赖
        sort(a, 0, a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);  //排 切割点左边的
        sort(a, j+1, hi);  //排 切割点右边的
    }

    private static int partition(Comparable[] a, int lo, int hi){
        // 将数组切分为a[lo,...,i-1]   a[i]  a[i+1,...,hi]
        int i = lo, j = hi+1;   //左右扫描指针   其中i会从lo+1开始扫，j会从hi开始扫（所以j初始值为hi+1）
        Comparable v = a[lo];  //切分元素v
        while(true){
            while(less(a[++i], v)){  //向右扫描直到遇到第一个大于等于v的元素
                if(i == hi) break;
            }
            while(less(v, a[--j])){  //向左扫描直到遇到第一个小于等于v的元素
                if(j == lo) break;
            }
            if(i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j); //此时，j的位置就是切分元素应该在的位置
        return j;
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
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
            if(less(a[i],a[i-1])){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        //从标准输入读取字符串，将他们排序并输出
        String[] a = new In("tiny.txt").readAllStrings();
        show(a);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}