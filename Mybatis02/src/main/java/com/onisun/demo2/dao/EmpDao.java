package com.onisun.demo2.dao;

import com.onisun.demo2.bean.Emp;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Neo
 * @version 1.0
 */
public interface EmpDao {
    Emp getEmpByEmpno(Integer empno);
    int insertEmp(Emp emp);
    Emp getEmpByEmpnoAndName(Emp emp);
    Map<Object,Object> getEmpByEmpnoResultMap(Integer empno);

    Emp getEmpEnameAndSal(Emp emp);

    List<Emp> selectAll();

    @MapKey("empno")
    Map<String,Emp> selectAll2();

    List<Emp>  selectEmpByEmpnoAndSal(Emp emp);
    List<Emp>  selectEmpByEmpnoAndSal2(@Param("empno") Integer empno,@Param("sal") Double sal);
    List<Emp>  selectEmpByEmpnoAndSal3(Map<String,Object> map);
}
