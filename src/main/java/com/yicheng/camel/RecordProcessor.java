package com.yicheng.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

/**
 * Created by yuer on 2017/2/15.
 */
@Slf4j
public class RecordProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Processing msg {}", exchange);
        Map<String, Object> record = exchange.getIn().getBody(Map.class);
        log.info("Processing record {}", record);
    }
}
