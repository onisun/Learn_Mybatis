import com.onisun.demo2.bean.*;
import com.onisun.demo2.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Neo
 * @version 1.0
 */
public class MyTest {

    private SqlSessionFactory sqlSessionFactory = null;

    @Before
    public void init() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);
//        Emp empByEmpno = mapper.getEmpByEmpno(7369);
        sqlSession.close();
//        System.out.println(empByEmpno);
    }

    @Test
    public void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);

        Emp emp = new Emp();
        emp.setEname("Sakura4");
        emp.setJob("Seller4");
        emp.setSal(4500.00);
        int i = mapper.insertEmp(emp);
        sqlSession.commit();
        sqlSession.close();
        System.out.println(i);
    }


    @Test
    public void test3() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);

        Emp emp = new Emp();
        emp.setEmpno(7369);
        emp.setEname("SMITH");
        Emp emp2 = mapper.getEmpByEmpnoAndName(emp);
        sqlSession.commit();
        sqlSession.close();
        System.out.println(emp2);
    }

    @Test
    public void test4() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUserName("bob");
        Integer save = mapper.save(user);

        //useGeneratedKeys=true keyProperty="id"
        //完成自增插入操作时，可以将自增生成的主键值返回到具体对象中
        System.out.println(user);
        sqlSession.commit();
        sqlSession.close();
        System.out.println(save);
    }

    @Test
    public void test5() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        List<User> users = mapper.queryAll();
        for (User user : users) {
            System.out.println(user);
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test6() {
        //resultType 查询所有emp
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);

        List<Emp> list = mapper.selectAll();
        for (Emp emp : list) {
            System.out.println(emp);
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test7() {
        //根据empno查询emp
        // EmpDao.xml中使用的#{} SQL是： select * from emp where empno = ?   预处理没有SQL注入问题
        //使用${} SQL是：select * from emp where empno = 7369    拼接的，有SQL注入问题
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);

        Emp emp = mapper.getEmpByEmpno(7369);
        System.out.println(emp);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test8() {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);
        Emp emp1 = new Emp();
        emp1.setEmpno(7369);
        emp1.setSal(500.0);
        List<Emp> list = mapper.selectEmpByEmpnoAndSal2(7369, 500.0);
        for (Emp emp : list) {
            System.out.println(emp);
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test9() {
        //自定义map结构
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("empno", 7369);
        map.put("sal", 500.0);
        List<Emp> list = mapper.selectEmpByEmpnoAndSal3(map);
        for (Emp emp : list) {
            System.out.println(emp);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test10() {
        //返回类型是map结构 只有1个结果
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);

        Map<Object, Object> map = mapper.getEmpByEmpnoResultMap(7369);
        System.out.println(map);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test11() {
        //返回类型是map集合，包含多个结果
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);

        Map<String, Emp> stringEmpMap = mapper.selectAll2();
        System.out.println(stringEmpMap);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test12() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DogDao mapper = sqlSession.getMapper(DogDao.class);

        Dog dog = mapper.selectDogById(2);
        System.out.println(dog);

        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void test13() {
        //表联合查询
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Emp2Dao mapper = sqlSession.getMapper(Emp2Dao.class);

        Emp2 emp2 = mapper.selectEmpByEmpno(7369);
        System.out.println(emp2);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test14() {
        //表联合查询 Dept 包含List<emp>
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DeptDao mapper = sqlSession.getMapper(DeptDao.class);

        Dept dept = mapper.getDeptByDeptno(10);
        System.out.println(dept);

        sqlSession.commit();
        sqlSession.close();
    }
}
