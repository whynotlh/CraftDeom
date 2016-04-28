package utility;


import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class URIBuilder {

    private UriBuilder builder;
    private String BASE = "";
    private String apiVersion = "";
    private String uriMethod = "";
    private String path = "";

    public URIBuilder(String Base, String apiVersion, String uriMethod){
        this.BASE = Base;
        this.apiVersion = apiVersion;
        this.uriMethod = uriMethod;
        builder = UriBuilder.fromPath("https://" + Base + "/" + apiVersion + "/" + uriMethod);
    }

    public URIBuilder(String Base, String apiVersion, String uriMethod, String path){
        this.BASE = Base;
        this.apiVersion = apiVersion;
        this.uriMethod = uriMethod;
        this.path = path;
        builder = UriBuilder.fromPath("https://" + Base + "/" + apiVersion + "/" + uriMethod + "/" + path);
    }

    public void addQueryParameters(String key, String value){
        builder = builder.queryParam(key, value);
    }

    public URI build(){
        return this.builder.build();
    }


    public String getBASE() {
        return BASE;
    }

    public void setBASE(String BASE) {
        this.BASE = BASE;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getUriMethod() {
        return uriMethod;
    }

    public void setUriMethod(String uriMethod) {
        this.uriMethod = uriMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
