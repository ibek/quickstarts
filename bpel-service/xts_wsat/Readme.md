Introduction
============
This quickstart demostrates the use of WS-AtomicTransaction in BPEL process which invokes a web service operation requiring WS-AtomicTransaction.


Running the quickstart
======================

JBoss AS 7
----------
1. Build the quickstart:
    mvn clean install
2. Configure JBoss AS7 to enable XTS (https://community.jboss.org/wiki/DistributedTransactionsInRiftSaw3#JBoss_AS7_XTS_Configuration) 
2. Start JBoss AS 7 in standalone-xts mode:
    ${AS}/bin/standalone.sh --server-config=standalone-xts.xml
3. Deploy the BPEL process and the Web service :
    cp bpel/target/switchyard-quickstart-bpel-service-xts-wsat-bpel.jar ${AS7}/standalone/deployments
    cp ws/target/switchyard-quickstart-bpel-service-xts-wsat-ws.jar ${AS7}/standalone/deployments
4. Submit a webservice request to invoke the SOAP gateway.  There are a number of ways to do this :
      - Submit a request with your preferred SOAP client - src/test/resources/xml contains sample
        requests and the responses that you should see
      - SOAP-UI : Use the wsdl for this projects (src/main/resources/wsdl/) to create a soap-ui project.
        Use the sample request (src/test/resources/xml/soap-request.xml) as an example of a sample
        request.
