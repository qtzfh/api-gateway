package com.api.gateway.base;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.Reader;

/**
 * DbConnection
 *
 * @author zhangfuhao
 * @Desc 数据库基础connect
 * @date 2018年11月22日
 * @Version V1.0
 */
public enum BaseDBConnect {
    /**
     * 单例
     */
    INSTANCE;

    private SqlSession sqlSession = null;

    BaseDBConnect() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis/mybatis.cfg.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            //从刚刚创建的 sqlSessionFactory 中获取 session
            sqlSession = sqlSessionFactory.openSession();
            //创建一个MapperHelper
            MapperHelper mapperHelper = new MapperHelper();
            mapperHelper.processConfiguration(sqlSession.getConfiguration());
        } catch (IOException e) {
            Assert.assertTrue("获取数据库信息失败", false);
        }
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }
}
