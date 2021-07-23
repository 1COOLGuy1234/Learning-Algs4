package Sort.priority_queue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdOut;


/**
 * 本例中，输入的三个文件对应 streams[0], streams[1], streams[2]
 * streams[i].readString() 在读取完对应字符串后，会自动跳转到下个字母，下次再执行该函数，读取的就是下个字符串
 *
 * */
public class Multiway {

    // This class should not be instantiated.
    private Multiway() { }

    public static void merge(In[] streams){
        int N = streams.length;
        IndexMinPQ<String> pq = new IndexMinPQ<String>(N);

        // 初始化
        for (int i=0; i < N; i++){
            if(!streams[i].isEmpty()){
                pq.insert(i, streams[i].readString());
            }
        }

        //
        while (!pq.isEmpty()){
            StdOut.println(pq.minKey());
            int i = pq.delMin();
            if (!streams[i].isEmpty()){
                pq.insert(i, streams[i].readString());
            }
        }
    }

    public static void main(String[] args){
        int N = args.length;
        In[] streams = new In[N];
        for (int i = 0; i < N; i++){
            streams[i] = new In(args[i]);
        }
        merge(streams);
//        System.out.println(streams[0].readString());
    }

}
