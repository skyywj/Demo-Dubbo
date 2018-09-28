package com.sky.hrpro.entity;

import java.util.Objects;

/**
 * @Author: CarryJey
 * @Date: 2018/9/27 上午10:30
 */
public class test {

    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof test)) return false;
        test test = (test) o;
        return id == test.id &&
                age == test.age &&
                Objects.equals(name, test.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, age);
    }
}
