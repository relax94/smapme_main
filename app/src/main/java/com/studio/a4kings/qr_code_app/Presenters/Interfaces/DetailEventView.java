package com.studio.a4kings.qr_code_app.Presenters.Interfaces;

import android.content.Context;
import android.view.Menu;
import android.view.animation.Animation;

import com.studio.a4kings.qr_code_app.Fragments.PhotoListFragment;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventResponseModel;
import com.studio.a4kings.qr_code_app.Models.ImageEventModel;
import com.studio.a4kings.qr_code_app.Models.RequestModel;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;

import java.util.ArrayList;

/**
 * Created by DUX on 08.06.2016.
 */
public interface DetailEventView {
    Context getContext();
    void showItemsIfAdmin();
    void showIfParticipant();
    void finishActivity();
    void setUpAdminMenu(Menu menu, boolean isCreator);
    void showButtonAnim(Animation anim);
    void setUpHeader(String title);
    void setUpEvent(EventResponseModel eventModel);
    void setSendImagesButton(boolean enabled, int visibility);
    void setUsersCount(ArrayList<SubscriberModel> model, boolean isCreator);
    void setRequestsCount(ArrayList<RequestModel> model, boolean isCreator);
    void setImages(ArrayList<ImageEventModel> model);
    void subscribe();
    PhotoListFragment getPhotoList();
    Integer getEventId();
    void setUserCreatorId(String usId);
    void startLoading();
    void finishLoading();
}
