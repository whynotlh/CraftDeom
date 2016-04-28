package utility;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.GZipEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Convenient Jersey singleton to return client object
 */
public class JerseyClient {

    //Twitter Key header
    public static final String CONSUMER_KEY = "Consumer_key";
    public static final String CONSUMER_SECRET = "Consumer_secret";
    public static final String ACCESS_TOKEN = "Access_token";
    public static final String ACCESS_TOKEN_SECRET = "Access_token_secret";

    //StackExchange Key Params
    public static final String STACK_EXCHANGE_APPLICATION_KEY = "Application_key";

    protected static Properties properties;
    private static Client twitterClient;
    private static Client stackExchangeClient;

    static {
        properties = new Properties();
        InputStream inputStream= null;

        try{
            inputStream = new FileInputStream("/root/eclipse_ws/Twitter/src/main/java/config/auth.properties");
            properties.load(inputStream);

        } catch (IOException io){
            io.printStackTrace();
        }
    }


    public static Client getDefaultStackExchangeClient(){
        if(stackExchangeClient != null){
            return stackExchangeClient;
        }else{
            stackExchangeClient = ClientBuilder.newBuilder()
                    .register(GZipEncoder.class)
                    .register(JacksonFeature.class)
                    .build();

            return stackExchangeClient;
        }
    }


    public static Client getDefaultTwitterClient(){

        final Feature defaultOAuth1FilterFeature = defaultOAuth1FilterForTwitter();
        if(twitterClient != null) return twitterClient;
        else {
            twitterClient = ClientBuilder.newBuilder()
                    .register(defaultOAuth1FilterFeature)
                    .register(JacksonFeature.class)
                    .build();

            return twitterClient;
        }

    }

    public static Feature defaultOAuth1FilterForTwitter(){
        final ConsumerCredentials credentials = new ConsumerCredentials(properties.getProperty(CONSUMER_KEY),
                                                    properties.getProperty(CONSUMER_SECRET));


        final AccessToken accessToken = new AccessToken(
                properties.getProperty(ACCESS_TOKEN),
                properties.getProperty(ACCESS_TOKEN_SECRET));

        return OAuth1ClientSupport.builder(credentials)
                .feature().accessToken(accessToken).build();

    }

    public static Properties getProperties(){
        return properties;
    }

}
