package com.api.gateway.mysql;

import com.api.gateway.mysql.test.mapper.TestMapper;
import com.api.gateway.mysql.test.model.Test;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * DbConnection
 *
 * @author zhangfuhao
 * @Desc
 * @date 2018年11月22日
 * @Version V1.0
 */
public class DbConnection {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis/mybatis.cfg.xml");
        SqlSessionFactory sqlSessionFactory = new  SqlSessionFactoryBuilder().build(reader);
        //从刚刚创建的 sqlSessionFactory 中获取 session
        SqlSession session = sqlSessionFactory.openSession();
        //创建一个MapperHelper
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.processConfiguration(session.getConfiguration());
        TestMapper mapper = session.getMapper(TestMapper.class);
        List<Test> tests = mapper.selectAll();
        tests.stream().forEach(x-> System.out.println(x));
    }
}
