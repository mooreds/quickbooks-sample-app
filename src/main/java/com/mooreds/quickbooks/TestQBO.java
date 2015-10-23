package com.mooreds.quickbooks;


import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;

public class TestQBO {

//    private static final String REQUEST_TOKEN_ENDPOINT_URL = "https://oauth.intuit.com/oauth/v1/get_request_token";
//    private static final String ACCESS_TOKEN_ENDPOINT_URL =  "https://oauth.intuit.com/oauth/v1/get_access_token";
//    private static final String AUTHORIZE_WEBSITE_URL = "https://appcenter.intuit.com/Connect/Begin";
    private static final String REQUEST_TOKEN_ENDPOINT_URL = "https://oauth.intuit.com/oauth/v1/get_request_token";
    private static final String ACCESS_TOKEN_ENDPOINT_URL =  "https://oauth.intuit.com/oauth/v1/get_access_token";
    private static final String AUTHORIZE_WEBSITE_URL = "https://appcenter.intuit.com/Connect/Begin";

    public static void main(String[] args) throws FMSException, OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
        String accessToken = "...";
        String accessTokenSecret = "...";
        // fill in from qbo app form
        String consumerKey = "";
        String consumerSecret = "";
        String appToken = "";
        
        // fill in from qbo explorer
        String companyId = "";
        
        OAuthProvider provider = new DefaultOAuthProvider(
                REQUEST_TOKEN_ENDPOINT_URL, ACCESS_TOKEN_ENDPOINT_URL,
                AUTHORIZE_WEBSITE_URL);
        
        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey,
                consumerSecret);
        /*
         * 
         * only needs to be done once every 180 days
        String url = provider.retrieveRequestToken(consumer,  "https://httpbin.org/get"); // or some other website for logs, like https://httpbin.org/get
        
        //String url = provider.retrieveRequestToken(consumer,  OAuth.OUT_OF_BAND); // gives 500 error, no way to get verification token
        
        System.out.println("visit url in IE: "+url);
       
        System.out.println("Enter value from oauth_verified");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String verificationToken = in.readLine();
        
        System.out.println("token: "+verificationToken);
        
        provider.retrieveAccessToken(consumer, verificationToken);
        accessToken = consumer.getToken();
        accessTokenSecret = consumer.getTokenSecret();
        
        System.out.println("token: "+accessToken);
        System.out.println("secrettoken: "+accessTokenSecret);
        
        System.exit(0);
       */

        accessToken = "...";
        accessTokenSecret = "...";
        

        OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey,
                consumerSecret, accessToken, accessTokenSecret);
        
        Context context = new Context(oauth, appToken, ServiceType.QBO,
                companyId);

        DataService service = new DataService(context);

        // search for first time
        Customer customer = GenerateQuery.createQueryEntity(Customer.class);

        String query = select($(customer)).generate();

        QueryResult queryResult = service.executeQuery(query);
        
        Customer custFromDb = (Customer) queryResult.getEntities().get(0);

        System.out.println("from query: "
                + custFromDb.getFamilyName() + ", "
                + custFromDb.getGivenName());
        
        custFromDb.setFamilyName("Test");
        
        service.update(custFromDb);
        
        // search again
        queryResult = service.executeQuery(query);
        
        custFromDb = (Customer) queryResult.getEntities().get(0);

        System.out.println("from query: "
                + custFromDb.getFamilyName() + ", "
                + custFromDb.getGivenName());
        
                
    }
}
