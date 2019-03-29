package com.icy.librouter.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.icy.librouter.GotoMethod;
import com.icy.librouter.config.Config;


/**
 * Created by Icy on 2017/7/24.
 */

public interface GotoAdapter<T> {
    @Nullable
    T goTo(@NonNull Config config, @NonNull GotoMethod method, @NonNull Object[] args);
}
