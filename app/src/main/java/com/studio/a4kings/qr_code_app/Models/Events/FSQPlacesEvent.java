package com.studio.a4kings.qr_code_app.Models.Events;

import com.studio.a4kings.qr_code_app.Models.FSQResponse;

/**
 * Created by Dmitry Pavlenko on 16.03.2016.
 */
public class FSQPlacesEvent {
    private FSQResponse fsqResponse;

    public FSQPlacesEvent(FSQResponse fsqResponse) {
        this.fsqResponse = fsqResponse;
    }

    public FSQResponse getFsqResponse() {
        return fsqResponse;
    }

    public void setFsqResponse(FSQResponse fsqResponse) {
        this.fsqResponse = fsqResponse;
    }
}
