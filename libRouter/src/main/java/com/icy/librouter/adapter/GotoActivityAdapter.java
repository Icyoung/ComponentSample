package com.icy.librouter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.icy.librouter.GotoMethod;
import com.icy.librouter.config.Config;
import com.icy.librouter.config.UriConfig;
import com.icy.librouter.util.RouterUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Icy on 2017/7/24.
 */

public class GotoActivityAdapter implements GotoAdapter<Boolean> {

    @Nullable
    @Override
    public Boolean goTo(@NonNull Config config, @NonNull GotoMethod method, @NonNull Object[] args) {
        final Context context = RouterUtils.getContextFromFirstParameter(args);
        if (context == null) {
            return false;
        }

        String uri = null;
        if (config instanceof UriConfig) {
            final UriConfig uriConfig = (UriConfig) config;
            uri = method.getUri(uriConfig.getScheme(), uriConfig.getHost(), args);
        }
        final Intent intent = new Intent();

        final Class targetClass = method.getTargetClass();
        if (targetClass != null) {
            intent.setClass(context, targetClass);
        }

        final String targetClassName = method.getTargetClassName();
        if (targetClassName != null) {
           intent.setClassName(context,targetClassName);
        }

        if (uri != null) {
            intent.setDataAndType(Uri.parse(uri), method.getMimeType());
        }
        intent.putExtras(method.getBundle(args));
        intent.setFlags(method.getTargetFlags());
        intent.setAction(method.getTargetAction());

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final Integer requestCode = method.getRequestCode();
            if (requestCode != null && context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);

            } else {
                context.startActivity(intent);
            }
            return true;
        }

        return false;
    }

}
