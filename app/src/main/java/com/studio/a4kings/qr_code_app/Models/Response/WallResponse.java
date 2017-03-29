package com.studio.a4kings.qr_code_app.Models.Response;

/**
 * Created by Dmitry Pavlenko on 01.06.2016.
 */
public class WallResponse<T> {
    private boolean state;
    private T r;

    public boolean isState() {
        return state;
    }

    public T getR() {
        return r;
    }
}
