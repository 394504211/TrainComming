package service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;


/**
 * Created by Administrator on 2017/1/25.
 */
public class MainService extends Service{

    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
