package com.hammerbyte.sahas.services.api;

import com.hammerbyte.sahas.activities.ActivityUpdate;
import com.hammerbyte.sahas.application.ApplicationSahas;
import com.hammerbyte.sahas.exceptions.NoNetWorkException;
import com.hammerbyte.sahas.fragments.updates.FragmentUpdateInstall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public interface ServiceUpdate extends ServiceAPI{


    void checkForUpdate();
    void onUpdateAvailable(HashMap<String, String> updateData);
    void onUpdateUnAvailable();
    void updateProgressVisibility(boolean visibility);

    default void startUpdateDownload(WeakReference<ApplicationSahas> appInstance, String endPoint) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            onUpdateDownloadStart();
            try {
                //onUpdateDownloadCompleted(appInstance.get().requestUpdateBinary(endPoint));
                onUpdateDownloadCompleted(1,new File(""));
            }  finally {
                executorService.shutdown();
                onAPIRequestEnd();
            }
        });
    }
    void onUpdateDownloadStart();

    void onUpdateDownloadCompleted(long downloadId, File updateFile);
    void onUpdateDownloadFailed();

    void onUpdateInstalled();

    void runOnUiThread(Runnable runnable);
}
