package com.angmas.aws.iot;

import com.amazonaws.services.iotdata.AWSIotData;
import org.apache.camel.Endpoint;
import org.apache.camel.Producer;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IotEndpointTest extends CamelTestSupport {

    @Test
    public void createProducer() throws Exception {
        Endpoint endpoint = context.getEndpoint("aws-iot-data:iot?accessKey=AAA&secretKey=BBB");
        assertTrue("endpoint is an IotEnpoint instance", endpoint instanceof IotEndpoint);

        IotEndpoint iotendpoint = (IotEndpoint)endpoint;
        Producer producer = iotendpoint.createProducer();
        assertTrue("producer is an IotProducer", producer instanceof IotProducer);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createConsumer() throws Exception {
        Endpoint endpoint = context.getEndpoint("aws-iot-data:iot?accessKey=AAA&secretKey=BBB");
        assertTrue("endpoint is an IotEnpoint instance", endpoint instanceof IotEndpoint);

        IotEndpoint iotendpoint = (IotEndpoint)endpoint;
        iotendpoint.createConsumer(context.getProcessor("aa"));
    }

    @Test
    public void endpointStarts() throws Exception {
        Endpoint endpoint = context.getEndpoint("aws-iot-data:iot?accessKey=AAA&secretKey=BBB");
        assertTrue("endpoint is an IotEnpoint instance", endpoint instanceof IotEndpoint);

        IotEndpoint iotendpoint = (IotEndpoint)endpoint;
        iotendpoint.doStart();
        AWSIotData client = iotendpoint.getClient();
        assertTrue("The client is created and is of type AWSIotData", client instanceof AWSIotData);
        iotendpoint.doStop();
        assertTrue("The client has shutdown", true);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}