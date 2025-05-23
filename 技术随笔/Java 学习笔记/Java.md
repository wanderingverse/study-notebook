# JDK（Java Development Kit）
Java 开发工具包。包括 JRE 和开发工具（如 javac 编译器、javadoc、调试器等），用于开发和编译 Java 程序。
## JRE（Java Runtime Environment）
Java 运行时环境。包括 JVM 和运行 Java 程序所需的类库，用于运行 Java 程序。
### JVM（Java Virtual Machine）
Java 虚拟机。负责执行字节码（.class）文件，实现 Java 的跨平台特性。
# Java 语法
### 常用类库
#### Object
##### Object 简介
##### Object 常用方法

#### Stream
##### Stream 简介
`Stream`将要处理的`元素集合`看作一种流，在流的过程中，借助`Stream API` 对流中的元素进行操作，比如筛选、排序、聚合等。
`Optional<T>` 是一个可以存放 `null` 或非 `null` 值的容器对象，如果 `Optional` 中有值，`.isPresent()` 方法会返回 `true`，可以使用 `.get()` 获取值。
##### Java 中的集合
在 Java 中，集合框架是一组用于存储和操作对象的类和接口，主要位于 java.util 包中。
Java 集合框架分为两大体系：Collection 和 Map。
Collection 是一个接口，它是集合层次结构中的根接口，用于存储单个元素。有三个主要的子接口：List、Set 和 Queue。
Map 是一个接口，用于存储键值对（key-value）。键是唯一的，每个键最多映射到一个值。

##### Stream 的创建
- 创建一个顺序流：`java.util.Collection.stream()`
- 创建一个并行流：`java.util.Collection.parallelStream()`
##### Stream 常用方法
- 把顺序流转换成并行流：`.parallel()`
- 筛选出所有符合条件的元素：`.filter(x ->条件)`
- 控制台输出所有 Stream 流处理后的元素：`.forEach(System.out::println)`
- 获取顺序流中第一个元素：`Optional<T> findFirst()`
- 获取并行流中一个元素：`Optional<T> findAny()`
- 判断流中的元素是否满足指定条件：`boolean anyMatch(x -> 条件)`
- 将 Stream 流对象转换为 List`<T>`：List`<T>` collect(Collectors.toList())
- 获取 Stream 流中条件最大的元素：``Optional<T> max(Comparator.comparing(T::条件))`
### 字符串
#### 字符串拼接
- `MessageFormat`：`MessageFormat.format("字符串0：{0}；字符串1：{1}",stringNo1,stringNo2`
- `StringBuilder`：性能高（尤其是需要多次拼接时）
```java
StringBuilder sb = new StringBuilder();
sb.append("字符串0：").append(stringNo1)
  .append("；字符串1：").append(stringNo2);
```
- `StringBuffer`：线程安全，适用于多线程环境（加锁），性能比 `StringBuilder` 略低
```java
StringBuffer sb = new StringBuffer();
sb.append("字符串0：").append(stringNo1)
  .append("；字符串1：").append(stringNo2);
```
### 异常
#### Exception
受检异常（Checked Exception）：继承自 Exception 类的异常。受检异常要求程序员必须要显式处理。如果某个方法可能抛出受检异常，程序员必须使用 try-catch 语句块来捕获异常并处理，或者在方法签名中使用 throws 关键字将异常抛出，交给调用该方法的代码去处理。
#### RuntimeException
非受检异常（unchecked exception）：继承自 RuntimeException 类的异常，又称运行时异常。非受检异常不必要被显式捕获或声明才能抛出。编译器不会强制要求程序员在代码中显式地捕获或声明这些异常，程序员可以选择不处理这些异常，而是让它们在运行时直接抛出。
##### NullPointerException
##### ArrayIndexOutOfBoundsException
##### ArithmeticException