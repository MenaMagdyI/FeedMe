package mina.com.feedme.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Mena on 1/21/2018.
 */

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }


}
