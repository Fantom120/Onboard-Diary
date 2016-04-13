package com.example.Onboard_diary.Social_Neetwork;

import android.content.Intent;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Ipro on 17.03.2016.
 */
public class Application extends android.app.Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "k6jxj0m6toLC8IwtSNcmvHBTE";
    private static final String TWITTER_SECRET = "yNfpLR6WORktyaR3xTDvC9WihomV6aua1KKrFjYxhPIiYWgvBm";



    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
           ;
               Intent intent = new Intent(Application.this,VkShare.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
     vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
  Log.d("log", "Application onCreate");
    }

}
