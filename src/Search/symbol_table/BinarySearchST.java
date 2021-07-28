package Search.symbol_table;


import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

/**
 *  The {@code BST} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, <em>select</em>, and <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  <p>
 *  This implementation uses a <em>sorted array</em>.
 *  The <em>put</em> and <em>remove</em> operations take &Theta;(<em>n</em>)
 *  time in the worst case.
 *  The <em>contains</em>, <em>ceiling</em>, <em>floor</em>,
 *  and <em>rank</em> operations take &Theta;(log <em>n</em>) time in the worst
 *  case.
 *  The <em>size</em>, <em>is-empty</em>, <em>minimum</em>, <em>maximum</em>,
 *  and <em>select</em> operations take &Theta;(1) time.
 *  Construction takes &Theta;(1) time.
 *  <p>
 *  For alternative implementations of the symbol table API,
 *  see {@link ST}, {@link BST}, {@link SequentialSearchST}, {@link RedBlackBST},
 *  {@link SeparateChainingHashST}, and {@link LinearProbingHashST},
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/31elementary">Section 3.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */


public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2; // 数组的初始容量
    private Key[] keys;
    private Value[] vals;
    private int n = 0; // ST的键值对数量

    /**
     * Initialize an empty symbol table.
     */
    public BinarySearchST(){
        this(INIT_CAPACITY); // this()是调用下面的那个Instructor
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     * @param capacity the maximum capacity
     */
    @SuppressWarnings("unchecked")
    private BinarySearchST(int capacity){
        keys = (Key[]) new Comparable[capacity];  // 不明白这里的(Key[])是啥意思，猜是强制转换接口的数据类型
        vals = (Value[]) new Object[capacity];
    }

    /**
     * resize the underlying arrays
     *
     * @param capacity the new capacity
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity){
        assert capacity >= n;  // 容量必须大于等于现在的数量
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++){
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        keys = tempk;
        vals = tempv;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key in this symbol table.
     * 无序表中，是顺序查找。这里是利用{@code rank()}查找
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        if (isEmpty()) return null;  // new added than "SequentialSearchST"
        int i = rank(key); // rank() 函数给予二分查找
        /**
         * 1. i < n 是用来判断第二个判断条件keys[i]是否越界的, 容易漏
         * 2. 第二个判断条件是判断是否该key是否在ST中
         * */
        if (i < n && keys[i].compareTo(key) == 0) return vals[i];
        return null;
    }

    /**
     * Returns the number of keys in this symbol table strictly less than {@code key}.
     * 整个类的核心方法，based in BinarySearch
     *
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");

        int lo = 0, hi = n-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if      (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val){
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null){
            delete(key);
            return;
        }

        int i = rank(key);

        // key is already in table
        if(i < n && keys[i].compareTo(key) == 0){
            vals[i] = val;
            return;
        }

        // key is not in table, insert new key-value pair
        if(n == keys.length){  // 如果容量已满
            resize(2*keys.length);
        }
        // keys[i]..keys[n-1]全部向后平移
        for(int j = n; j > i; j--){
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        // 此时keys[i] vals[i]已空出，可以直接overwrite
        keys[i] = key;
        vals[i] = val;
        n++;

        assert check(); // 后面会写
    }

    /**
     * Removes the specified key and associated value from this symbol table
     * (if the key is in the symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key){
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (isEmpty()) return;

        // compute rank
        int i = rank(key);

        // key is not in the ST
        if(i==n || keys[i].compareTo(key) != 0){  // key如果比所有键都大，i==n是可能的，这样会造成越界
            return;
        }

        // key in the ST, keys[i+1]..keys[n-1]全部向前平移
        for(int j=i; j < n-1; j++){
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }

        // to avoid loitering
        keys[n-1] = null;
        vals[n-1] = null;

        // size-1
        n--;

        assert check();
    }

    /**
     * Removes the smallest key and associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table underflow error!");
        delete(min());
    }

    /**
     * Removes the largest key and associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table underflow error!");
        delete(max());
    }

    /***************************************************************************
     *  Ordered symbol table methods.
     ***************************************************************************/

    /**
     * Returns the smallest key in this symbol table.
     *
     * @return the smallest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key min(){
        if(isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return keys[0];
    }

    /**
     * Returns the largest key in this symbol table.
     *
     * @return the largest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key max(){
        if(isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return keys[n-1];
    }

    /**
     * Return the kth smallest key in this symbol table.
     *
     * @param  k the order statistic
     * @return the {@code k}th smallest key in this symbol table
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *        <em>n</em>–1
     */
    public Key select(int k){
        if (k < 0 || k > n-1){
            throw new IllegalArgumentException("called select() with invalid arugment:" + k);
        }
        return keys[k];
    }

    /**
     * Returns the largest key in this symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in this symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key){
        if(key == null) throw new IllegalArgumentException("arguement to floor() is null");
//        if(!contains(key)) throw new NoSuchElementException("No Such Element"); // 不用contains是因为开销太大
        int i = rank(key);  // i in [0,n]
        if(i == 0){  // 他是最小的，此时无返回值，抛出异常
            throw new NoSuchElementException("arguement to floor() is too small");
        }
        else if(i < n && keys[i].compareTo(key)==0){
            return keys[i];
        }
        else{
            return keys[i-1];
        }
    }

    /**
     * Returns the smallest key in this symbol table greater than or equal to {@code key}.
     *
     * @param  key the key
     * @return the smallest key in this symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key){
        if(key == null) throw new IllegalArgumentException("arguement to ceiling() is null");
        int i = rank(key);  // i in [0,n]
        if(i == n){
            throw new NoSuchElementException("argument to ceiling() is too large");
        }
        else{
            return keys[i];
        }
    }

    /**
     * Returns the number of keys in this symbol table in the specified range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in this symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public int size(Key lo, Key hi){
        // 检查 lo 和 hi不能为null
        if(lo == null || hi == null){
            throw new IllegalArgumentException("arguments to size exist null");
        }
        // 检查 lo <= hi
        if (lo.compareTo(hi) > 0) return 0;

        int i = rank(lo); // i, j in [0,n]
        int j = rank(hi);

//        if(j < n && keys[j].compareTo(hi)==0){
//            return j-i+1;
//        }
        //上面的应该也可，下面的是书上的标准代码
        if (contains(hi)) return j-i+1;
        else return j-i;
    }

    /**
     * Returns all keys in this symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in this symbol table
     */
    public Iterable<Key> keys(){
        return keys(min(),max());
    }

    /**
     * Returns all keys in this symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in this symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi){
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo); i < rank(hi); i++){
            queue.enqueue(keys[i]);
        }
        if(contains(hi)){
            queue.enqueue(keys[rank(hi)]);
        }

        return queue;
    }

    /***************************************************************************
     *  Check internal invariants.
     ***************************************************************************/
    private boolean check(){
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++){
            if(keys[i].compareTo(keys[i-1]) < 0) return false;
        }
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck(){
        for(int i = 0; i < size(); i++){
            if(rank(select(i)) != i){
                return false;
            }
        }
        for (int i = 0; i < size(); i++){
            if (keys[i].compareTo(select(rank(keys[i]))) != 0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        for (int i=0; !StdIn.isEmpty(); i++){
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s: st.keys()){
            StdOut.println(s + " " + st.get(s));
        }
    }

}
