package com.studio.a4kings.qr_code_app.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Activities.AddEventActivity;
import com.studio.a4kings.qr_code_app.Activities.FriendsOrSubscribersListActivity;
import com.studio.a4kings.qr_code_app.Activities.GalleryActivity;
import com.studio.a4kings.qr_code_app.Activities.MainActivity;
import com.studio.a4kings.qr_code_app.Callbacks.ResponseCallback;
import com.studio.a4kings.qr_code_app.Callbacks.ResponseMethods;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.SubscribersService;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.UserProfileService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.ImageSizes;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Enums.UserEnum;
import com.studio.a4kings.qr_code_app.Models.ImageEventModel;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.MemberProfile;
import com.studio.a4kings.qr_code_app.Models.Response.AllUsersResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.Response.PhotoResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileImagesResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileResponse;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.DialogUtils;
import com.studio.a4kings.qr_code_app.Utils.Helper;
import com.studio.a4kings.qr_code_app.Utils.ImageFilePath;
import com.studio.a4kings.qr_code_app.Utils.ImageLoader;
import com.studio.a4kings.qr_code_app.Utils.ImageUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelfProfile extends Fragment implements AppBarLayout.OnOffsetChangedListener, Callback<PhotoResponse> {

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    @Bind(R.id.main_linearlayout_title)
    LinearLayout mTitleContainer;
    @Bind(R.id.main_textview_title)
    TextView mTitle;
    @Bind(R.id.main_appbar)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.main_toolbar)
    Toolbar mToolbar;
    Context mContext;
    @Bind(R.id.profileImg)
    CircleImageView profilePhoto;
    Member member;
    @Bind(R.id.signature)
    TextView signature;
    @Bind(R.id.main_imageview_placeholder)
    ImageView wallPaper;
    @Bind(R.id.addEvent)
    Button addEvent;
    @Bind(R.id.subscribersCount)
    TextView subscribersCount;
    @Bind(R.id.subscribers)
    LinearLayout subscribers;
    @Bind(R.id.birthday)
    TextView birthday;
    @Bind(R.id.gender)
    TextView gender;
    @Bind(R.id.aboutMe)
    TextView aboutMe;
    @Bind(R.id.photosBlock)
    LinearLayout photosBlock;
    @Bind(R.id.photos)
    TextView photos;

    PhotoListFragment photoList;

    @OnClick(R.id.eventsBlock)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(mContext.getString(R.string.sub_enum), UserEnum.MY);
        ((MainActivity) getActivity()).changeFragment(new EventsListFragment(), bundle);
    }

    private UserProfileService userProfileService;
    private SubscribersService subscribersService;
    DialogUtils dialogUtils;
    ImageUtils imageUtils;

    @OnClick(R.id.main_imageview_placeholder)
    public void onCoverImageClick() {
        dialogUtils.showDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_self_profile, container, false);
        ButterKnife.bind(this, v);
        mContext = this.getContext();

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        userProfileService = new UserProfileService(Constants.SITE_URL);
        subscribersService = new SubscribersService(Constants.SITE_URL);
        String token = PrefsManager.getInstance(mContext).getFromCore(PrefsParam.TOKEN);
        subscribersService.setOperationToken(token);
        userProfileService.setOperationToken(token);

        userProfileService.getProfileAsync(profileResponseCallback);

        userProfileService.getImages(new ResponseCallback<ProfileImagesResponse>(new ResponseMethods() {
            @Override
            public void onSuccess(Response<? extends MainResponse> response) {
                ProfileImagesResponse response1 = (ProfileImagesResponse) response.body();
                ArrayList<String> model = response1.getModel();
                if(model != null && !model.isEmpty()) {
                    final ArrayList<ImageEventModel> models = new ArrayList<>();
                    for (String s : model) {
                        ImageEventModel imageEventModel = new ImageEventModel();
                        imageEventModel.setPhotoName(s);
                        models.add(imageEventModel);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(mContext.getResources().getString(R.string.imageList), models);
                    photoList = new PhotoListFragment();
                    photoList.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.photo_gallery, photoList).commitAllowingStateLoss();
                    photos.setText(String.valueOf(model.size()));
                    photosBlock.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), GalleryActivity.class);
                            intent.putExtra("images", models);
                            startActivity(intent);
                        }
                    });
                }
            }
        }));

        subscribersService.getAllUserFriends(new ResponseCallback<AllUsersResponse>(new ResponseMethods() {
            @Override
            public void onSuccess(Response<? extends MainResponse> response) {
                AllUsersResponse response1 = (AllUsersResponse) response.body();
                final ArrayList<SubscriberModel> model = response1.getModel();
                if (model != null && !model.isEmpty()) {
                    subscribersCount.setText(String.valueOf(model.size()));
                    subscribers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, FriendsOrSubscribersListActivity.class);
                            intent.putExtra("list", model);
                            startActivity(intent);
                        }
                    });
                }
            }
        }));


        member = PrefsManager.getInstance(mContext).getMember();
        if (member != null)
            viewMember(member.getProfile());

        mToolbar.setTitle("");
        mToolbar.setVisibility(View.INVISIBLE);
        mAppBarLayout.addOnOffsetChangedListener(this);

        Helper.startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        //TODO: add loader
        Toast.makeText(mContext, "add loader", Toast.LENGTH_LONG).show();

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddEventActivity.class));
            }
        });

        imageUtils = new ImageUtils(mContext, this);
        dialogUtils = new DialogUtils(mContext, imageUtils);
    }

    private void viewMember(MemberProfile profile) {
        if (profile.getBirthday() != null)
            birthday.setText(profile.getBirthday());
        if (profile.getGender() != null) {
            String gender = profile.getGender();
            gender = gender.equals("0") ? "Not specified" : (gender.equals("1") ? "Male" : "Female");
            this.gender.setText(gender);
        }
        if (profile.getAboutMe() != null)
            aboutMe.setText(profile.getAboutMe());
        setSignature(profile);
        updateUserPhoto(profile);
        updateUserWallpaper(profile);
    }

    private void setSignature(MemberProfile profile) {
        signature.setText(profile.getSignature());
    }

    private void updateUserWallpaper(MemberProfile profile) {
        if (profile.getWallpaper() != null)
            ImageLoader.loadImage(mContext,
                    wallPaper,
                    Constants.PATH_TO_USER_IMAGES + Constants.IMAGE_SIZES.get(ImageSizes.BIG) + profile.getWallpaper()
                    , null);
    }

    private void updateUserPhoto(MemberProfile profile) {
        if (profile.getPhoto() != null)
            ImageLoader.loadImage(mContext,
                    profilePhoto,
                    Constants.PATH_TO_USER_IMAGES + Constants.IMAGE_SIZES.get(ImageSizes.MEDIUM) + profile.getPhoto());
    }

    Callback<ProfileResponse> profileResponseCallback = new Callback<ProfileResponse>() {
        @Override
        public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
            if (response.body() != null) {
                member = new Member();
                member.setProfile(response.body().getProfile());
                viewMember(member.getProfile());
                Toast.makeText(mContext, "kill loader", Toast.LENGTH_LONG).show();
                //TODO: kill loader
            }
        }

        @Override
        public void onFailure(Call<ProfileResponse> call, Throwable t) {

        }
    };

    private void saveWallPaper(Bitmap bitmap) {
        this.userProfileService.updateWallPaperAsync(bitmap, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.GALLERY_REQUEST:
                    imageUtils.setmCurrentPhotoPath(ImageFilePath.getPath(mContext, intent.getData()));
                    imageUtils.doCrop(wallPaper, wallPaper.getWidth(), wallPaper.getHeight());
                    break;
                case Constants.REQUEST_TAKE_PHOTO:
                    imageUtils.doCrop(wallPaper, wallPaper.getWidth(), wallPaper.getHeight());
                    break;
                case Constants.CROP_IMAGE_REQUEST:
                    Bitmap bitmap = imageUtils.decodeUriAsBitmap();
                    //wallPaper.setImageBitmap(bitmap);
                    saveWallPaper(bitmap);
                    break;
            }
        }
    }

    @Override
    public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
        if (response.body() != null) {
            if (response.body().getCode() == 200) {
                String fileName = response.body().getFileName();
                switch (response.body().getType()) {
                    case 1://photo
                        member.getProfile().setPhoto(fileName);
                        updateUserPhoto(member.getProfile());
                        break;
                    case 2://wallPapers
                        member.getProfile().setWallpaper(fileName);
                        updateUserWallpaper(member.getProfile());
                        break;
                }
                //TODO: kill loader
            }
        }
    }

    @Override
    public void onFailure(Call<PhotoResponse> call, Throwable t) {
        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= Constants.PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                Helper.startAlphaAnimation(mTitle, Constants.ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
                mToolbar.setVisibility(View.VISIBLE);
            }
        } else {
            if (mIsTheTitleVisible) {
                Helper.startAlphaAnimation(mTitle, Constants.ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
                mToolbar.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= Constants.PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                Helper.startAlphaAnimation(mTitleContainer, Constants.ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                Helper.startAlphaAnimation(mTitleContainer, Constants.ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
