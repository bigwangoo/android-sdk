package com.bigwangoo.sample;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * description
 *
 * @author wangyd
 * @date 2018/12/27
 */
public class ConstantTest {


    @Test
    public void MethodInvoke() {
        try {

            Animal animal = new Animal();
            Cat cat = new Cat();


            Method animalMethod = Animal.class.getDeclaredMethod("print");
            animalMethod.invoke(cat);
            animalMethod.invoke(animal);

            Method catMethod = Cat.class.getDeclaredMethod("print");
            catMethod.invoke(cat);
            catMethod.invoke(animal);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("","");

    }

    class Animal {
        public void print() {
            System.out.println("Animal.print()");
        }
    }

    class Cat extends Animal {
        @Override
        public void print() {
            System.out.println("Cat.print()");
        }
    }

}