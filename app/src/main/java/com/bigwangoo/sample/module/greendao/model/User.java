package com.bigwangoo.sample.module.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 数据库实体演示
 *
 * @author wangyd
 * @date 2018/12/29
 */

@Entity(
        // 多个schema时设置关联实体。插件产生不支持，需使用产生器
//        schema = "myschema",

        // 表的别名，默认使用的是实体的类名
//        nameInDb = "user",

        // 定义多列索引，可以跨越多个列
//        indexes = {@Index(value = "name DESC", unique = true)},

        // 标记一个实体处于活动状态，活动实体有 update、delete、refresh 方法。默认为 false
        active = false,

        // 标记是否创建表，默认true。多实体对应一个表或者表已创建，不需要greenDAO创建时设置false
        createInDb = true,

        // 参数构造器，默认为true 无参构造器必定产生
        generateConstructors = true,

        // GET/SET方法，默认为true
        generateGettersSetters = true
)
public class User {

    // 主键 Long型，可以通过@Id(autoincrement = true)设置自增长
    @Id(autoincrement = true)
    private Long id;

    //设置数据库表当前列不能为空
    @NotNull
    private String username;

    // 添加次标记之后不会生成数据库表的列
    @Transient
    private String password;

    // 设置一个非默认关系映射所对应的列名，默认是的使用字段名
    @Property(nameInDb = "sex")
    private String gender;

    //使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
//    @Index
//    private String index;

    //向数据库列添加了一个唯一的约束
//    @Unique
//    private String unipue;

    //定义与另一个实体（一个实体对象）的关系
//    @ToOne
//    private String toOne;

    //定义与多个实体对象的关系
//    @ToMany
//    private String toMany;


    @Generated(hash = 693353052)
    public User(Long id, @NotNull String username, String gender) {
        this.id = id;
        this.username = username;
        this.gender = gender;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
