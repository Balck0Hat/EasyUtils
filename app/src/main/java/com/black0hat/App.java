package com.black0hat;

import android.app.Application;
import com.black0hat.customutils.remote.Connector;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Connector.Companion.initConfig(this);
    }
}
