# status
[![Build Status](https://travis-ci.org/arxanchain/tomago-sdk-java.svg?branch=master)](https://travis-ci.org/arxanchain/tomago-sdk-java)

# tomago-sdk-java

Tomago is a project code name, which is used to wrap SmartContract invocation
from the business point of view, including APIs for managing digital assets,
asset owners (entities), etc. management, digital assets, etc. You need not
care about how the backend blockchain runs or the unintelligible techniques,
such as consensus, endorsement and decentralization. Simply use the SDK we
provide to implement your business logics, we will handle the caching, tagging,
compressing, encrypting and high availability.

We also provide a way from this SDK to invoke the SmartContract, a.k.a.
Chaincode, which is deployed by yourself.

This SDK enables *Java* developers to develop applications that interact with the
SmartContract which is deployed out of the box or by yourself in the ArxanChain
BaaS Platform via Tomago.

# Usage
Tomago-sdk-java is Maven projcet, we have already put this project to Maven Repository.
When you use tomago-sdk-java, you should reference project like this:

```pom.xml```

```
<dependency>
    <groupId>com.arxanfintech</groupId>
    <artifactId>tomago-sdk-java</artifactId>
    <version>1.5.1</version>
</dependency>
```

## Sample Usage
Tomago-sdk-java have three import API. For more details please refer to [tomago api](http://www.arxanfintech.com/infocenter/html/development/mycc.html)

Before you use tomago-sdk-java, you should prepare certificates.

The certificates include:

* The public key of ArxanChain BaaS Platform (server.crt) which is used to
  encrypt the data sent to Tomago service. You can download it from the
  ArxanChain BaaS ChainConsole -> System Management -> API Certs Management
* The private key of the client user (such as `APIKey.key`) which is used to sign the
  data. You can download it when you create an API Certificate.

After downloading the two files, use the following command to convert your private key file into PEM format.

```sh
$ openssl ec -in apikey.key -outform PEM -out apikey.key
```

Then copy (rename as follows) your TLS certificate and PEM private key file as follows path:

```
└── your_cert_dir
    ├── tls
    |   └── tls.cert
    └── users
        └── your-api-key
            └── your-api-key.key
```

#### invoke
```java
import com.arxanfintech.sdk.tomago.Tomago;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

String strdata = "{\"payload\": {\"chaincode_id\": \"mycc\",\"args\": [\"invoke\", \"a\", \"b\", \"1\"]} }";
JSONObject jsondata = JSON.parseObject(strdata);

String strheader = "{\"Callback-Url\":\"http://something.com\",\"Bc-Invoke-Mode\":\"sync\"}";
JSONObject jsonheader = JSON.parseObject(strheader);

Client client = new Client();
client.Address = "IP:PORT"; //YOUR SERVER ADDRESS
client.ApiKey = "5zt592jTM1524126512"; // API-KEY
client.CertPath = "/Users/yan/tmp/cert136"; //your_cert_dir

Tomago tomago = new Tomago(client);
       
String response = tomago.Invoke(jsonheader, jsondata);    
```

#### query
```java
import com.arxanfintech.sdk.tomago.Tomago;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

String strdata = "{\"payload\": {\"chaincode_id\": \"mycc\",\"args\":[\"query\", \"a\"]} }";
JSONObject jsondata = JSON.parseObject(strdata);

String strheader = "{\"Callback-Url\":\"http://something.com\",\"Bc-Invoke-Mode\":\"sync\"}";
JSONObject jsonheader = JSON.parseObject(strheader);

Client client = new Client();
client.Address = "IP:PORT";
client.ApiKey = "5zt592jTM1524126512";
client.CertPath = "/Users/yan/tmp/cert136";

Tomago tomago = new Tomago(client);
     
String response = tomago.Query(jsonheader, jsondata); 
```

#### transaction/{TXNID}
```java
import com.arxanfintech.sdk.tomago.Tomago;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

String id = "test0";

String strheader = "{\"Callback-Url\":\"http://something.com\"}";
JSONObject jsonheader = JSON.parseObject(strheader);

Client client = new Client();
client.Address = "IP:PORT";
client.ApiKey = "5zt592jTM1524126512";
client.CertPath = "/Users/yan/tmp/cert136";

Tomago tomago = new Tomago(client);
        
String response = tomago.Transaction(jsonheader, id);

```
