package com.yicheng.camel;


import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;



/**
 * Created by yuer on 2017/2/14.
 */
public class ExecuteSqlWithCamelJdbcTest {
    private CamelContext context;
    private ExecuteSqlWithCamelJdbc camelJdbc = new ExecuteSqlWithCamelJdbc();
    @Before
    public void init(){
        context = camelJdbc.createCamelContext();
    }
    @Test
    public void camelJDBCTest() throws Exception {
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer://timer1?period=1s")
                        .setBody(constant("SELECT * FROM user"))
                        .to("jdbc:mysqlDB").split(body())
                        .to("bean:recordProcessor");

            }
        });
        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}