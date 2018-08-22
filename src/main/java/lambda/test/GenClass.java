package lambda.test;

import java.util.List;

public class GenClass<T> {
    private T obj;

    public GenClass(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    //泛型方法
    public <T> T test(T t) {
        return t;
    }

    //类型通配符
    public void wildCard(List<? extends String> c) {
        for (int i = 0; i < c.size(); i++) {
            System.out.println(c.get(i));
        }
    }
}
