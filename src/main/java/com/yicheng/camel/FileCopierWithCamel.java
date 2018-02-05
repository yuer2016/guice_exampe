package com.yicheng.camel;

import lombok.SneakyThrows;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.sql.DataSource;

/**
 * Created by yuer on 2017/2/8.
 */
public class FileCopierWithCamel {
    @SneakyThrows
    public static void main(String[] args) {
        DataSource dataSource = null;
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:data/inbox?noop=true")
                        .to("file:data/outbox");
            }
        });
        camelContext.start();
        Thread.sleep(10000);
        camelContext.stop();
    }
}
