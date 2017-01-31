package automation.jenkins.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;

public class HttpClient {

    private static String Jenkins_UserName = "Asaxena", Jenkins_Password = "Aditya1";

    public HttpClient() {

    }
    
    public ClientResponse getHttpResponse(String resourceURL) {
        Client client = getResponseFromParty();
        WebResource webResourceGet = client.resource(resourceURL);
        ClientResponse response = webResourceGet.get(ClientResponse.class);
        return response;
    }

    private Client getResponseFromParty() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);
        Jenkins_UserName = System.getProperty("jenkinsUserName", Jenkins_UserName);
        Jenkins_Password = System.getProperty("jenkinsPassword", Jenkins_Password);
        client.addFilter(new HTTPBasicAuthFilter(Jenkins_UserName, Jenkins_Password));
        return client;  
    }
}
