package com.hammerbyte.sahas.services.api;


public interface ServiceMaintenance extends ServiceAPI {


    void checkMaintenance();

    void onMaintenanceEnabled();

    void onMaintenanceDisabled();

    void updateStatus(String status);

    void runOnUiThread(Runnable runnable);

}
