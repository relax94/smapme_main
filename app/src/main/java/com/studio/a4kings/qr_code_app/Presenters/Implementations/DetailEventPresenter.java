package com.studio.a4kings.qr_code_app.Presenters.Implementations;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Activities.Map;
import com.studio.a4kings.qr_code_app.Activities.UpdateEventActivity;
import com.studio.a4kings.qr_code_app.Callbacks.ResponseCallback;
import com.studio.a4kings.qr_code_app.Callbacks.ResponseMethods;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.EventsService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventFullResponse;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventResponseModel;
import com.studio.a4kings.qr_code_app.Models.ImageEventModel;
import com.studio.a4kings.qr_code_app.Models.RequestModel;
import com.studio.a4kings.qr_code_app.Models.Response.AllRequestsResponse;
import com.studio.a4kings.qr_code_app.Models.Response.AllUsersResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ImageEventResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.DetailEventView;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUX on 08.06.2016.
 */
public class DetailEventPresenter {

    private DetailEventView view;
    EventsService eventsService;
    Context context;
    String userId;
    boolean isCreator;
    boolean isParticipant;
    boolean isSubscriber;
    private Animation rotate_forward, rotate_backward;
    AlertDialog rateDialog;

    public DetailEventPresenter(DetailEventView view) {
        this.view = view;
        init();
    }

    Integer eventId;
    EventResponseModel event;

    //початкова ініціалізація
    private void init() {
        context = view.getContext();
        //event = view.getEvent();
        eventId = view.getEventId();
        userId = PrefsManager.getInstance(context).getFromCore(PrefsParam.USER_ID);
        eventsService = new EventsService(Constants.SITE_URL);
        eventsService.setOperationToken(PrefsManager.getInstance(context).getFromCore(PrefsParam.TOKEN));


        if (eventId != -1)
            getEventInfoById(eventId);

    }

    public void getEventInfoById(final Integer eventId) {
        view.startLoading();
        eventsService.getEventById(eventId, new ResponseCallback<EventFullResponse>(new ResponseMethods() {
            @Override
            public void onSuccess(Response<? extends MainResponse> response) {
                EventFullResponse response1 = (EventFullResponse) response.body();
                event = response1.getModel();
                view.setUserCreatorId(event.getUserCreatorId());
                view.finishLoading();
                loadForm();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        }));
    }

    private void loadForm() {
        rotate_forward = AnimationUtils.loadAnimation(context, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(context, R.anim.rotate_backward);
        isCreator = userId.equals(event.getUserCreatorId());
        if (event.getIsParticipant() != null) {
            isParticipant = event.getIsParticipant();
        }
        if (event.getIsSubscriber() != null) {
            isSubscriber = event.getIsSubscriber();
        }
        if (isParticipant) {
            view.showIfParticipant();
        }
        getAllRequestsForEvent();
        getAllUsersForEvent();
        subscribeToEventBtnClick();
        getAllImagesForEventById();
        view.setUpHeader(event.getTitle());
        view.setUpEvent(event);
        if (!isCreator) {
            if (isParticipant || isSubscriber) {
                view.showButtonAnim(rotate_forward);
            }
        } else {
            view.showItemsIfAdmin();
        }
    }

    //збереження картинок на сервері
    public void sendImages() {
        ArrayList<Bitmap> bitmaps = view.getPhotoList().getAddedPhotos();
        eventsService.addImagesToEvent(event.getId(), bitmaps, new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                view.setSendImagesButton(true, View.GONE);
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                view.setSendImagesButton(true, View.VISIBLE);
            }
        });
    }

