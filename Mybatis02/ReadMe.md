### ResultType

```xml
<!--    ResultType 表示返回结果的类型，此类型只能返回单一的对象
        当返回的结果是一个集合时，并不需要ResultMap，只需要ResultType指定集合中的元素类即可
 -->
<select id="selectAll" resultType="com.onisun.demo2.bean.Emp">
    select * from emp
</select>
```

```java
List<Emp> selectAll();
```

```java
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
```



### #{}和${}的区别

```xml
<!--    使用的#{} SQL是： select * from emp where empno = ?   预处理没有SQL注入问题
    使用${} SQL是：select * from emp where empno = 7369    拼接的，有SQL注入问题
    ${}有自己的使用场景，比如：当需要动态传入表名列名的时候
    select * from ${arg0} where empno = ${arg1}
    -->
    <select id="getEmpByEmpno" resultType="com.onisun.demo2.bean.Emp" >
        select * from emp where empno = #{empno}
    </select>
```



```xml
<!-- 如果参数不是一个对象，而是多个基本数据类型或者引用数据类型，#{属性名/参数名}无法取到值
，只能通过#{arg0,arg1...}  #{param0,param1...}
如果想要能够通过参数名获取到值，可以在接口参数中定义@Param("value")
-->
<select id="selectEmpByEmpnoAndSal2" resultType="com.onisun.demo2.bean.Emp" >
    select * from emp where empno = #{empno} and sal > #{sal}
</select>

<select id="selectEmpByEmpnoAndSal" resultType="com.onisun.demo2.bean.Emp" >
    select * from emp where empno = #{empno} and sal > #{sal}
</select>
```

```java
List<Emp>  selectEmpByEmpnoAndSal2(@Param("empno") Integer empno,@Param("sal") Double sal);
```





### 自定义Map结构

```xml
<!--    自定义map结构-->
    <select id="selectEmpByEmpnoAndSal3" resultType="com.onisun.demo2.bean.Emp" >
        select * from emp where empno = #{empno} and sal > #{sal}
    </select>
```

```java
List<Emp>  selectEmpByEmpnoAndSal3(Map<String,Object> map);
```

```java
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
```



### 返回类型是Map类型

返回map集合，只有一个对象

```xml
<!--   返回map集合，只有一个对象会把查询结构的字段值作为key，结果作为value -->
    <select id="getEmpByEmpnoResultMap" resultType="map" >
        select * from emp where empno = #{empno}
    </select>
```

```java
Map<Object,Object> getEmpByEmpnoResultMap(Integer empno);
```

```java
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
```



返回的map集合包含多个对象

```xml
<!--    返回的map集合包含多个对象，此时没有key会报错。
        需要在Dao的方法上添加注解@Mapkey 来标识哪个属性值作为key -->
    <select id="selectAll2" resultType="map" >
        select * from emp
    </select>
```



```java
@MapKey("empno")
Map<String,Emp> selectAll2();
```



```java
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
```





### 自定义结果集



当表中的列名与类中的属性名不一致时，查询出来的结果，在赋值的时候会为空，比如：



dog表

![image-20220316132639915](image/image-20220316132639915.png)

dog类

![image-20220316131338139](image/image-20220316131338139.png)



sql

```xml
<select id="selectDogById" resultType="com.onisun.demo2.bean.Dog">
    select * from dog where id = #{id}
</select>
```



```java
@Test
public void test12() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    DogDao mapper = sqlSession.getMapper(DogDao.class);

    Dog dog = mapper.selectDogById(2);
    System.out.println(dog);

    sqlSession.commit();
    sqlSession.close();
}
```

打印结果：

```
DEBUG [main] - ==>  Preparing: select * from dog where id = ?
DEBUG [main] - ==> Parameters: 2(Integer)
TRACE [main] - <==    Columns: id, dname, dage, dgender
TRACE [main] - <==        Row: 2, 二黄, 2, 雌
DEBUG [main] - <==      Total: 1
Dog{id=2, name='null', age=null, gender='null'}
```

可以发现，能查询出来结果，但是赋值给Dog对象时却赋值不上。就是因为列名与属性名不一致，mybatis不知道如何赋值，此时就需要自定义结果集



#### 使用resultMap自定义结果集

