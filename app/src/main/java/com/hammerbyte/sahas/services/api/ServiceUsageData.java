package com.hammerbyte.sahas.services.api;


import org.json.JSONObject;


public interface ServiceUsageData extends ServiceAPI {

    void submitUsageData(JSONObject usageData);
    void onUsageDataSubmitted();



}
