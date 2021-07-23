package Sort.basic_sort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Shell Sort is based on Insertion Sort
 * 思想：
 * 使数组中任意间隔h的元素都是有序的
 * i.e.:
 * 1.先取一个h间隔，然后给整个数组分组，每个组内的元素较少
 * 2.给每个组内的元素使用插入排序，使其变成有序子数组
 * 3.之后，缩小h（增大每个子数组的容量）重复1,2；直到h=1
 */

public class Shell {
    public static void sort(Comparable[] a){
//        排序算法
        int N = a.length;
        int h = 1;
        while(h < N/3) h = 3*h+1; //将h调整至合适的值
        while(h>=1){
            //将数组变为h有序
            for(int i=h; i<N; i++){
                for(int j=i; j>=h && less(a[j],a[j-h]); j-=h){
                    exch(a,j,j-h);
                }
            }
            h = h/3;
        }
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
