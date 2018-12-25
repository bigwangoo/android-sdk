package com.bigwangoo.sample.module.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * @author wangyd
 * @date 2018/11/28
 * @description description
 */
@Table(name = "DbTest")
public class DbTest {

    @Column(name = "id", isId = true, autoGen = true)
    private long id;
    @Column(name = "name")
    private String name;

    public DbTest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


//    @Column(name = "age")
//    private int age;
//
//    public DbTest(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }


    @Column(name = "age")
    private String age;

    public DbTest(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "DbTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
