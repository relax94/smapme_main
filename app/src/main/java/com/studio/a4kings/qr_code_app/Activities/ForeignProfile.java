package com.studio.a4kings.qr_code_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.a4kings.qr_code_app.Callbacks.ResponseCallback;
import com.studio.a4kings.qr_code_app.Callbacks.ResponseMethods;
import com.studio.a4kings.qr_code_app.Fragments.CommentsDialog;
import com.studio.a4kings.qr_code_app.Fragments.PhotoListFragment;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.SubscribersService;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.UserProfileService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.ImageSizes;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.ImageEventModel;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.MemberProfile;
import com.studio.a4kings.qr_code_app.Models.Response.AllUsersResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileImagesResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileResponse;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;
import com.studio.a4kings.qr_code_app.Presenters.Implementations.WallPresenter;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.WallView;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.Helper;
import com.studio.a4kings.qr_code_app.Utils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

/**
 * Created by DUX on 08.02.2016.
 */
public class ForeignProfile extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, WallView {

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    SubscribersService subscribersService;


    @Bind(R.id.main_linearlayout_title)
    LinearLayout mTitleContainer;
    @Bind(R.id.main_textview_title)
    TextView mTitle;
    @Bind(R.id.main_appbar)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.main_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.subscribers)
    LinearLayout subscribersLayout;
    @Bind(R.id.subscribersCount)
    TextView subscribersCountTextView;

    Context mContext;
    @Bind(R.id.profileImg)
    CircleImageView profilePhoto;
    MemberProfile member;
    @Bind(R.id.signature)
    TextView signature;
    @Bind(R.id.coverImg)
    ImageView wallPaper;
    @Bind(R.id.addToFriends)
    Button addToFriends;
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

    @Bind(R.id.postsRecycle)
    RecyclerView postsRecycleView;

    @Bind(R.id.postEditText)
    EditText postEditText;
    private WallPresenter wallPresenter;

    private void setSignature(String signature) {
        mTitle.setText(signature);
        this.signature.setText(signature);
    }

    UserProfileService userProfileService;

    String userId;
    PhotoListFragment photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_profile);
        ButterKnife.bind(this);
        mContext = this;

        subscribersService = new SubscribersService(Constants.SITE_URL);
        subscribersService.setOperationToken(PrefsManager.getInstance(mContext).getFromCore(PrefsParam.TOKEN));

        userProfileService = new UserProfileService(Constants.SITE_URL);
        userProfileService.setOperationToken(PrefsManager.getInstance(mContext).getFromCore(PrefsParam.TOKEN));

        userId = getIntent().getStringExtra(getString(R.string.userId));
        if (userId != null) {
            userProfileService.getProfileAsyncById(userId, new ResponseCallback<ProfileResponse>(new ResponseMethods() {
                @Override
                public void onSuccess(Response<? extends MainResponse> response) {
                    ProfileResponse response1 = (ProfileResponse) response.body();
                    member = response1.getProfile();
                    init();
                }
            }));

            subscribersService.getAllSubscribersById(userId, new ResponseCallback<AllUsersResponse>(new ResponseMethods() {
                @Override
                public void onSuccess(Response<? extends MainResponse> response) {
                    AllUsersResponse response1 = (AllUsersResponse) response.body();
                    final ArrayList<SubscriberModel> list = response1.getModel();
                    subscribersCountTextView.setText(String.valueOf(list.size()));
                    subscribersLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (member != null) {
                                Intent intent = new Intent(mContext, FriendsOrSubscribersListActivity.class);
                                intent.putExtra("id", member.getUserId());
                                intent.putExtra("list", list);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }));

            userProfileService.getImages(userId, new ResponseCallback<ProfileImagesResponse>(new ResponseMethods() {
                @Override
                public void onSuccess(Response<? extends MainResponse> response) {
                    ProfileImagesResponse response1 = (ProfileImagesResponse) response.body();
                    ArrayList<String> model = response1.getModel();
                    if (model != null && !model.isEmpty()) {
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
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.photo_gallery, photoList).commitAllowingStateLoss();
                        photos.setText(String.valueOf(model.size()));
                        photosBlock.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getBaseContext(), GalleryActivity.class);
                                intent.putExtra("images", models);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }));

            Bitmap bitmap = getIntent().getParcelableExtra("profileImg");
            if (bitmap != null)
                profilePhoto.setImageBitmap(bitmap);
            mToolbar.setVisibility(View.INVISIBLE);
            mToolbar.setTitle("");
            mAppBarLayout.addOnOffsetChangedListener(this);
            setSupportActionBar(mToolbar);
            Helper.startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        }
    }

    public void init() {
        setSignature(member.getSignature());
        ImageLoader.loadImage(mContext, wallPaper, Constants.PATH_TO_USER_IMAGES + Constants.IMAGE_SIZES.get(ImageSizes.BIG) + member.getWallpaper());
        ImageLoader.loadImage(mContext, profilePhoto, Constants.PATH_TO_USER_IMAGES + Constants.IMAGE_SIZES.get(ImageSizes.BIG) + member.getPhoto());
        wallPresenter = new WallPresenter(this, this, String.format("%s", member.getUserId()));
        wallPresenter.setRecycleView(postsRecycleView);
        viewMemberInfo(member);
    }

    private void viewMemberInfo(MemberProfile profile) {
        if (profile.getBirthday() != null)
            birthday.setText(profile.getBirthday());
        if (profile.getGender() != null) {
            String gender = profile.getGender();
            gender = gender.equals("0") ? "Not specified" : (gender.equals("1") ? "Male" : "Female");
            this.gender.setText(gender);
        }
        if (profile.getAboutMe() != null)
            aboutMe.setText(profile.getAboutMe());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
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
                Helper.startAlphaAnimation(floatingActionButton, Constants.ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                floatingActionButton.setEnabled(false);
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                Helper.startAlphaAnimation(mTitleContainer, Constants.ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
                Helper.startAlphaAnimation(floatingActionButton, Constants.ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                floatingActionButton.setEnabled(true);
            }
        }
    }

    @OnClick(R.id.postButton)
    public void onPostButtonClicked() {
        this.wallPresenter.addPost(postEditText.getText().toString(), "", 0, "");
    }

    @Override
    public void initWall() {
        String userId = PrefsManager.getInstance(this).get(PrefsParam.USER_ID);
        if (!userId.isEmpty()) {
            this.wallPresenter.getMongoMember(userId);
            this.wallPresenter.getHistory();
        }
    }

    @Override
    public void addPostResult(boolean postResult, Post post) {

    }

    @Override
    public void onMongoMemberLoaded(boolean loadResult, MongoMember mongoMember) {

    }

    @Override
    public void onUploadImageAttachment(boolean isUploaded, String url) {
        if (isUploaded) {
            this.wallPresenter.addPost(postEditText.getText().toString(), "", 1, url);
        }
    }

    @Override
    public void showCommentDialog(MongoMember mongoMember, Post post) {
        if (mongoMember != null && post != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("mongoMember", mongoMember);
            bundle.putSerializable("post", post);
            // bundle.putInt("position", );

            //FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            CommentsDialog newFragment = CommentsDialog.newInstance();
            newFragment.setArguments(bundle);
            //  newFragment.show(ft, "slideshow");

    /*    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, newFragment).commit();
*/
            FragmentManager fm = getSupportFragmentManager();
            //MyDialogFragment myDialogFragment = new MyDialogFragment();
            newFragment.show(fm, "dialog_fragment");
        }
    }

    @OnClick(R.id.fab)
    public void openMessenger() {
        Member member = PrefsManager.getInstance(this).getMember();
        String user_id = PrefsManager.getInstance(this).get(PrefsParam.USER_ID);
        if (member != null && !user_id.isEmpty()) {
            this.openApp(this, "com.a4kings.dmitrypavlenko.smapme_mess", 1, user_id);
        }
    }

    public boolean openApp(Context context, String packageName, int runMode, String userId) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            i.putExtra("runMode", runMode);
            i.putExtra("user_id", userId);
            if (i == null) {
                return false;
                //throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
