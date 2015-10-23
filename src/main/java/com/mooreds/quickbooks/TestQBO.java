package com.mooreds.quickbooks;


import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;

public class TestQBO {

    public static void main(String[] args) throws FMSException {
        String consumerKey = "...";
        String consumerSecret = "...";
        String accessToken = "...";
        String accessTokenSecret = "...";
        String appToken = "...";
        String companyId = "...";

        OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey,
                consumerSecret, accessToken, accessTokenSecret);

        Context context = new Context(oauth, appToken, ServiceType.QBO,
                companyId);

        DataService service = new DataService(context);

        Customer customer = GenerateQuery.createQueryEntity(Customer.class);

        String query = select($(customer.getId()), $(customer.getGivenName()))
                .generate();

        QueryResult queryResult = service.executeQuery(query);

        System.out.println("from query: "
                + ((Customer) queryResult.getEntities().get(0)).getGivenName());
    }
}
