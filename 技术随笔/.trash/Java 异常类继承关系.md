#### Exception
受检异常（Checked Exception）：继承自 Exception 类的异常。受检异常要求程序员必须要显式处理。如果某个方法可能抛出受检异常，程序员必须使用 try-catch 语句块来捕获异常并处理，或者在方法签名中使用 throws 关键字将异常抛出，交给调用该方法的代码去处理。
#### RuntimeException
非受检异常（unchecked exception）：继承自 RuntimeException 类的异常，又称运行时异常。非受检异常不必要被显式捕获或声明才能抛出。编译器不会强制要求程序员在代码中显式地捕获或声明这些异常，程序员可以选择不处理这些异常，而是让它们在运行时直接抛出。
##### NullPointerException
##### ArrayIndexOutOfBoundsException
##### ArithmeticException