package com.geekq.miaosha;

import com.geekq.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * mybatis xml 写法的配置测试类
 */
public class Test {
    public static void main(String[] args) throws IOException {

        //读取配置文件
        Reader reader = Resources.getResourceAsReader("config.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);


        SqlSession session  = sessionFactory.openSession();

        String statement = "com.geekq.miaosha.dao.UserMapper.getMiaoShaUserById" ;
        MiaoshaUser user = session.selectOne(statement,"18612766134");

        System.out.println(user.toString());
    }
}