```xml
<!--    使用resultMap自定义结果集-->
    <resultMap id="myDog" type="com.onisun.demo2.bean.Dog">
<!--        将属性名与表的列名一一映射-->
        <id property="id" column="id"/>
        <result property="name" column="dname"/>
        <result property="age" column="dage"/>
        <result property="gender" column="dgender"/>
    </resultMap>

<!--    属性名与列名不一致，mybatis可以查询成功，但无法给对象赋值  需要使用resultMap自定义结果集-->
    <select id="selectDogById" resultMap="myDog">
        select * from dog where id = #{id}
    </select>
    
```



```
DEBUG [main] - ==>  Preparing: select * from dog where id = ?
DEBUG [main] - ==> Parameters: 2(Integer)
TRACE [main] - <==    Columns: id, dname, dage, dgender
TRACE [main] - <==        Row: 2, 二黄, 2, 雌
DEBUG [main] - <==      Total: 1
Dog{id=2, name='二黄', age=2, gender='雌'}
```

此时发现对象已经成功赋值。





#### resultMap中的association



```xml
<!--   association :多表联合查询的时候使用，将结果集映射到javaType的类中 如:
表联合查询 将结果集映射到了Author类中，
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
    <result property="favouriteSection" column="author_favourite_section"/>
  </association>

-->
```



案例：

Emp2实体类中关联了Dept类 ，根据关联的deptno进行联合查询

```java
public class Emp2 {
    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private Date hiredate;
    private Double sal;
    private Double comm;
    private Dept dept;
```



Dept类：

```java
public class Dept {
    private Integer deptno;
    private String dname;
    private String loc;
```

表的字段：

![image-20220316143729692](image/image-20220316143729692.png)





xml:

```xml
<!--第一种方式：将实体类对象，写成对象.属性的方式-->
    <resultMap id="myEmp2" type="com.onisun.demo2.bean.Emp2">
        <result column="deptno" property="dept.deptno"/>
        <result column="loc" property="dept.loc"/>
        <result column="dname" property="dept.dname"/>
    </resultMap>
    <select id="selectEmpByEmpno" resultMap="myEmp2">
        select * from emp left join dept on emp.deptno = dept.deptno where empno = #{empno}
    </select>
```



推荐使用：

```xml
    <!--第2种方式：使用association-->
    <resultMap id="myEmp2" type="com.onisun.demo2.bean.Emp2">
        <id column="empno" property="empno"/>
        <result column="ename" property="ename"/>
        <result column="job" property="job"/>
        <result column="mgr" property="mgr"/>
        <result column="hiredate" property="hiredate"/>
        <result column="sal" property="sal"/>
        <result column="comm" property="comm"/>
<!--      property 表示类中的一个属性 ，javaType表示这个属性是什么类型  -->
        <association property="dept" javaType="com.onisun.demo2.bean.Dept">
            <id column="deptno" property="deptno"/>
            <result column="dname" property="dname"/>
            <result column="loc" property="loc"/>
        </association>
    </resultMap>
    <select id="selectEmpByEmpno" resultMap="myEmp2">
        select * from emp left join dept on emp.deptno = dept.deptno where empno = #{empno}
    </select>
```

第二种方式如果只写了association的话，其他的值会赋值不上



#### **resultMap中的collection**



查询对象中的属性是一个集合对象时使用，关联查询到该部门中所有的员工

```java
public class Dept {
    private Integer deptno;
    private String dname;
    private String loc;
    private List<Emp2> emps;
```





```xml
<resultMap id="myDept" type="com.onisun.demo2.bean.Dept">
    <id property="deptno" column="deptno"/>
    <result property="dname" column="dname"/>
    <result property="loc" column="loc"/>

    <collection property="emps" ofType="com.onisun.demo2.bean.Emp2">
        <id column="empno" property="empno"/>
        <result column="ename" property="ename"/>
        <result column="job" property="job"/>
        <result column="mgr" property="mgr"/>
        <result column="hiredate" property="hiredate"/>
        <result column="sal" property="sal"/>
        <result column="comm" property="comm"/>
    </collection>
</resultMap>
<select id="getDeptByDeptno" resultMap="myDept">
    select * from dept left join emp on dept.deptno = emp.deptno where dept.deptno = #{deptno}
</select>
```



```java
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
```