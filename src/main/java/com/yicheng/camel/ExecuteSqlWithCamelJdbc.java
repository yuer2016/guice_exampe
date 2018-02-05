package com.yicheng.camel;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.impl.SimpleRegistry;

import javax.sql.DataSource;

/**
 * Created by yuer on 2017/2/14.
 */
public class ExecuteSqlWithCamelJdbc {

    public DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.101.205:3306/mysql");
        dataSource.setPoolPreparedStatements(true);
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    public CamelContext createCamelContext(){
        SimpleRegistry registry = new SimpleRegistry();
        DataSource ds = getDataSource();
        registry.put("mysqlDB",ds);
        registry.put("recordProcessor",new RecordProcessor());
        return  new DefaultCamelContext(registry);
    }
}
