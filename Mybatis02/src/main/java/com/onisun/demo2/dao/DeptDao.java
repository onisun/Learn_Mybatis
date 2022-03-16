package com.onisun.demo2.dao;

import com.onisun.demo2.bean.Dept;

import java.util.List;

/**
 * @author Neo
 * @version 1.0
 */
public interface DeptDao {

    Dept getDeptByDeptno(Integer deptno);
}
