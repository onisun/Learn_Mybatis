package com.onisun.demo2.bean;

import java.util.List;

/**
 * @author Neo
 * @version 1.0
 */
public class Dept {
    private Integer deptno;
    private String dname;
    private String loc;
    private List<Emp2> emps;

    public Dept() {
    }


    public List<Emp2> getEmps() {
        return emps;
    }

    public void setEmps(List<Emp2> emps) {
        this.emps = emps;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptno=" + deptno +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                ", emps=" + emps +
                '}';
    }
}
