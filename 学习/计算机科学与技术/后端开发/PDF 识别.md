## Apache PDFBox
识别文本型 PDF 文件中的内容。
### 快速入门
#### 依赖
```xml
<dependency>  
    <groupId>org.apache.pdfbox</groupId>  
    <artifactId>pdfbox</artifactId>  
    <version></version>  
</dependency>
```
#### 解析 PDF
```java
// 类加载器，加载资源文件
ClassLoader classLoader = Test.class.getClassLoader();
// 通过类加载器获取名为 "合同.pdf" 的资源的 URL
URL resourceUrl = classLoader.getResource("合同.pdf");
// 确保资源不为空，否则抛出 NullPointerException
Objects.requireNonNull(resourceUrl);
// 获取资源的文件路径（仍可能是 URL 编码形式，例如空格会被写成 %20）
String encodedFilePath = resourceUrl.getFile();
// 对文件路径进行 URL 解码，恢复为操作系统可识别的本地文件路径格式
String decodedFilePath = URLDecoder.decode(encodedFilePath, StandardCharsets.UTF_8);
// 创建一个 File 对象，指向解码后的本地 PDF 文件路径
File pdfFile = new File(decodedFilePath);
// 使用 Apache PDFBox 加载 PDF 文件为 PDDocument 对象（代表整个 PDF 文档）
PDDocument pdDocument = PDDocument.load(pdfFile);
// 创建一个 PDFTextStripper 对象，用于提取 PDF 文档中的纯文本内容
PDFTextStripper pdfTextStripper = new PDFTextStripper();
// 从 PDDocument 对象中提取文本内容，并存储到字符串中
String fullText = pdfTextStripper.getText(pdDocument);

System.out.println(fullText);
```
## MinerU
识别文本型和扫描型 PDF 文件中的内容。