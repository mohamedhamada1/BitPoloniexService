/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package sa.bitoasis.bitoasis.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sa.bitoasis.bitoasis.repository.local.db.AppDatabase;
import sa.bitoasis.bitoasis.repository.local.db.dao.UserDao;
import sa.bitoasis.bitoasis.repository.local.prefs.IPreferenceHelper;
import sa.bitoasis.bitoasis.repository.local.prefs.PreferenceHelperImpl;
import sa.bitoasis.bitoasis.utils.AppConstant;


import static sa.bitoasis.bitoasis.utils.AppConstant.DB_NAME;

@Module
 class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    UserDao providerUserDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstant.PREF_NAME;
    }

    @Provides
    @Singleton
    SharedPreferences providerSharedPreference(Context context, @PreferenceInfo String prefFileName) {
        return context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Provides
    @Singleton
    IPreferenceHelper providePreferencesHelper(SharedPreferences mPref) {
        return new PreferenceHelperImpl(mPref);
    }

}
