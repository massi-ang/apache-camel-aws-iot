package com.angmas.aws.iot;

import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.camel.Endpoint;

import static org.junit.Assert.*;

public class IotConfigurationTest extends CamelTestSupport {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }


    @Test
    public void itAcceptsValidConfiguration() throws Exception {
        Endpoint endpoint = context.getEndpoint("aws-iot-data:iot?topic=test&qos=2&accessKey=ZZZ&secretKey=AAA&region=eu_west_1");

        assertTrue("Endpoint not an aws-iot-data endpoint", endpoint instanceof IotEndpoint);
        IotEndpoint iotEndpoint = (IotEndpoint)endpoint;

        assertEquals(iotEndpoint.getConfiguration().getTopic(), "test");
        assertEquals((int)iotEndpoint.getConfiguration().getQos(), (int)2);
        assertEquals(iotEndpoint.getConfiguration().getAccessKey(), "ZZZ");
        assertEquals(iotEndpoint.getConfiguration().getSecretKey(), "AAA");

        assertEquals(iotEndpoint.getConfiguration().getRegion(), "EU_WEST_1");
    }


}