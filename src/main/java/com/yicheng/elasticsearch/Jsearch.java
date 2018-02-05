package com.yicheng.elasticsearch;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yuer on 2017/6/2.
 */
public class Jsearch {
    private static final String search = "{\n" +
            "  \"from\": 0,\n" +
            "  \"size\": 1000,\n" +
            "  \"query\": {\n" +
            "    \"match\": {\n" +
            "      \"_all\": \"奶瓶\"\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @SneakyThrows
    public static void main(String[] args) {
        search();
    }

    private static void search() throws java.io.IOException {
        JSearchServer jSearchServer = new JSearchServer("http://119.23.146.107:9200");
        Search query = new Search.Builder(search)
                .addIndex("youhui")
                .addType("goods")
                .build();
        SearchResult searchResult = jSearchServer.getJestClient().execute(query);
        List<SearchResult.Hit<Goods, Void>> list = searchResult.getHits(Goods.class);
        list.forEach(good ->
                System.out.println(good.source));

    }

    /* JestClientFactory clientFactory = new JestClientFactory();
        clientFactory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://119.23.146.107:9200")
                .multiThreaded(true)
                .defaultMaxTotalConnectionPerRoute(2)
                .maxTotalConnection(20)
                .build());
        JestClient client = clientFactory.getObject();*/
}
