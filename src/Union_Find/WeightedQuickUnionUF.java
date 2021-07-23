package Union_Find;

public class WeightedQuickUnionUF
{
    /*
     * 加入一个sz数组记录树的大小
     * */
    private int[] id;
    private int[] sz;
    private int count; // 连通分量的数量

    public WeightedQuickUnionUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
        {
            id[i] = i;
        }
        sz = new int[N];
        for (int i = 0; i < N; i++)
        {
            sz[i] = 1;
        }
        count = N;
    }

    public int count()  //返回count
    {
        return count;
    }

    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }

    public int root(int i)
    {
        while(i != id[i])
        {
            i = id[i];
        }
        return i;
    }

    public void union(int p, int q)
    {
        int i = root(p);
        int j = root(q);
        if (connected(i,j))
        {
            return;
        }
        if(sz[i]<=sz[j])  //将i连到j下面 同时sz[j]加上i的尺寸
        {
            id[i] = j;
            sz[j] += sz[i];
        }
        else
        {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;
    }
}
