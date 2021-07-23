《算法4》中有关在Terminal中进行编译和运行的小Tips：

------

## GBK

错误信息：

```
Sort\priority_queue\TopM.java:11: 错误: 编码 GBK 的不可映射字符 (0x8C)
```

由于JDK是国际版的，我们在用javac编译时，编译程序首先会获得我们操作系统默认采用的编码格式（GBK），然后JDK就把Java源文件从GBK编码格式转换为Java内部默认的Unicode格式放入内存中，然后javac把转换后的Unicode格式的文件编译成class类文件，此时，class文件是Unicode编码的，它暂存在内存中，紧接着，JDK将此以Unicode格式编码的class文件保存到操作系统中形成我们见到的class文件。当我们不加设置就编译时，相当于使用了参数：javac -encoding GBK Test.java，就会出现不兼容的情况。
————————————————
版权声明：本文为CSDN博主「阿腾木」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_15989473/article/details/103997810



解决办法：加上 -encoding UTF-8

```
javac -encoding UTF-8 Sort\priority_queue\TopM.java
```

## 找不到主类

问题：

```java
package Sort.priority_queue;
```

当java文件中有这一行代码时，无法运行`.class`文件

具体原因可以看这篇blog：[使用java命令运行class文件提示“错误：找不到或无法加载主类“的问题分析 - 大C - 博客园 (cnblogs.com)](https://www.cnblogs.com/wangxiaoha/p/6293340.html)

总结：

1. 我们应该按照package定义的路径来存放源文件
2. 一个类的全名是 包名+类名
3. 避免路径重复，在包名的上一级目录的Terminal中执行命令
