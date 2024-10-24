package com.hammerbyte.sahas.services.api;

import com.hammerbyte.sahas.application.ApplicationSahas;
import com.hammerbyte.sahas.exceptions.NoNetWorkException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface ServiceAPI {

    void onAPIRequestStart();

    default void HitAPI(WeakReference<ApplicationSahas> appInstance, String endPoint,JSONObject postBody) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            onAPIRequestStart();
            try {
                onAPIResponseReceived(appInstance.get().requestAPI(endPoint, postBody));
            } catch (JSONException e) {
                e.printStackTrace();
                onAPIResponseParseError();
            } catch (IOException e) {
                e.printStackTrace();
                onAPIResponseNotReceived(endPoint);
            }catch (NoNetWorkException e) {
                onNoNetworkAvailable();
            }finally {
                executorService.shutdown();
                onAPIRequestEnd();
            }
        });
    }

    void onAPIResponseReceived(HashMap<String,Object> apiResponse);

    void onNoNetworkAvailable();

    void onAPIResponseNotReceived(String endPont);

    void onAPIResponseParseError();

    void onAPIRequestEnd();

}
