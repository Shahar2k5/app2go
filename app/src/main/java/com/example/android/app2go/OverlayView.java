package com.example.android.app2go;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by baryariv1 on 03/03/2016.
 */
public class OverlayView extends RelativeLayout {
    private RelativeLayout rootView;


    public OverlayView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context ctx) {
        rootView = (RelativeLayout) inflate(ctx, R.layout.overlay, this);
    }
}
