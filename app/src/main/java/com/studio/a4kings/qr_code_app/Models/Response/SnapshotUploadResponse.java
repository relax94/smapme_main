package com.studio.a4kings.qr_code_app.Models.Response;

/**
 * Created by Dmitry Pavlenko on 28.05.2016.
 */
public class SnapshotUploadResponse {
    private boolean state;
    private String url;

    public boolean isState() {
        return state;
    }

    public String getUrl() {
        return url;
    }
}
