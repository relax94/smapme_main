package com.studio.a4kings.qr_code_app.Models.Response;

import com.studio.a4kings.qr_code_app.Models.Member;

/**
 * Created by DUX on 26.02.2016.
 */
public class UserResponse extends MainResponse {
    Member user;

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }
}
