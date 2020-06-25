package com.springboot.aoplog.util;

import com.springboot.aoplog.annotation.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 初始化工具-用于创建sql 语句（创建表）
 */
public class InitTableAndColumnUtil {
    /**
     *     判断AnnotatedElement是否被某注解所标记：AnnotatedElement.isAnnotationPresent(SomeAnnotation.class)；
     *
     *     是的话，获取该注解对象：Annotation annotation = bean.getAnnotation(SomeAnnotation.class);；
     *
     *     根据该注解对象获取某个成员参数（比如name）：Method method = SomeAnnotation.class.getMethod("name");；
     *
     *     利用反射机制，获取该注解中的某成员的值：String name = (String) method.invoke(annotation);。
     */


    private static String initTableName(Class<?> tablename){
        // 表名
        String tableName = null;
        if(tablename.isAnnotationPresent(Table.class)){
            // 如果该对象按在指定类型的注解，则返回该注解，否则返回null。 只有类级别的注解会被继承得到
            Annotation tableAnnotation = tablename.getAnnotation(Table.class);

            try {
                Method method = Table.class.getMethod("value");
                tableName = (String)method.invoke(tableAnnotation);
            } catch (Exception  e) {
                e.printStackTrace();
            }
        }


        return tableName;
    }
}
