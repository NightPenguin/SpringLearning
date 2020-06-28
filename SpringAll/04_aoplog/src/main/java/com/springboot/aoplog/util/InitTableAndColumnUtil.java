package com.springboot.aoplog.util;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.springboot.aoplog.annotation.Column;
import com.springboot.aoplog.annotation.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 初始化工具-用于创建sql 语句（创建表）
 */
public class InitTableAndColumnUtil {

    public static String createTable(Class<?> bean) {
        Map<String, String> tableMap = initTableName(bean);
        String tableName = tableMap.get("name");
        List<ColumnBean> columns = initColumn(bean);

        //主键
        List<String> primaryKey = new ArrayList<>();
        // 若表不为空，开始创建sql 语句
        if (tableName != null && !tableName.equals("") && !columns.isEmpty()) {
            StringBuilder sql = new StringBuilder("create table ");

            //拼接表名
            sql.append(tableName);
            sql.append(" (");

            // 拼接字段
            for (int i = 0; i < columns.size(); i++) {
                ColumnBean column = columns.get(i);

                // 字段名和类型
                sql.append(column.name);
                sql.append(" ");
                sql.append(column.type);

                // 字段长
                int length = column.length;
                if (length != 0) {
                    sql.append("(");
                    sql.append(length);
                    sql.append(") ");
                }

                //字段是否为空
                if (column.isNotNull) {
                    sql.append(" NOT NULL ");
                }

                //判断字段是否自增
                if (column.isAutoIncrement) {
                    sql.append("AUTO_INCREMENT ");
                }

                // 字段默认值
                if (column.defaultValue != null && !column.defaultValue.equals("")) {
                    sql.append("DEFAULT ");
                    sql.append(column.defaultValue);
                    sql.append(" ");
                }

                if (column.comment != null && !column.comment.equals("")) {
                    sql.append("COMMENT ");
                    sql.append("\'" + column.comment + "\' ");
                }

                if (column.isPrimaryKey) {
                    primaryKey.add(column.name);
                }

                if (i < columns.size() - 1) {
                    sql.append(",");
                }
            }

            StringBuilder primaryKeySql = new StringBuilder();
            if (!primaryKey.isEmpty()) {
                primaryKeySql.append(",PRIMARY KEY (");
                for (int i = 0; i < primaryKey.size(); i++) {
                    if (i < primaryKey.size() - 1) {
                        primaryKeySql.append(primaryKey.get(i) + ",");
                    } else {
                        primaryKeySql.append(primaryKey.get(i));
                    }
                }
                primaryKeySql.append(") USING BTREE");
            }
            sql.append(primaryKeySql.toString() + ")");


            if (tableMap.get("comment") != null && !tableMap.get("comment").equals("")) {
                sql.append(" CHARSET=" + tableMap.get("charset") + " COMMENT=\'" + tableMap.get("comment") + "\';");
            } else {
                sql.append(" CHARSET=" + tableMap.get("charset") + ";");
            }

            return sql.toString();
        } else {
            throw new RuntimeException("table's name is null");
        }
    }

    /**
     * 判断AnnotatedElement是否被某注解所标记：AnnotatedElement.isAnnotationPresent(SomeAnnotation.class)；
     * <p>
     * 是的话，获取该注解对象：Annotation annotation = bean.getAnnotation(SomeAnnotation.class);；
     * <p>
     * 根据该注解对象获取某个成员参数（比如name）：Method method = SomeAnnotation.class.getMethod("name");；
     * <p>
     * 利用反射机制，获取该注解中的某成员的值：String name = (String) method.invoke(annotation);。
     */


