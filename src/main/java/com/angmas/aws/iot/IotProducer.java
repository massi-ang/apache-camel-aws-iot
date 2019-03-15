package com.angmas.aws.iot;


/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.nio.ByteBuffer;


import com.amazonaws.services.iotdata.AWSIotData;
import com.amazonaws.services.iotdata.model.PublishRequest;
import com.amazonaws.services.iotdata.model.PublishResult;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.NoFactoryAvailableException;
import org.apache.camel.support.DefaultProducer;
import org.apache.camel.util.URISupport;

/**
 * A Producer which sends messages to the Amazon Web Service Simple Queue
 * Service <a href="http://aws.amazon.com/sqs/">AWS SQS</a>
 */
public class IotProducer extends DefaultProducer {

    private transient String iotProducerToString;

    public IotProducer(IotEndpoint endpoint) throws NoFactoryAvailableException {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception {
        processSingleMessage(exchange);
    }

    public void processSingleMessage(final Exchange exchange) {
        ByteBuffer body = exchange.getIn().getBody(ByteBuffer.class);
        PublishRequest request = new PublishRequest();
        log.trace("Body: {}", body.toString());
        log.trace(exchange.getIn().getHeaders().toString());
        request.setPayload(body);
        request.setQos((Integer)exchange.getIn().getHeader("qos"));
        request.setTopic((String)exchange.getIn().getHeader("topic"));
        log.trace("Sending request [{}] from exchange [{}]...", request, exchange);

        PublishResult result = getClient().publish(request);

        log.trace("Received result [{}]", result);
    }

    protected AWSIotData getClient() {
        return getEndpoint().getClient();
    }

    protected IotConfiguration getConfiguration() {
        return getEndpoint().getConfiguration();
    }

    @Override
    public IotEndpoint getEndpoint() {
        return (IotEndpoint)super.getEndpoint();
    }

    @Override
    public String toString() {
        if (iotProducerToString == null) {
            iotProducerToString = "IotProducer[" + URISupport.sanitizeUri(getEndpoint().getEndpointUri()) + "]";
        }
        return iotProducerToString;
    }


    public static Message getMessageForResponse(final Exchange exchange) {
        if (exchange.getPattern().isOutCapable()) {
            Message out = exchange.getOut();
            out.copyFrom(exchange.getIn());
            return out;
        }
        return exchange.getIn();
    }

}
