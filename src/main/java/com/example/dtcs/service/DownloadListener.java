package com.example.dtcs.service;

/**
 * Created by q9163 on 26/01/2018.
 */

public interface DownloadListener {
    void onProgress(int progress );
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
