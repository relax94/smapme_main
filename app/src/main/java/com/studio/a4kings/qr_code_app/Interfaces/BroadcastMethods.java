package com.studio.a4kings.qr_code_app.Interfaces;

import com.studio.a4kings.qr_code_app.Models.Member;

/**
 * Created by Dmitry Pavlenko on 04.01.2016.
 */
public interface BroadcastMethods {
    void BroadcastAuthenticationResult(boolean result, Member data);
}
