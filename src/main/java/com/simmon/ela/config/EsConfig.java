package com.simmon.ela.config;

import com.simmon.ela.client.ElasticSearchClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

    @Bean
    public RestHighLevelClient client(){
        return ElasticSearchClient.getClient();
    }
}
