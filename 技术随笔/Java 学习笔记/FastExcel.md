# 介绍
FastExcel 是一个 Java 库，专为需要高性能和低内存占用的 Excel 文件处理而设计。适用处理大规模的 Excel 数据。
# 使用文档
### 编写数据类
编写用于读写 Excel 数据的实体类。实体类中每个使用 `@ExcelProperty()` 注解标识的字段，都表示 Excel 中的一个列标题，字段的值表示一个单元格数据。
注意：数据类是一个 `POJO` 类，且必须拥有 `getXxx()`、`setXxx()`、`equals()`、`hashCode()` 方法。
可使用 `lombok` 的 `@Data` 注解或 `@Getter` + `@Setter` + `@EqualsAndHashCode` 组合。
#### `@ExcelProperty()` 注解
该注解支持 2 种参数：
- `@ExcelProperty(列标题名)`。表示按指定的列名读取。
- `@ExcelProperty(index = 列索引)`。表示按指定的列下标读取。其中，`列索引` 为大于等于 0 的正整数。
```java
@Data
public class DemoData {  
    @ExcelProperty("姓名")  
    private String name;  
      
    @ExcelProperty("年龄")  
    private int age;  
      
    @ExcelProperty("家庭地址")  
    private String address;  
}
```
### 读取 Excel 文件
#### 编写数据监听器
编写一个实现 `ReadListener<T>` 接口的 `Java` 类作为自定义数据监听器，处理从 Excel 中读取的数据。
- 重写 `void invoke()` 方法。
- 重写 `void doAfterAllAnalysed()` 方法。
```java
@Slf4j  
public class ReadExcelListener implements ReadListener<DemoData> {  
  
    @Override  
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {  
        log.info("解析到一条数据:{}", JSON.toJSONString(demoData));  
    }  
  
    @Override  
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {  
        log.info("所有数据解析完成！");  
    }  
}
```
#### 读取 Excel 内容
```java
public static void main(String[] args) {
    String filename = "demo.xlsx";  
    FastExcel.read(filename, DemoData.class, new ReadExcelListener()).sheet().doRead();
}
```
#### 注意
数据监听器在每次使用时都应当手动创建新实例，不能交由 Spring 管理。以保证数据隔离与线程安全。
### 创建 Excel 文件
#### 向数据类填充数据
```java
public static List<DemoData> writeData() {  
    List<DemoData> demoDataList = new ArrayList<>();  
  
    for (int i = 0; i < 10; i++) {  
        // 写入数据  
        DemoData demoData = new DemoData();  
        demoData.setName("张三");  
        demoData.setAge(12);  
        demoData.setAddress("北京");  
  
        demoDataList.add(demoData);  
    }  
    return demoDataList;  
}
```
#### 写入 Excel 并生成文件
```java
String filename = "demo.xlsx";  
FastExcel.write(filename, DemoData.class).sheet("模板1").doWrite(writeData());
```
### 填充写入 Excel 文件
基于模板文件填充数据到 Excel 。
#### 编写模板文件
模板文件是一个 Excel 文件，要填充的数据，在模板文件中使用 `{字段名}` 作为占位符。其中，`字段名`须与编写的`数据类`对应字段名一致。
#### 填入Excel 文件
```java
// 生成的 Excel 文件名
String filename = "demo.xlsx";
String templateFileName = "demoTemplateFile.xlsx";
FastExcel.write(filename).withTemplate(templateFileName).sheet().doFill(writeData());
```
### Excel 转换为 PDF
```java
String ExcelFilename = "demo";
String PDFFilename = "pdfFile"
FastExcel.convertToPdf(new File(Excelfilename),new File(PDFFilename),null,null);
```