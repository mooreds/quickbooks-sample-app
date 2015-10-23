# quickbooks-sample-app
  
  
## Libs

git clone https://github.com/IntuitDeveloper/SampleApp-TimeTracking_Invoicing-Java
cd sample
mkdir lib
cp ../SampleApp-TimeTracking_Invoicing-Java/libs/qbo-sdk-2.3.1/ipp-* lib/
mvn install:install-file -Dfile=lib/ipp-java-qbapihelper-1.2.0-jar-with-dependencies.jar -DgroupId=com.intuit.ipp -DartifactId=ipp-java-qbapihelper -Dversion=1.2.0 -Dpackaging=jar 
mvn install:install-file -Dfile=lib/ipp-v3-java-data-2.3.1.jar -DgroupId=com.intuit.ipp -DartifactId=ipp-v3-java-data -Dversion=2.3.1 -Dpackaging=jar 
mvn install:install-file -Dfile=lib/ipp-v3-java-devkit-2.3.1-jar-with-dependencies.jar -DgroupId=com.intuit.ipp -DartifactId=ipp-v3-java-devkit -Dversion=2.3.1 -Dpackaging=jar 

promote app to production (use IE).  unable to find test sandbox urls

 mvn exec:java -Dexec.mainClass="com.mooreds.quickbooks.TestQBO"

 https://developer.intuit.com/blog/2015/02/19/oauth-for-intuit-demystified
