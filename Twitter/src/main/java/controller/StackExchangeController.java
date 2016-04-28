package controller;


import model.Question;
import model.StackResponse;

import utility.JerseyClient;
import utility.URIBuilder;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("/StackExchange")
public class StackExchangeController {

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll(){
        JsonArrayBuilder builder = Json.createArrayBuilder();
        Map<String, String> queryParams = new LinkedHashMap<String, String>();
        queryParams.put("tagged", "quickbooks");
        queryParams.put("site", "stackoverflow");
        queryParams.put("key", JerseyClient.getProperties().getProperty(JerseyClient.STACK_EXCHANGE_APPLICATION_KEY));

        final List<Question> questionList = defaultStachExchangeResponseWithGivenParams(queryParams);
        buildStackExchangeJsonResponse(questionList, builder);
        return builder.build();
    }

    public List<Question> defaultStachExchangeResponseWithGivenParams(Map<String, String> params){
        List<Question> responseQuestions = null;
        try {
            Client stackExchangeClient = JerseyClient.getDefaultStackExchangeClient();
            URIBuilder stackURIBuilder = new URIBuilder("api.stackexchange.com", "2.2", "questions");
            for (String s : params.keySet()) {
                stackURIBuilder.addQueryParameters(s, params.get(s));
            }
            URI uri = stackURIBuilder.build();
            final Response response = stackExchangeClient.target(uri.toString()).request().get();
            final StackResponse stackResponse = response.readEntity(StackResponse.class);
            responseQuestions = stackResponse.getItems();
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseQuestions;
    }

    public void buildStackExchangeJsonResponse(List<Question> lists, JsonArrayBuilder builder){
        for(final Question q: lists){
            builder.add(Json.createObjectBuilder()
                    .add("Title", q.getTitle())
                    .add("Link", q.getLink())
                    .add("Is_Answered", q.getIsAnswered())
                    .add("Score", q.getScore())
                    .add("Created_At", q.getCreatedAt()));
        }
    }
}
