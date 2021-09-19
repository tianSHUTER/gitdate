import java.io.*;
import java.lang.reflect.Method;

public class XlassLoader extends ClassLoader
{
    public static void main(String[] args) throws Exception {

        final  String className="Hello";
        final  String classMethod="hello";

        ClassLoader xlassLoader = new XlassLoader();
        Class<?> Class = xlassLoader.loadClass(className);
        for (Method m: Class.getDeclaredMethods()
             ) {
            System.out.println("总共的方法总项"+m.getName());
        }
        Object instance = Class.getDeclaredConstructor().newInstance();
        Method method = Class.getMethod(classMethod);
        method.invoke(instance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String resourcePath=name.replace(".","/");
        final String suffix=".xlass";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(resourcePath + suffix);
        try{
        int available = resourceAsStream.available();//找不到xlass会报出空指针异常  这样显示不符合逻辑

        byte[] bytes=new byte[available];
            System.out.println(bytes.toString());
        resourceAsStream.read(bytes);
        bytes=decode(bytes);
            //无法根据文件创建出一个可读取的内容时会报错
            // java.lang.ClassFormatError: Incompatible magic value 2291900044 in class file Hello
            return defineClass(name,bytes,0,available);  }
        catch (IOException e){
            throw new ClassNotFoundException(name,e);
        }finally {
        close(resourceAsStream);
        }

    }
    //考虑内存的存储关系
    /*
    是选择引用参数进行数组的操作 还是在此方法中创建一个数组进行操作
     */
//    private static byte[] decode(byte[] bytes) {
//        System.out.println(bytes.toString());
//    //获取对象hash值查看引用的规则
//        for (int i = 0 ; i<bytes.length;i ++){
//            bytes[i]=(byte) (255-bytes[i]);
//        }
//        return bytes;
//    }


    private static byte[] decode(byte[] bytes) throws IOException {

    //获取对象hash值查看引用的规则
    byte[] byteArray=new byte[bytes.length];

    //获取对象hash值查看引用的规则
        for (int i = 0 ; i<bytes.length;i ++){
          byteArray[i]=(byte) (255-bytes[i]);
        }
        //将解密后的文件下载到当前目录

        FileOutputStream osw = new FileOutputStream("time.txt");
        osw.write(byteArray);
        return byteArray;
    }

    //可以附加@After设置   =====aop切面

    private static void close(Closeable res){
        if (res !=null)
        {
            try {
                res.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
