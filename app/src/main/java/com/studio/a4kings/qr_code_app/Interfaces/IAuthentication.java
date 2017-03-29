package com.studio.a4kings.qr_code_app.Interfaces;

import com.studio.a4kings.qr_code_app.Models.Member;

/**
 * Created by Dmitry Pavlenko on 26.12.2015.
 */
public interface IAuthentication {
    void authenticationResult(boolean isAuthenticate, Member member);
}
