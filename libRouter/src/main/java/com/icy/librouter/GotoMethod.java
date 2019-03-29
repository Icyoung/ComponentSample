package com.icy.librouter;

import android.os.Bundle;
import android.text.TextUtils;

import com.icy.librouter.util.RouterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Icy on 2017/7/24.
 */

public class GotoMethod {
    private String mimeType;
    private Class targetClass;
    private String targetClassName;
    private int targetFlags;
    private String targetAction;
    private Integer requestCode;

    private ArrayList<Object> pathSegements = new ArrayList<>(); // [segementString, position, ...]
    private HashMap<String, Integer> queryPositions = new HashMap<>(); // {key: position, ...}
    private ArrayList<Integer> queryMapPositions = new ArrayList<>(); // [position, position, ...]
    private HashMap<String, Integer> bundlePositions = new HashMap<>();



    @Nullable
    public String getUri(@Nullable String scheme, @Nullable String host, @NonNull Object[] args) {
        if (TextUtils.isEmpty(scheme)) {
            return null;
        }
        final String hostUri = scheme + "://" + (TextUtils.isEmpty(host) ? "" : host + "/");
        final String path = getPath(args);
        final String queryString = getQueryString(args);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return hostUri + path + queryString;
    }

    @Nullable
    public String getMimeType() {
        return mimeType;
    }

    @Nullable
    public Class getTargetClass() {
        return targetClass;
    }

    @Nullable
    public String getTargetClassName() {
        return targetClassName;
    }

    public int getTargetFlags() {
        return targetFlags;
    }

    @Nullable
    public String getTargetAction() {
        return targetAction;
    }

    @Nullable
    public Integer getRequestCode() {
        return requestCode;
    }

    public void setTargetClass(@Nullable Class targetClass) {
        this.targetClass = targetClass;
    }

    public void setTargetClassName(@Nullable String targetClassName){
        this.targetClassName = targetClassName;
    }

    public void setMimeType(@Nullable String mimeType) {
        this.mimeType = mimeType;
    }

    public void setTargetFlags(int targetFlags) {
        this.targetFlags = targetFlags;
    }

    public void setTargetAction(@Nullable String targetAction) {
        this.targetAction = targetAction;
    }

    public void setRequestCode(@Nullable Integer requestCode) {
        this.requestCode = requestCode;
    }

    public void addPathSegement(@NonNull Object pathSegements) {
        this.pathSegements.add(pathSegements);
    }

    public void addQueryPositions(@NonNull String key, @NonNull Integer queryPosition) {
        this.queryPositions.put(key, queryPosition);
    }

    public void addQueryMapPositions(@NonNull Integer queryMapPosition) {
        this.queryMapPositions.add(queryMapPosition);
    }

    public void addBundlePositions(@NonNull String key, @NonNull Integer bundlePosition) {
        this.bundlePositions.put(key, bundlePosition);
    }

    @NonNull
    private String getPath(@NonNull Object[] args) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Object segment : pathSegements) {
            if (segment instanceof String) {
                stringBuilder.append((String) segment);
            } else if (segment instanceof Integer) {
                stringBuilder.append((String) args[(int) segment]);
            }
        }

        return stringBuilder.toString();
    }

    @NonNull
    private String getQueryString(@NonNull Object[] args) {
        final StringBuilder stringBuilder = new StringBuilder("?");

        int count = 0;
        for (Map.Entry<String, Integer> entry : queryPositions.entrySet()) {
            final String key = entry.getKey();
            final String value = String.valueOf(args[entry.getValue()]);

            if (count++ == 0) {
                stringBuilder.append(key).append("=").append(value);
            } else {
                stringBuilder.append("&").append(key).append("=").append(value);
            }
        }

        for (Integer position : queryMapPositions) {
            final Map<String, ?> queryMap = (Map<String, ?>) args[position];

            for (Map.Entry<String, ?> entry : queryMap.entrySet()) {
                final String key = entry.getKey();
                final String value = String.valueOf(entry.getValue());

                if (count++ == 0) {
                    stringBuilder.append(key).append("=").append(value);
                } else {
                    stringBuilder.append("&").append(key).append("=").append(value);
                }
            }
        }

        return count == 0 ? "" : stringBuilder.toString();
    }

    @NonNull
    public Bundle getBundle(@NonNull Object[] args) {
        final Bundle bundle = new Bundle();

        for (Map.Entry<String, Integer> entry : bundlePositions.entrySet()) {
            final String key = entry.getKey();
            final Object value = args[entry.getValue()];

            RouterUtils.putValueToBundle(bundle, key, value);
        }

        return bundle;
    }
}