    //створення спливаючого вікна для підтвердження встановлення рейтингу
    public void createRatingDialog(final Float rating) {
        rateDialog = new AlertDialog.Builder(context)
                .setTitle("Your vote for this event = " + rating)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eventsService.sendRating(event.getId(), rating, new ResponseCallback<>(new ResponseMethods() {
                            @Override
                            public void onSuccess(Response<? extends MainResponse> response) {
                                Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }
                })
                .create();
        rateDialog.show();
    }

    //метод старту карти, в залежності від ролі різний requestCode
    public void startMapActivity(int requestCode) {
        Intent intent = new Intent(context, Map.class);
        intent.putExtra("ownerId", event.getUserCreatorId());
        intent.putExtra("eventId", event.getId() + "");
        intent.putExtra("requestCode", requestCode);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    //запит, щоб отримати всіх користувачів події
    private void getAllUsersForEvent() {
        eventsService.getAllUsersForEvent(event.getId(), new Callback<AllUsersResponse>() {
            @Override
            public void onResponse(Call<AllUsersResponse> call, Response<AllUsersResponse> response) {
                final ArrayList<SubscriberModel> model = response.body().getModel();
                if (model != null && !model.isEmpty()) {
                    view.setUsersCount(model, isCreator);
                }
            }

            @Override
            public void onFailure(Call<AllUsersResponse> call, Throwable t) {

            }
        });
    }

    ResponseCallback<MainResponse> requestResp = new ResponseCallback<>(new ResponseMethods() {
        @Override
        public void onSuccess(Response<? extends MainResponse> response) {
            Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
            if (isParticipant)
                isParticipant = !isParticipant;
            else
                isSubscriber = !isSubscriber;
            showAnimationForFloatBtn();
        }
    });

    //метод зміни анімації кнопки в залежності від ролі користувача
    private void showAnimationForFloatBtn() {
        if (isParticipant || isSubscriber) {
            view.showButtonAnim(rotate_forward);
        } else {
            view.showButtonAnim(rotate_backward);
        }
    }

    private void subscribeToEventBtnClick() {
        if (!isCreator) {
            view.subscribe();
        }
    }

    //метод, щоб підписатись або відписатись від івенту
    public void subscribe() {
        if (isParticipant)
            eventsService.removeUserFromEvent(event.getId(), requestResp);
        else if (!isSubscriber)
            eventsService.addRequest(event.getId(), userId, requestResp);
        else eventsService.removeRequest(event.getId(), requestResp);
    }

    //запит на отримання всіх підписників події
    private void getAllRequestsForEvent() {
        eventsService.getAllRequestsForEvent(event.getId(), new Callback<AllRequestsResponse>() {
            @Override
            public void onResponse(Call<AllRequestsResponse> call, Response<AllRequestsResponse> response) {
                if (response.body() != null) {
                    if (response.body().getCode() != null && response.body().getCode() == 200) {
                        final ArrayList<RequestModel> model = response.body().getModel();
                        if (model != null) {
                            if (!model.isEmpty()) {
                                view.setRequestsCount(model, isCreator);

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AllRequestsResponse> call, Throwable t) {

            }
        });
    }

    //запит на отримання всіх картинок події
    private void getAllImagesForEventById() {
        eventsService.getAllImagesForEventById(event.getId(), new Callback<ImageEventResponse>() {
            @Override
            public void onResponse(Call<ImageEventResponse> call, Response<ImageEventResponse> response) {
                if (response.body() != null) {
                    ArrayList<ImageEventModel> model = response.body().getModel();
                    view.setImages(model);
                }
            }

            @Override
            public void onFailure(Call<ImageEventResponse> call, Throwable t) {

            }
        });
    }

    //метод видалення події
    public void deleteEvent() {
        eventsService.deleteEvent(event.getId(), new ResponseCallback<>(new ResponseMethods() {
            @Override
            public void onSuccess(Response<? extends MainResponse> response) {
                view.finishActivity();
            }
        }));
    }

    //метод для длдавання нового зображення
    public void addNewImage(String path, Bitmap bitmap) {
        view.getPhotoList().addNewImage(path, bitmap);
    }

    //метод для відписування від усіх посилань, щоб зменшити витоки пам’яті
    public void destroy() {
        context = null;
    }

    //метод, для створення додаткових пунктів меню, якщо зайшов адмін
    public void setUpAdminMenu(Menu menu) {
        view.setUpAdminMenu(menu, isCreator);
    }

    //метод для того, щоб можна було ділитись подією в різних соц.мережах
    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "Share"));
    }

    //метод для оновлення події
    public void updateEvent() {
        Intent transitionIntent = new Intent(context, UpdateEventActivity.class);
        transitionIntent.putExtra(context.getString(R.string.event), event);
        context.startActivity(transitionIntent);
    }
}
