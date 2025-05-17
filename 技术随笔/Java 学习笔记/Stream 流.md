## Stream 简介
`Stream`将要处理的`元素集合`看作一种流，在流的过程中，借助`Stream API`对流中的元素进行操作，比如筛选、排序、聚合等。
"`Optional<T>` 是一个可以存放 `null` 或非 `null` 值的容器对象，如果 `Optional` 中有值，`.isPresent()` 方法会返回 `true`，可以使用 `.get()` 获取值。
### Java 中的集合
在 Java 中，集合框架是一组用于存储和操作对象的类和接口，主要位于 java.util 包中。
Java 集合框架分为两大体系：Collection 和 Map。
Collection 是一个接口，它是集合层次结构中的根接口，用于存储单个元素。有三个主要的子接口：List、Set 和 Queue。
Map 是一个接口，用于存储键值对（key-value）。键是唯一的，每个键最多映射到一个值。

## Stream 的创建
- 创建一个顺序流：`java.util.Collection.stream()`
- 创建一个并行流：`java.util.Collection.parallelStream()`
## Stream 常用方法
- 把顺序流转换成并行流：`.parallel()`
- 筛选出所有符合条件的元素：`.filter(x ->条件)`
- 控制台输出所有 Stream 流处理后的元素：`.forEach(System.out::println)`
- 获取顺序流中第一个元素：`Optional<T> findFirst()`
- 获取并行流中一个元素：`Optional<T> findAny()`
- 判断流中的元素是否满足指定条件：`boolean anyMatch(x -> 条件)`
- 将 Stream 流对象转换为 List`<T>`：List`<T>` collect(Collectors.toList())
- 获取 Stream 流中条件最大的元素：``Optional<T> max(Comparator.comparing(T::条件))`



