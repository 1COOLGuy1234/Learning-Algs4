package Union_Find;

public class quickUnionUF
{
    private int[] id;

    public quickUnionUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++) id[i]=i;
    }

    private int root(int i)  //找根节点
    {
        int temp = i;
        while(id[temp] != temp)
        {
            temp = id[temp];
        }
        return temp;
    }

    public boolean connected(int p, int q)
    {
        return (root(p)==root(q));
    }

    public void union(int p, int q)
    {
        int i = root(p);   //是要连接2点的根节点
        int j = root(q);
        id[i] = j;  // j成为i的父节点
    }
}
