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

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import sa.bitoasis.bitoasis.views.login.LoginActivity;
import sa.bitoasis.bitoasis.views.login.LoginActivityModule;
import sa.bitoasis.bitoasis.views.main.TickerActivity;
import sa.bitoasis.bitoasis.views.main.TickerActivityModule;
import sa.bitoasis.bitoasis.views.signup.SignupActivity;
import sa.bitoasis.bitoasis.views.signup.SignupActivityModule;
import sa.bitoasis.bitoasis.views.splash.SplashActivity;
import sa.bitoasis.bitoasis.views.splash.SplashActivityModule;


@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {SplashActivityModule.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {LoginActivityModule.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {SignupActivityModule.class})
    abstract SignupActivity bindSignupActivity();

    @ContributesAndroidInjector(modules = {TickerActivityModule.class})
    abstract TickerActivity bindTickerActivity();

}
