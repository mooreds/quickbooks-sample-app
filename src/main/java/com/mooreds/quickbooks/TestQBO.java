package com.mooreds.quickbooks;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.StAXEventBuilder;
import org.jdom2.input.StAXStreamBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.*;

import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.intuit.ipp.data.PurchaseOrder;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;

public class TestQBO {

    public static final String pathname = "samplepo.xml";
    
//    private static final String REQUEST_TOKEN_ENDPOINT_URL = "https://oauth.intuit.com/oauth/v1/get_request_token";
//    private static final String ACCESS_TOKEN_ENDPOINT_URL =  "https://oauth.intuit.com/oauth/v1/get_access_token";
//    private static final String AUTHORIZE_WEBSITE_URL = "https://appcenter.intuit.com/Connect/Begin";
    private static final String REQUEST_TOKEN_ENDPOINT_URL = "https://oauth.intuit.com/oauth/v1/get_request_token";
    private static final String ACCESS_TOKEN_ENDPOINT_URL =  "https://oauth.intuit.com/oauth/v1/get_access_token";
    private static final String AUTHORIZE_WEBSITE_URL = "https://appcenter.intuit.com/Connect/Begin";

    public static void main(String[] args) throws FMSException, OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException, IOException, ParserConfigurationException, SAXException, JAXBException {
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
        
        
        /*  update code
        custFromDb.setFamilyName("Test");
        
        service.update(custFromDb);
        
        // search again
        queryResult = service.executeQuery(query);
        
        custFromDb = (Customer) queryResult.getEntities().get(0);

        System.out.println("from query: "
                + custFromDb.getFamilyName() + ", "
                + custFromDb.getGivenName());
        */
        
        /* couldn't make work
        JAXBContext jc = JAXBContext.newInstance(PurchaseOrder.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File(pathname);
        PurchaseOrders pos = (PurchaseOrders) unmarshaller.unmarshal(xml);
        
        System.out.println("po: "+pos.getPurchaseOrders().size());
        */
        /*
         * 
         * using jdom to parse files
        File xml = new File(pathname);
        
 
        //we can create JDOM Document from DOM, SAX and STAX Parser Builder classes
        org.jdom2.Document jdomDoc = useDOMParser(xml.getAbsolutePath());
        Element root = jdomDoc.getRootElement();
        List<Element> elements = root.getChildren("PurchaseOrder");
        
        List<PurchaseOrder> purchaseOrderList = new ArrayList();
        for (Element element : elements ) {
            PurchaseOrder po = new PurchaseOrder();
            po.setId(element.getChildText("Id"));
            po.setTotalAmt(BigDecimal.valueOf(Double.valueOf(element.getChildText("TotalAmt"))));
            purchaseOrderList.add(po);
        }
        
        System.out.println("total: "+purchaseOrderList.size());
        */
    }
    
    //Get JDOM document from DOM Parser
    private static org.jdom2.Document useDOMParser(String fileName)
            throws ParserConfigurationException, SAXException, IOException {
        //creating DOM Document
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(fileName));
        DOMBuilder domBuilder = new DOMBuilder();
        return domBuilder.build(doc);

    }
}
