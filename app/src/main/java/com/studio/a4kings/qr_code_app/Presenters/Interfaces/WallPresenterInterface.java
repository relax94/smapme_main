package com.studio.a4kings.qr_code_app.Presenters.Interfaces;

import android.support.v7.widget.RecyclerView;

import com.studio.a4kings.qr_code_app.Models.Events.SignalEvent;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;

/**
 * Created by Dmitry Pavlenko on 05.06.2016.
 */
public interface WallPresenterInterface {
    void setRecycleView(RecyclerView recycleView);
    void addPost(Post post);
    void addPost(String text, String creatorId, int postType, String attachmentUrl);
    void getHistory();

    void uploadPostImage(String imagePath);
    void getMongoMember(String userId);
    void handleSignalMessage(SignalEvent signalEvent);
}
