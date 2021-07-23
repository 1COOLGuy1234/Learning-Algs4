package Union_Find;

public class quickFindUF {
    /*
     * 需要O(n^2)的时间复杂度，对于仅仅查询是否连通来说，这太慢了
     * */
    private int[] id;

    public quickFindUF(int N){      // Constructor (构造函数)
        id = new int[N];
        for(int i=0; i < N; i++){
            id[i] = i;
        }
    }

    public boolean connected(int p, int q){
        return (id[p]==id[q]);
    }

    public void union(int p, int q){
        int pid = id[p];    // 需要先取出pid和qid的值
        int qid = id[q];
        for(int i=0; i<id.length; i++){
            if(id[i] == pid){
                id[i] = qid;
            }
        }
    }
}

