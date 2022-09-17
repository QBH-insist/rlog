package com.qbh.github.simpleexample;

import java.util.List;

/**
 *
 * @author QBH
 * @date 2022/8/30
 */
public class User {
    private String id;

    private String name;

    private String sex;

    private List<String> sNos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getsNos() {
        return sNos;
    }

    public void setsNos(List<String> sNos) {
        this.sNos = sNos;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", sNos=" + sNos +
                '}';
    }
}
