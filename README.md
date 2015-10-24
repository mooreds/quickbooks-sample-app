# quickbooks-sample-app
  
  
## Libs

You need to install these libs to your local maven repository to use the sample app.

* git clone https://github.com/IntuitDeveloper/SampleApp-TimeTracking_Invoicing-Java
* cd sample
* mkdir lib
* cp ../SampleApp-TimeTracking_Invoicing-Java/libs/qbo-sdk-2.3.1/ipp-* lib/
* mvn install:install-file -Dfile=lib/ipp-java-qbapihelper-1.2.0-jar-with-dependencies.jar -DgroupId=com.intuit.ipp -DartifactId=ipp-java-qbapihelper -Dversion=1.2.0 -Dpackaging=jar 
* mvn install:install-file -Dfile=lib/ipp-v3-java-data-2.3.1.jar -DgroupId=com.intuit.ipp -DartifactId=ipp-v3-java-data -Dversion=2.3.1 -Dpackaging=jar 
* mvn install:install-file -Dfile=lib/ipp-v3-java-devkit-2.3.1-jar-with-dependencies.jar -DgroupId=com.intuit.ipp -DartifactId=ipp-v3-java-devkit -Dversion=2.3.1 -Dpackaging=jar 

## App

Create app in qbo developer center.

Promote app to production (use IE).  I was unable to find test sandbox oauth urls.  You need to verify your email address for this.

Get the access token (good for 180 days), by uncommenting first part of the `TestQBO` class.

Command to run:

mvn exec:java -Dexec.mainClass="com.mooreds.quickbooks.TestQBO"

## Links

https://developer.intuit.com/blog/2015/02/19/oauth-for-intuit-demystified

java docs: https://developer.intuit.com/docs/0100_accounting/0500_developer_kits/0200_java/java_sdk_for_quickbooks_v3.0

Oauth for java: https://github.com/mttkay/signpost/blob/master/docs/GettingStarted.md

API Explorer: https://developer.intuit.com/v2/apiexplorer?apiname=V3QBO#?id=Customer

Queries examples: https://developer.intuit.com/docs/0100_accounting/0500_developer_kits/0200_java/java_sdk_for_quickbooks_v3.0/0011_query_filters#/Query_code_samples
