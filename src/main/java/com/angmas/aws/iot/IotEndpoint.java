package com.angmas.aws.iot;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iotdata.AWSIotData;
import com.amazonaws.services.iotdata.AWSIotDataClientBuilder;


import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.Consumer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.util.ObjectHelper;

/**
 * The aws-sqs component is used for sending and receiving messages to Amazon's SQS service.
 */
@UriEndpoint(firstVersion = "2.6.0", scheme = "aws-iot-data", title = "AWS IoT Producer", syntax = "aws-iot-data",
    label = "cloud,messaging")
public class IotEndpoint extends DefaultEndpoint {

    private AWSIotData client;

    @UriParam
    private IotConfiguration configuration;


    public IotEndpoint(String uri, IotComponent component, IotConfiguration configuration) {
        super(uri, component);
        this.configuration = configuration;
    }

    public Producer createProducer() throws Exception {
        return new IotProducer(this);
    }

    public Consumer createConsumer(Processor p) throws Exception {
        throw new UnsupportedOperationException("aws-iot-data does not support Consumers");
    }

    public boolean isSingleton() {
        return true;
    }

    @Override
    protected void doStart() throws Exception {
        client = getConfiguration().getAwsIotClient() != null
            ? getConfiguration().getAwsIotClient() : getClient();
    }


    @Override
    public void doStop() throws Exception {
        if (ObjectHelper.isEmpty(configuration.getAwsIotClient())) {
            if (client != null) {
                client.shutdown();
            }
        }
        super.doStop();
    }

    public IotConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(IotConfiguration configuration) {
        this.configuration = configuration;
    }

    public AWSIotData getClient() {
        if (client == null) {
            client = createClient();
        }
        return client;
    }

    public void setClient(AWSIotData client) {
        this.client = client;
    }

    /**
     * Provide the possibility to override this method for an mock implementation
     * @return AmazonSQSClient
     */
    AWSIotData createClient() {
        AWSIotData client = null;
        AWSIotDataClientBuilder clientBuilder = null;
        ClientConfiguration clientConfiguration = null;
        boolean isClientConfigFound = false;
        if (ObjectHelper.isNotEmpty(configuration.getProxyHost()) && ObjectHelper.isNotEmpty(configuration.getProxyPort())) {
            clientConfiguration = new ClientConfiguration();
            clientConfiguration.setProxyHost(configuration.getProxyHost());
            clientConfiguration.setProxyPort(configuration.getProxyPort());
            isClientConfigFound = true;
        }
        if (configuration.getAccessKey() != null && configuration.getSecretKey() != null) {
            AWSCredentials credentials = new BasicAWSCredentials(configuration.getAccessKey(), configuration.getSecretKey());
            AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
            if (isClientConfigFound) {
                clientBuilder = AWSIotDataClientBuilder.standard().withClientConfiguration(clientConfiguration).withCredentials(credentialsProvider);
            } else {
                clientBuilder = AWSIotDataClientBuilder.standard().withCredentials(credentialsProvider);
            }
        } else {
            if (isClientConfigFound) {
                clientBuilder = AWSIotDataClientBuilder.standard();
            } else {
                clientBuilder = AWSIotDataClientBuilder.standard().withClientConfiguration(clientConfiguration);
            }
        }
        if (ObjectHelper.isNotEmpty(configuration.getRegion())) {
            clientBuilder = clientBuilder.withRegion(Regions.valueOf(configuration.getRegion()));
        }
        client = clientBuilder.build();
        return client;
    }


}