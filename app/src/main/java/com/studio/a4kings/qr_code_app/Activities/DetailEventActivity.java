package com.studio.a4kings.qr_code_app.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.studio.a4kings.qr_code_app.Fragments.CommentsDialog;
import com.studio.a4kings.qr_code_app.Fragments.PhotoListFragment;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventResponseModel;
import com.studio.a4kings.qr_code_app.Models.Events.SignalEvent;
import com.studio.a4kings.qr_code_app.Models.Events.SignalSentEvent;
import com.studio.a4kings.qr_code_app.Models.ImageEventModel;
import com.studio.a4kings.qr_code_app.Models.RequestModel;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;
import com.studio.a4kings.qr_code_app.Presenters.Implementations.DetailEventPresenter;
import com.studio.a4kings.qr_code_app.Presenters.Implementations.WallPresenter;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.DetailEventView;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.WallView;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.DialogUtils;
import com.studio.a4kings.qr_code_app.Utils.Helper;
import com.studio.a4kings.qr_code_app.Utils.ImageFilePath;
import com.studio.a4kings.qr_code_app.Utils.ImageLoader;
import com.studio.a4kings.qr_code_app.Utils.ImageUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailEventActivity extends AppCompatActivity implements WallView, DetailEventView {
    PhotoListFragment photoList;

    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.content)
    LinearLayout content;

    @Bind(R.id.snapshot)
    ImageView snapshot;
    @Bind(R.id.backdrop)
    ImageView bigHeadImage;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.category)
    TextView category;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.smallImage)
    ImageView smallImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.addNewImage)
    ImageView addNewImage;
    @Bind(R.id.sendImages)
    ImageView sendImages;
    @Bind(R.id.subscribeToEvent)
    FloatingActionButton subscribeToEventFloatingBtn;
    @Bind(R.id.subscribersCount)
    TextView subscribersCount;
    @Bind(R.id.userCount)
    TextView userCount;
    @Bind(R.id.map)
    ImageView mapIcon;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.gallery)
    CardView gallery;

    DetailEventPresenter presenter;

    private WallPresenter wallPresenter;
    @Bind(R.id.postsRecycle)
    RecyclerView postsRecycleView;
    @Bind(R.id.postEditText)
    EditText postEditText;
    @Bind(R.id.postButton)
    Button postButton;
    @Bind(R.id.buttonsForAdmin)
    LinearLayout buttonsForAdmin;
    private boolean isPostImageRequest = false;
    String userCreatorId;

    @OnClick(R.id.snapshot)
    public void onSnapshotClick() {
        presenter.startMapActivity(200);
    }

    @OnClick(R.id.map)
    public void onMapIconClicked() {
        presenter.startMapActivity(100);
    }


    //private EventResponseModel event;
    ImageUtils imageUtils;
    DialogUtils dialogUtils;
    Context context;
    // private Animation rotate_forward;


    Integer eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);
        context = this;

        imageUtils = new ImageUtils(this);
        dialogUtils = new DialogUtils(this, imageUtils);
        eventId = getIntent().getIntExtra("eventId", -1);

        presenter = new DetailEventPresenter(this);

        this.wallPresenter = new WallPresenter(this, this, String.format("%s", eventId));
        this.wallPresenter.setRecycleView(this.postsRecycleView);
        EventBus.getDefault().post(new SignalSentEvent("subscribeRoom", eventId.toString()));
    }


    private final int edit = Menu.FIRST;
    private final int delete = Menu.FIRST + 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        presenter.setUpAdminMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setUpAdminMenu(Menu menu, boolean isCreator) {
        if (isCreator) {
            int groupId = 1;
            menu.add(groupId, edit, edit, "Edit").setIcon(android.R.drawable.ic_menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(groupId, delete, delete, "Delete").setIcon(android.R.drawable.ic_menu_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.share) {
            presenter.share();
        } else if (id == edit) {
            presenter.updateEvent();
        } else if (id == delete) {
            presenter.deleteEvent();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUpEvent(EventResponseModel eventModel) {
        Float rating = eventModel.getRating();
        if (rating != null) {
            //Rating
            //this.ratingBar.setRating(rating);
            TextDrawable textDrawable = Helper.getTextDrawable(rating);
            smallImage.setImageDrawable(textDrawable);
        }
        //Title
        title.setText(eventModel.getTitle() != null ? eventModel.getTitle() : "");
        //Tag
        category.setText(eventModel.getTag() != null ? eventModel.getTag() : "");

        //EventImage
        //DateCreated
        /*if(eventModel.getStartDate() != null)
            dateCreated.setText(eventModel.getStartDate());*/

        /*if(eventModel.getAddress() != null)
            address.setText(eventModel.getAddress());
*/
        description.setText(eventModel.getDescription() != null ? eventModel.getDescription() : "");
    }

    @Override
    public void setSendImagesButton(boolean enabled, int visibility) {
        sendImages.setEnabled(enabled);
        sendImages.setVisibility(visibility);
    }

    @Override
    public void setUsersCount(final ArrayList<SubscriberModel> model, final boolean isCreator) {


        userCount.setText(String.format("Users: %d", model.size()));
        userCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventUsersListActivity.class);
                intent.putExtra(context.getString(R.string.request_list), model);
                intent.putExtra(context.getString(R.string.event_id), eventId);
                intent.putExtra(context.getString(R.string.is_creator), isCreator);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setRequestsCount(final ArrayList<RequestModel> model, boolean isCreator) {
        subscribersCount.setText(String.format("Subscribers: %d", model.size()));
        if (isCreator)
            subscribersCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RequestListActivity.class);
                    intent.putExtra(context.getString(R.string.request_list), model);
                    intent.putExtra(context.getString(R.string.event_id), eventId);
                    startActivity(intent);
                }
            });
    }

    @Override
    public void setImages(ArrayList<ImageEventModel> model) {
        if (model != null && !isFinishing()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(context.getResources().getString(R.string.imageList), model);
            bundle.putInt("eventId", eventId);
            photoList = new PhotoListFragment();
            photoList.setArguments(bundle);
            if (!model.isEmpty())
                gallery.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.photo_gallery, photoList).commitAllowingStateLoss();
        }
    }

    @Override
    public void subscribe() {

        subscribeToEventFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailEventActivity.this, "Send request", Toast.LENGTH_SHORT).show();
                subscribeToEventFloatingBtn.setEnabled(false);
                presenter.subscribe();
            }
        });
    }

    @Override
    public PhotoListFragment getPhotoList() {
        return photoList;
    }

    @Override
    public Integer getEventId() {
        return eventId;
    }

    @Override
    public void setUserCreatorId(String usId) {
        userCreatorId = usId;
    }

    @Override
    public void startLoading() {
        subscribeToEventFloatingBtn.setVisibility(View.INVISIBLE);
        Helper.swapViews(progressBar, content);
    }

    @Override
    public void finishLoading() {
        subscribeToEventFloatingBtn.setVisibility(View.VISIBLE);
        Helper.swapViews(content, progressBar);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {

            if (isPostImageRequest) {
                isPostImageRequest = false;
                this.wallPresenter.uploadPostImage(ImageFilePath.getPath(this, intent.getData()));
                return;
            }
            switch (requestCode) {
                case Constants.GALLERY_REQUEST:
                    imageUtils.setmCurrentPhotoPath(ImageFilePath.getPath(this, intent.getData()));
                    imageUtils.doCrop(300, 800, 800);
                    break;
                case Constants.REQUEST_TAKE_PHOTO:
                    imageUtils.doCrop(300, 800, 800);
                    break;
                case Constants.CROP_IMAGE_REQUEST:
                    Bitmap bitmap = imageUtils.decodeUriAsBitmap();
                    String path = "file:///" + imageUtils.getmCurrentPhotoPath();
                    sendImages.setVisibility(View.VISIBLE);
                    if (gallery.getVisibility() == View.GONE)
                        gallery.setVisibility(View.VISIBLE);
                    presenter.addNewImage(path, bitmap);
                    break;
            }
        }
    }


    @OnClick(R.id.postButton)
    public void onPostButtonClicked() {
        this.wallPresenter.addPost(postEditText.getText().toString(), userCreatorId, 0, "");
    }

    @OnClick(R.id.postImageButton)
    public void onPostImageButtonClicked() {
        isPostImageRequest = true;
        dialogUtils.showDialog();
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
        //Snackbar.make(getApplication(), "Post added success", Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onMongoMemberLoaded(boolean loadResult, MongoMember mongoMember) {

    }

    @Override
    public void onUploadImageAttachment(boolean isUploaded, String url) {
        if (isUploaded) {
            this.wallPresenter.addPost(postEditText.getText().toString(), userCreatorId, 1, url);
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

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void showItemsIfAdmin() {
        ratingBar.setVisibility(View.VISIBLE);
        buttonsForAdmin.setVisibility(View.VISIBLE);
        subscribeToEventFloatingBtn.setVisibility(View.GONE);
        addNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUtils.showDialog();
            }
        });
        sendImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSendImagesButton(false, View.VISIBLE);
                presenter.sendImages();
            }
        });
    }

    @Override
    public void showIfParticipant() {
        ratingBar.setVisibility(View.VISIBLE);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                presenter.createRatingDialog(rating);
            }
        });
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showButtonAnim(Animation anim) {
        subscribeToEventFloatingBtn.startAnimation(anim);
    }


    @Override
    public void setUpHeader(String title) {
        ratingBar.setVisibility(View.GONE);
        buttonsForAdmin.setVisibility(View.GONE);
        Bitmap bitmap = getIntent().getParcelableExtra(this.getString(R.string.eventImg));
        bigHeadImage.setImageBitmap(bitmap);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(title);
        ImageLoader.loadImage(this, snapshot, AppSettings.SNAPSHOT_URL + eventId, R.drawable.map_placeholder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }


    @Subscribe
    public void onSignalMessage(SignalEvent signalEvent) {
        this.wallPresenter.handleSignalMessage(signalEvent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
