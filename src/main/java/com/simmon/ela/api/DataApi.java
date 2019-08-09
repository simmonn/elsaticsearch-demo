package com.simmon.ela.api;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("data")
public class DataApi extends BaseApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataApi.class);


//    @Autowired
//    RestHighLevelClient client;

    private static String index = "novel-spread";


    @RequestMapping(value = "del", method = RequestMethod.GET)
    public void putData() throws IOException {
        DeleteRequest request = new DeleteRequest(index, "novelInfo", "1");
        client.deleteAsync(request, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
                    System.out.println("success");
                }
                System.out.println(deleteResponse.status());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void update() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.field("novel", "sam");
        builder.field("novelId", 156);
        builder.field("func", "update");
        builder.field("userId", 12);
        builder.timeField("publicTime", new Date());
        builder.endObject();
        UpdateRequest request = new UpdateRequest(index, "novelInfo", "1").doc(builder);

        client.updateAsync(request, RequestOptions.DEFAULT, new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    System.out.println("updated");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

    @RequestMapping(value = "/{novel}", method = RequestMethod.GET)
    public String search(@PathVariable String novel) throws IOException {
        SearchRequest request = new SearchRequest("flying-*");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.fuzzyQuery("novel", novel));
        builder.from(0);
        builder.size(10);
        builder.timeout(TimeValue.MINUS_ONE);
        request.source(builder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        if (hits.getTotalHits() == 0) {
            System.out.println("no matching");
        }
        for (SearchHit documentFields : hits1) {
            return documentFields.toString();
        }

        LOGGER.info("time:{}", search.getTook().millis());
        return "success";

    }


}
