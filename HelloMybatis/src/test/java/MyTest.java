import com.onisun.demo1.DAO.EmpDao;
import com.onisun.demo1.bean.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Neo
 * @version 1.0
 */
public class MyTest {

    SqlSessionFactory sqlSessionFactory = null;
    @Before
    public void init(){
        //根据全局配置文件创建出sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;

        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Emp empByEmpno = null;
        try {
            EmpDao mapper = sqlSession.getMapper(EmpDao.class);
            empByEmpno = mapper.getEmpByEmpno(7369);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        System.out.println(empByEmpno);
    }



    @Test
    public void test2(){
        //获取数据库连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);
        Emp emp = new Emp();
        emp.setEname("sam");
        emp.setEmpno(7839);
        int result = mapper.updataEmp(emp);

        System.out.println(result);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testInsert(){
        //获取数据库连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);
        Emp emp = new Emp();
        emp.setEname("Bob");
        emp.setJob("play");
        int result = mapper.insertEmp(emp);

        System.out.println(result);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDel(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpDao mapper = sqlSession.getMapper(EmpDao.class);
        int result = mapper.deleteEmp(7856);
        System.out.println(result);
        sqlSession.commit();
        sqlSession.close();

    }
}
