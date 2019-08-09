package com.simmon.ela.api;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
public class BaseApi {

    @Autowired
    RestHighLevelClient client;

    @Override
    protected void finalize() throws Throwable {
        client.close();
        super.finalize();
    }
}
