# 并查集（union-find）

## Quick Find

数据结构：

一个整数数组，size = N

算法描述：

1. 使用一个简单的一维数组id[]描述若干个obj之间的连通关系
2. 当id[a] == id[b]时，a与b相连通
3. 当执行union（a,b）时，将id[b]的值赋给id[a], 以及所有值等于id[a]的element。
4. connected(a,b)判断a与b是否相连接时，只需要判断if(id[a] == id[b])

## Quick Union

数据结构：

一个整数数组，size = N

解释：

1. 将一个整数数组转化成一棵树，id[a]的值为a的父节点；
2. 如果a为根节点，则id[a] == a 

判断p和q是否连接：

Check if p and q have the same root.

> 执行union(p,q)操作时，是连接两个节点的根节点。 



## Weighted Quick Union

数据结构：

和quick union相比，需要一个额外的array sz[i]来计算根节点为i的树里面的对象个数