    private static Map initTableName(Class<?> tablename) {
        Map<String, String> tableMap = new HashMap<String, String>();

        // 表名
        String tableName = null;
        String charSet = null;
        String comment = null;
        if (tablename.isAnnotationPresent(Table.class)) {
            // 如果该对象按在指定类型的注解，则返回该注解，否则返回null。 只有类级别的注解会被继承得到
            Annotation tableAnnotation = tablename.getAnnotation(Table.class);

            try {
                Method method = Table.class.getMethod("name");
                tableName = (String) method.invoke(tableAnnotation);
                tableMap.put("name", tableName);

                Method charSetMethod = Table.class.getMethod("charSet");
                charSet = (String) charSetMethod.invoke(tableAnnotation);
                if (charSet == null || charSet.equals("")) {
                    tableMap.put("charset", "utf8mb4");
                }else{
                    tableMap.put("charset",charSet);
                }

                Method commentMethod = Table.class.getMethod("comment");
                comment = (String) commentMethod.invoke(tableAnnotation);
                tableMap.put("comment", comment);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tableMap;
    }

    private static List<ColumnBean> initColumn(Class<?> bean) {
        List<ColumnBean> columnBeanList = new ArrayList<>();
        // 表中所有的成员变量
        Field[] fields = bean.getDeclaredFields();

        if (fields != null) {
            // 成员变量获取其注解信息
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                // 判断当前列是否已被注解
                if (field.isAnnotationPresent(Column.class)) {
                    String type = null;
                    String name = null;
                    int length = 0;
                    boolean isNotNull = false;
                    boolean isAutoIncrement = false;
                    String defaultValue = null;
                    String comment = null;
                    boolean isPrimaryKey = false;

                    // 获取@Column 注解成员变量的值
                    Annotation columnAnnotation = field.getAnnotation(Column.class);
                    try {
                        Method nameMethod = Column.class.getMethod("name");
                        name = (String) nameMethod.invoke(columnAnnotation);

                        Method lenghtMethod = Column.class.getMethod("length");
                        length = (int) lenghtMethod.invoke(columnAnnotation);

                        Method isNotNullMethod = Column.class.getMethod("isNotNull");
                        isNotNull = (boolean) isNotNullMethod.invoke(columnAnnotation);

                        Method autoIncrementMethod = Column.class.getMethod("isAutoIncrement");
                        isAutoIncrement = (boolean) autoIncrementMethod.invoke(columnAnnotation);

                        Method defaultValueMethod = Column.class.getMethod("defaultValue");
                        defaultValue = (String) defaultValueMethod.invoke(columnAnnotation);

                        Method commentMethod = Column.class.getMethod("comment");
                        comment = (String) commentMethod.invoke(columnAnnotation);

                        Method primaryKeyMethod = Column.class.getMethod("isPrimaryKey");
                        isPrimaryKey = (boolean) primaryKeyMethod.invoke(columnAnnotation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (int.class.isAssignableFrom(field.getType()) || Integer.class.isAssignableFrom(field.getType())) {
                        type = "int";
                    } else if (String.class.isAssignableFrom(field.getType())) {
                        type = "varchar";
                    } else if (Date.class.isAssignableFrom(field.getType())) {
                        type = "datetime";
                    } else if (char.class.isAssignableFrom(field.getType()) || Char.class.isAssignableFrom(field.getType())) {
                        type = "char";
                    } else {
                        throw new RuntimeException("unexpported type = " + field.getType().getSimpleName());
                    }

                    columnBeanList.add(new ColumnBean(type, name, length, isNotNull, isAutoIncrement, defaultValue, comment, isPrimaryKey));
                }
            }
        }
        return columnBeanList;
    }

    private static class ColumnBean {
        final String type;
        final String name;
        final int length;
        final boolean isNotNull;
        final boolean isAutoIncrement;
        final String defaultValue;
        final String comment;
        final boolean isPrimaryKey;

        public ColumnBean(String type, String name, int length, boolean isNotNull, boolean isAutoIncrement, String defaultValue, String comment, boolean isPrimaryKey) {
            this.type = type;
            this.name = name;
            this.length = length;
            this.isNotNull = isNotNull;
            this.isAutoIncrement = isAutoIncrement;
            this.defaultValue = defaultValue;
            this.comment = comment;
            this.isPrimaryKey = isPrimaryKey;
        }
    }
}
