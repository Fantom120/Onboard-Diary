package com.example.Onboard_diary.Social_Neetwork;


import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import com.vk.sdk.api.VKError;

import com.vk.sdk.dialogs.VKShareDialogBuilder;


public class VkShare  extends AppCompatActivity {
   private final  String[] scope = new String[]{VKScope.WALL,VKScope.GROUPS};
   private String share;
    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    if(getIntent() != null){
        share = getIntent().getStringExtra("share");
    }

        VKSdk.login(this, sMyScope);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d("res","User passed Authorization" );
                // User passed Authorization
                new VKShareDialogBuilder()
                        .setText(share)
                        .show(getSupportFragmentManager(), "VK_SHARE_DIALOG");
            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
                Log.d("res", "User didn't pass Authorization");
            }
        };

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.d("res", "User didn't pass Authorization");
        }
    }


}

