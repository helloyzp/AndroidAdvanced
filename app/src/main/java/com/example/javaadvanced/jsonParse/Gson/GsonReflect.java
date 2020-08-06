package com.example.javaadvanced.jsonParse.Gson;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Gson用到的反射原理
 */
public class GsonReflect {

    public static class Person {
        public String name;
        public String sex;

        public Person() {
        }

        public Person(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //1.反射获取构造器，根据构造器创建Person对象
        Constructor<?> c = Person.class.getDeclaredConstructor();
        Person person = (Person) c.newInstance();
        System.out.println("person=" + person);

        //2.获取Person中的所有属性，为属性赋值
        Field[] fields = Person.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //获取属性名称
            String fieldName = field.getName();
            if(fieldName.equals("name")) {
                field.set(person, "孙悟空");
            } else if(fieldName.equals("sex")) {
                field.set(person, "男");
            }
        }
        System.out.println("person=" + person);
    }

}
