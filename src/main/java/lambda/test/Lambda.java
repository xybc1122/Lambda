package lambda.test;

import java.util.Arrays;
import java.util.List;

public class Lambda {
    public void oldRunable(){
        new Thread(new Runnable() {
            public void run() {
                System.out.println("老的内部类方法");
            }
        }).start();
    }
    public void runable(){
        new Thread(() -> System.out.println("新的内部类方法")).start();
    }

    public void iterTest() {
        List<String> languages = Arrays.asList("java", "scala", "python");

        languages.forEach(x -> System.out.println(x));
    }

    public void i(){
        boolean c=true;
     /*   Integer is [] =new Integer[]{1,1,2,3,4,5,6};
        long conunt=0;
        for (Integer in:is) {
                if(in >4){
                    conunt++;
                }
        }
        System.out.println(conunt);*/
     if(c==true){
         System.out.println("我来了");

     }else if(c==false){
         System.out.println("来了");
     }

    }



     public static void main(String []args){
        Lambda lambda =new Lambda();
        lambda.i();
     }
}
