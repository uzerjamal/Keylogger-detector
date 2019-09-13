public class test{
    static void hello(String s){
        System.out.println(s);
    }
    static String test2(){
        return "Hello";
    }
    public static void main(String[] args){
        test2().hello();
    }
}