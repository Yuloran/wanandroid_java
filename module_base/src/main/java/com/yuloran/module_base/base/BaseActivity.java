/*
 * Copyright (C) 2018 Yuloran(https://github.com/Yuloran)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuloran.module_base.base;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle3.components.support.RxFragmentActivity;
import com.yuloran.lib_core.bean.backend.response.SectionResp;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_repository.http.ApiProvider;
import com.yuloran.module_base.R;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * [BaseActivity]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 16:58
 *
 * @since 1.0.0
 */
public class BaseActivity extends RxFragmentActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable disposable = Single.fromCallable(new Callable<SectionResp>() {
                    @Override
                    public SectionResp call() throws Exception {
                        return ApiProvider.getInstance().getWanAndroidApi().getProjects().execute().body();
                    }
                }).subscribeOn(Schedulers.io()).subscribe(new Consumer<SectionResp>() {
                    @Override
                    public void accept(SectionResp sectionResp) {
                        Logger.debug(TAG, sectionResp.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.error(TAG, "onError.", throwable);
                    }
                });
            }
        });
    }
}
