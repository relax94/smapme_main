package com.studio.a4kings.qr_code_app.Dagger;

import com.studio.a4kings.qr_code_app.Managers.DbManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dmitry Pavlenko on 12.01.2016.
 */
/*@Singleton
@Component(modules = {DbManager.class})*/
public interface DbManagerComponent {
    DbManager provideDbManager();
}
