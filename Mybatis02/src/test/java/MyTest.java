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
import java.util.*;

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
        //???????????????????????????????????????????????????????????????????????????????????????
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
        //resultType ????????????emp
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
        //??????empno??????emp
        // EmpDao.xml????????????#{} SQL?????? select * from emp where empno = ?   ???????????????SQL????????????
        //??????${} SQL??????select * from emp where empno = 7369    ???????????????SQL????????????
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
        //?????????map??????
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
        //???????????????map?????? ??????1?????????
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);

        Map<Object, Object> map = mapper.getEmpByEmpnoResultMap(7369);
        System.out.println(map);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test11() {
        //???????????????map???????????????????????????
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
        //???????????????
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Emp2Dao mapper = sqlSession.getMapper(Emp2Dao.class);

        Emp2 emp2 = mapper.selectEmpByEmpno(7369);
//        System.out.println(emp2.getEname());
        System.out.println(emp2);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test14() {
        //??????????????? Dept ??????List<emp>
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DeptDao mapper = sqlSession.getMapper(DeptDao.class);

        Dept dept = mapper.getDeptByDeptno(10);
        System.out.println(dept);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test15() {
        //????????????
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Emp2Dao mapper = sqlSession.getMapper(Emp2Dao.class);

        Emp2 emp2 = mapper.selectEmpByStep(7369);
        System.out.println(emp2.getEname());

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test16() {
        //??????sql if
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Emp2Dao mapper = sqlSession.getMapper(Emp2Dao.class);

        Emp2 emp = new Emp2();
//        emp.setEname("SMITH");
        emp.setSal(200.0);
        List<Emp2> emp2 = mapper.selectEmp(emp);
        for (Emp2 emp21 : emp2) {
            System.out.println(emp21);
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test17() {
        //??????sql where??????
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Emp2Dao mapper = sqlSession.getMapper(Emp2Dao.class);

        Emp2 emp = new Emp2();
//        emp.setEname("SMITH");
        emp.setSal(200.0);
        List<Emp2> emp2 = mapper.selectEmp2(emp);
        for (Emp2 emp21 : emp2) {
            System.out.println(emp21);
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test18() {
        //??????sql foreach??????
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Emp2Dao mapper = sqlSession.getMapper(Emp2Dao.class);

        List<Emp2> emp2 = mapper.selectEmp2ByDeptnos(Arrays.asList(10,20));
        for (Emp2 emp21 : emp2) {
            System.out.println(emp21);
        }
        sqlSession.commit();
        sqlSession.close();
    }
}
