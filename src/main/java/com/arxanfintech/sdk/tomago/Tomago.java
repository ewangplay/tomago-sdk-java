/*******************************************************************************
Copyright ArxanFintech Technology Ltd. 2018 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

                 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*******************************************************************************/
package com.arxanfintech.sdk.tomago;

import java.io.FileInputStream;
import java.util.List;
import org.apache.http.NameValuePair;
//import org.omg.CORBA.Request;
import org.apache.http.Header;

import com.arxanfintech.common.crypto.Crypto;
import com.arxanfintech.common.rest.*;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Tomago Api Sdk
 *
 */
public class Tomago {
    private Client client;
    private Crypto crypto;

    public Tomago(Client client) {
        this.client = client;
        String privateKeyPath = client.GetCertPath() + "/users/" + client.GetApiKey() + "/" + client.GetApiKey() + ".key";
        String publicCertPath = client.GetCertPath() + "/tls/tls.cert";
        try {
            this.crypto = new Crypto(new FileInputStream(privateKeyPath), new FileInputStream(publicCertPath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String Invoke(JSONObject jsonheader, JSONObject jsonbody) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/tomago/v2/blockchain/invoke";

        System.out.println(request.header.toString());
        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String Query(JSONObject jsonheader, JSONObject jsonbody) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress()+ "/tomago/v2/blockchain/query";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String Transaction(JSONObject jsonheader, String id) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/tomago/v2/blockchain/transaction/" + id;

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
