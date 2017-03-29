package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.Notification.Notification;
import com.studio.a4kings.qr_code_app.Models.WallModels.Comment;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.Models.Response.MongoMemberResponse;
import com.studio.a4kings.qr_code_app.Models.Response.SnapshotUploadResponse;
import com.studio.a4kings.qr_code_app.Models.Response.WallResponse;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Dmitry Pavlenko on 01.06.2016.
 */
public interface WallInterface {

    @POST("/wall/post/add")
    Call<WallResponse<Post>> addWallPost(@Body Post post);

    @POST("/wall/post/getposts")
    @FormUrlEncoded
    Call<WallResponse<List<Post>>> getWallPosts(@FieldMap HashMap<String,String> fieldMap /*@Body Post post*/);



    @POST("/member/add")
    Call<MongoMemberResponse> synchronizeMember(@Body MongoMember mongoMember);

    @POST("/member/get")
    @FormUrlEncoded
    Call<MongoMemberResponse> getMongoMember(@Field("member_id") String memberId);


    @Multipart
    @POST("/trip/uploadsnapshot")
    Call<SnapshotUploadResponse> uploadWallImage(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    @POST("/wall/comment/getcomments")
    @FormUrlEncoded
    Call<WallResponse<List<Comment>>> getPostComments(@Field("post_id") String postId);

    @POST("/wall/comment/add")
    Call<WallResponse<Comment>> addPostComment(@Body Comment comment);

    @POST("/notify/getall")
    @FormUrlEncoded
    Call<WallResponse<List<Notification>>> getAllNotificationsByOwner(@Field("owner_id") String ownerId);


}
