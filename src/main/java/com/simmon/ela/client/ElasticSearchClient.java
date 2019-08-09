package com.simmon.ela.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ElasticSearchClient {
    private static RestHighLevelClient client = null;
    static {
        String hostsString = "localhost:9200";
        String[] nodes = hostsString.split(",");
        List<HttpHost> httpHosts = new ArrayList<>();
        for (String node : nodes) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.notNull(parts, "Must defined");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                httpHosts.add(new HttpHost(parts[0], Integer.parseInt(parts[1]), "http"));
            } catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid ES nodes " + "property '" + node + "'", ex);
            }
        }
        client= EsClientBuilder.build(httpHosts)
                .setConnectionRequestTimeoutMillis(10000)
                .setConnectTimeoutMillis(10000)
                .setSocketTimeoutMillis(30000)
                .setMaxConnectTotal(100)
                .setMaxConnectPerRoute(20)
                .create();

    }

    public static RestHighLevelClient getClient() {
        return client;
    }


}
