package Search.symbol_table;

import Fundamental.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Sequential Search Symbol Table 意思是顺序查找的符号表, 是无序的
 * 依赖Linked List实现
 * 依赖{@code equals()} 比较键值
 * */


public class SequentialSearchST<Key, Value> {
    private int n;  // 键值对数量
    private Node first; //键值对的链表

    // 一个辅助链表数据类型
    private class Node{
        private Key key;
        private Value val;
        private Node next;

        public Node(Key key, Value val, Node next){
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Initialize an empty Symbol Table
     * */
    public SequentialSearchST(){
    }

    /**
     * @return the number of key-value pairs in this symbol table
     */
    public int size(){
        return n;
    }

    /**
     *
     * @return {@code true} if this symbol table is empty
     *         {@code false} otherwise
     */
    public boolean isEmpty(){
        return n==0;
    }


    /**
     * Return true if this symbol table contains the specified key.
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key}
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key){
        if (key==null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key in this Symbol Table
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key){
        if (key==null) throw new IllegalArgumentException("argument to get() is null");
        for (Node x = first; x != null; x = x.next){
            if(key.equals(x.key)){
                return x.val;
            }
        }
        return null;
    }

    /**
     * Insert the specified key-value pair into ST
     * Overwrite the old value if the key is already in the ST
     * Delete the specified key(and its associated value) from the ST if {@code val} is {@code null}
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val){
         if (key==null) throw new IllegalArgumentException("first argument to put() is null");
         if (val==null){
             delete(key);
             return;
         }
         // Overwrite
         for (Node x = first; x != null; x = x.next){
             if(key.equals(x.key)){
                 x.val = val;
                 return;
             }
         }

         first = new Node(key,val,first);  // 成为新的first problem: how about "sequential"?
         n++;
    }

    /**
     * Remove the specified key and its associated value from this ST (if the key is in the ST)
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key){
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        first = delete(first, key);
    }

    // delete key in linked list beginning at Node x
    // warning: function call stack too large if table is large
    private Node delete(Node x, Key key){
        if (x == null) return null; // if the linked list has already been gone over, return null
        if (key.equals(x.key)){  // if the current Node x is the target
            n--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    /**
     * Returns all keys in the ST as an {@code Iterable}
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keys(){
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first; x != null; x = x.next){
            queue.enqueue(x.key);
        }
        return queue;

    }



    public static void main(String[] args){
        SequentialSearchST<String,Integer> st = new SequentialSearchST<String, Integer>();
        for (int i=0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s: st.keys()){
            StdOut.println(s + " " + st.get(s));
        }
    }
}
