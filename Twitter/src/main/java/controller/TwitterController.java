package controller;

import model.Status;
import model.TwitterResponse;
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
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("/Twitter")
public class TwitterController {

    public static final String QUICKBOOKS_HASHTAG = "#quickbooks";
    public static final String QUICKBOOKS_ATTAG = "@quickbooks";

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll(){
        JsonArrayBuilder builder = Json.createArrayBuilder();
        Map<String, String> queryParams = new LinkedHashMap<String, String>();
        queryParams.put("q", QUICKBOOKS_HASHTAG);
        final List<Status> statuses = getDefaultTwitterResponseWithGivenParams(queryParams);
        buildTwitterJsonResponse(statuses,builder);
        return builder.build();
    }

    @Path("/atQuickBookAPI")
    @GET
    @Produces("application/json")
    public JsonArray getTargetAtQuickBookAPI(){
        JsonArrayBuilder builder = Json.createArrayBuilder();
        Map<String, String> queryParams = new LinkedHashMap<String, String>();
        queryParams.put("q", QUICKBOOKS_ATTAG);
        queryParams.put("result_type", "recent");
        final List<Status> statuses = getDefaultTwitterResponseWithGivenParams(queryParams);
        buildTwitterJsonResponse(statuses,builder);
        return builder.build();
    }


    public List<Status> getDefaultTwitterResponseWithGivenParams(Map<String, String> params){
        List<Status> responseStatus = null;
        try {
            Client twitterClient = JerseyClient.getDefaultTwitterClient();
            URIBuilder twitterURIBuilder = new URIBuilder("api.twitter.com", "1.1", "search", "tweets.json");
            for (String s : params.keySet()) {
                twitterURIBuilder.addQueryParameters(s, URLEncoder.encode(params.get(s), "UTF-8"));
            }
            URI uri = twitterURIBuilder.build();
            final Response response = twitterClient.target(uri.toString()).request().get();
            final TwitterResponse twitterResponse = response.readEntity(TwitterResponse.class);
            responseStatus = twitterResponse.getStatues();
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseStatus;

    }

    public void buildTwitterJsonResponse(List<Status> lists, JsonArrayBuilder builder){
        for(final Status s: lists){
            builder.add(Json.createObjectBuilder()
                .add("Username", s.getUser().getName())
                    .add("Content", s.getText())
                    .add("Created_Time", s.getCreatedAt()));

        }
    }

}