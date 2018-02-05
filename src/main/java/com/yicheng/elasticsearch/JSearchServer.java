package com.yicheng.elasticsearch;

import com.google.common.util.concurrent.Futures;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import lombok.Getter;

@Getter
class JSearchServer {
    private JestClient jestClient;
    JSearchServer(String url) {
        JestClientFactory clientFactory = new JestClientFactory();
        clientFactory.setHttpClientConfig(new HttpClientConfig.Builder(url)
                .multiThreaded(true)
                .defaultMaxTotalConnectionPerRoute(2)
                .maxTotalConnection(20)
                .build());
        jestClient = clientFactory.getObject();
    }

}
