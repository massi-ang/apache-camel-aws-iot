package com.angmas.aws.iot;



import com.amazonaws.services.iotdata.AWSIotData;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

@UriParams
public class IotConfiguration implements Cloneable {
    public static final String PUBLISH_QOS = "CamelAWSIotQos";
    public static final String PUBLISH_TOPIC = "CamelAWSIotTopic";
    // common properties
    @UriParam
    private AWSIotData awsIotClient;
    @UriParam(label = "security", secret = true)
    private String accessKey;
    @UriParam(label = "security", secret = true)
    private String secretKey;
    @UriParam(defaultValue = "amazonaws.com")
    private String amazonAWSHost = "amazonaws.com";
    @UriParam(secret = true)
    private String iotOwnerAWSAccountId;
    @UriParam
    private String region;
    @UriParam(label = "proxy")
    private String proxyHost;
    @UriParam(label = "proxy")
    private Integer proxyPort;

    // producer properties
    @UriParam(label = "producer")
    private String topic;
    @UriParam(label = "producer")
    private Integer qos;


    public String getAmazonAWSHost() {
        return amazonAWSHost;
    }

    /**
     * The hostname of the Amazon AWS cloud.
     */
    public void setAmazonAWSHost(String amazonAWSHost) {
        this.amazonAWSHost = amazonAWSHost;
    }

    public String getTopic() {
        return topic;
    }

    /**
     * Name of topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getQos() {
        return qos;
    }

    /**
     * Name of topic
     */
    public void setQos(Integer qos) {
        this.qos = qos;
    }


    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Amazon AWS Access Key
     */
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Amazon AWS Secret Key
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public AWSIotData getAwsIotClient() {
        return awsIotClient;
    }

    public void setAwsIotClient(AWSIotData awsIotClient) {
        this.awsIotClient = awsIotClient;
    }

    public IotConfiguration copy() {
        try {
            return (IotConfiguration)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getIotOwnerAWSAccountId() {
        return iotOwnerAWSAccountId;
    }

    public void setIotOwnerAWSAccountId(String iotOwnerAWSAccountId) {
        this.iotOwnerAWSAccountId = iotOwnerAWSAccountId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        if (region != null) {
            region = region.toUpperCase();
        }
        this.region = region;
    }
}
