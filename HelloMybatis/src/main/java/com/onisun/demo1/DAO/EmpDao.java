package com.onisun.demo1.DAO;

import com.onisun.demo1.bean.Emp;

/**
 * @author Neo
 * @version 1.0
 */
public interface EmpDao {

    Emp getEmpByEmpno(Integer empno);

    int updataEmp(Emp emp);

    int deleteEmp(Integer emp);

    int insertEmp(Emp emp);
}
