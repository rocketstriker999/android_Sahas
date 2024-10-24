package com.hammerbyte.sahas.utils;

import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class Utils {


    public static final Map<String, String> REGEX = Map.of("REGEX_EMAIL", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "REGEX_PASSWORD", "^([a-zA-Z0-9@*#]{8,24})$", "REGEX_PHONE", "^[6789]\\d{9}$", "REGEX_USER_NAME", "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");



    private Utils() {
    }

    public static boolean isNetworkAvailable(ConnectivityManager connectivityManager) {
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
        return false;
    }


    public static String getUpdateBinary(String endPoint, String appUpdateBinaryDirectory, @NonNull JSONObject postBody) throws IOException {
        // Generate a unique filename using the current timestamp
        String fileName = "update_" + System.currentTimeMillis() + ".apk";
        String appUpdateBinaryLocalPath = appUpdateBinaryDirectory + File.separator + fileName;  // Concatenate directory and filename

        URL url = new URL(Server.URL_UPDATE(endPoint).concat("?no_cache=").concat(String.valueOf(System.currentTimeMillis() / 1000L)));
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json");

        // Send the POST body
        try (OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream())) {
            wr.write(postBody.toString());
            wr.flush();
        }

        // Download the APK and save it to the specified directory
        try (InputStream inputStream = urlConnection.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(appUpdateBinaryLocalPath)) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }
        }

        // Return the full path of the downloaded APK
        return appUpdateBinaryLocalPath;
    }



    public static HashMap<String, Object> getAPIResponse(String endPoint, @NonNull JSONObject postBody) throws IOException, JSONException {
        HashMap<String, Object> response = new HashMap<>();
        URL url = new URL(Server.URL_API.concat(endPoint).concat("?no_cache=").concat(String.valueOf(System.currentTimeMillis() / 1000L)));
        System.out.println("[+] Request : " + url);
        System.out.println("[+] Parameters : " + postBody);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        // Write JSON to output stream
        try (OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream())) {
            wr.write(postBody.toString());
            wr.flush();
        }
        // Get response code
        int responseCode = urlConnection.getResponseCode();
        response.put("RESPONSE_CODE", responseCode);
        StringBuilder stringBuilder = getStringBuilder(responseCode, urlConnection);
        urlConnection.disconnect();
        // Remove line while in production
        System.out.println("[+] SERVER OUTPUT :" + stringBuilder);
        response.put("RESPONSE_BODY", new JSONObject(stringBuilder.toString()));
        return response;
    }

    private static @NonNull StringBuilder getStringBuilder(int responseCode, HttpURLConnection urlConnection) throws IOException {
        BufferedReader reader;
        if (responseCode >= 200 && responseCode < 300) { // Success
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } else { // Error
            reader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder;
    }


    public static class Server {

        public static final String SERVER_URL = "https://sahasinstitute.com/";
        public static final String URL_API = SERVER_URL.concat("apis/");
        public static final String URL_BINARY = SERVER_URL.concat("binary/");


        public static String URL_UPDATE(String appUpdateProvider) {
            return SERVER_URL.concat(URL_BINARY).concat("updates/android/").concat(appUpdateProvider);
        }

        public static String URL_THUMBNAILS(String thumbnailFile) {
            return URL_BINARY.concat("thumbnails/").concat(thumbnailFile);
        }

        public static String URL_PAYMENT_RECEIPTS(String purchaseId) {
            return URL_BINARY.concat("generateReceipt.php?purchase_id=").concat(purchaseId);
        }

        public static String URL_AUDIO(String fileId) {
            return "https://drive.google.com/uc?authuser=0&id=".concat(fileId).concat("&export=download");
        }
    }
}