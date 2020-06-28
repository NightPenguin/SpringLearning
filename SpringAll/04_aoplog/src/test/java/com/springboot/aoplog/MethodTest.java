package com.springboot.aoplog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InvokeTest test = new InvokeTest();


        Method calMethod =  test.getClass().getMethod("calculate", int.class, int.class);

        System.out.println(calMethod.invoke(test,1,2));

        StringBuilder sql = new StringBuilder();
        System.out.println(sql.toString());

        System.out.println(1);
    }

}

class InvokeTest{
    public int calculate(int a, int b){
        return a + b;
    }
}
