package com.studio.a4kings.qr_code_app.Presenters.Interfaces;

import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;

/**
 * Created by Dmitry Pavlenko on 05.06.2016.
 */
public interface WallView {
    void initWall();
    void addPostResult(boolean postResult, Post post);
    void onMongoMemberLoaded(boolean loadResult, MongoMember mongoMember);
    void onUploadImageAttachment(boolean isUploaded, String url);
    void showCommentDialog(MongoMember mongoMember, Post post);
   /* void onHistoryLoaded(boolean isLoaded, List<Post> history);*/
}
