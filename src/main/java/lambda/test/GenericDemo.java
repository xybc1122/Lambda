package lambda.test;

import java.util.ArrayList;
import java.util.List;

public class GenericDemo {


    public static void main(String args[]) {
        //创建一个泛型对象
        GenClass<String> g1 = new GenClass<String>("test");
        System.out.println(g1.getObj());

        GenClass<Integer> g2 = new GenClass<Integer>(100);

        System.out.println(g2.getObj());
        List<String> r1 = new ArrayList<>();
        r1.add("111");
        g1.wildCard(r1);
    }
}
