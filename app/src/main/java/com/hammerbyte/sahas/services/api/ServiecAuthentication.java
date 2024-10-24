package com.hammerbyte.sahas.services.api;

public interface ServiecAuthentication extends ServiceAPI{

    void onAuthenticationSuccess();

    void onAuthenticationFailed();

    void onAuthenticationCanceled();

    void StartAuthentication(String email);

    void onUserNotFound();

    void onAuthenticationProgress(int progress);

    void runOnUiThread(Runnable runnable);

}
