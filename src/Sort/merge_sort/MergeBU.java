package Sort.merge_sort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 自底向上的归并排序：
 * 和通过递归的自顶向下相比较，只是sort方法改变，归并方法一模一样
 */


public class MergeBU {
    private static Comparable[] aux;  // 声明辅助数组aux

//    public static void sort(Comparable[] a){
////        排序算法
//        aux = new Comparable[a.length];  //给辅助数组aux申请内存空间
//        sort(a,0, a.length-1);  //调用私有方法递归
//    }

    private static void sort(Comparable[] a){      //自底向上
        int N = a.length;
        aux = new Comparable[N];
        for(int sz = 1; sz < N; sz = sz*2){
            for(int lo = 0;  lo < N-sz; lo = lo+2*sz){   // [lo, lo+sz-1][lo+sz, lo+2sz-1]  [lo+2sz]       lo+sz-1 必须要小于（不能等于）N-1,否则没有合并的必要 lo+sz-1 < N-1  ∴ lo < N-sz
                merge(a, lo, lo+sz-1, Math.min(lo+2*sz-1, N-1));
            }
        }
    }

    /**
     * 将a[lo...mid]和a[mid+1...hi]合并
     * 需要保证两个子数组都已经有序
     * @param a：待排序数组
     * @param lo：前面的子数组的第一个元素的索引
     * @param mid：第一个子数组的最后一个元素的索引
     * @param hi：第二个子数组的最后一个元素的索引
     */
    public static void merge(Comparable[] a, int lo, int mid, int hi){
        int i = lo;   //左子数组的当前元素
        int j = mid+1;  //右子数组的当前元素

        for(int k=lo; k<= hi; k++){   // 将a的内容复制进aux, 后面直接将值覆盖进a
            aux[k] = a[k];
        }

        for(int k=lo; k<=hi; k++){
            /**
             * k为a的当前元素
             * 四种判断条件
             * 1.左边小于右边，取左
             * 2.右边小于左边，取右
             * 3.左边已经用完，取右
             * 4.右边已经用完，取左
             */
            if(i > mid) a[k] = aux[j++];   // 左边已用完
            else if(j > hi) a[k] = aux[i++];  // 右边已用完
            else if(less(aux[i], aux[j])) a[k] = aux[i++]; // 左边＜右边
            else a[k] = aux[j++]; // 右边 ≤ 左边
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
