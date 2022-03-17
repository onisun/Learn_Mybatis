package com.onisun.demo2.dao;

import com.onisun.demo2.bean.Emp;
import com.onisun.demo2.bean.Emp2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Neo
 * @version 1.0
 */
public interface Emp2Dao {

    Emp2 selectEmpByEmpno(Integer empno);

    Emp2 selectEmpByStep(Integer empno);

    List<Emp2> selectEmp(Emp2 emp2);
    List<Emp2> selectEmp2(Emp2 emp2);

    List<Emp2> selectEmp2ByDeptnos(@Param("deptnos") List<Integer> deptnos);
}
