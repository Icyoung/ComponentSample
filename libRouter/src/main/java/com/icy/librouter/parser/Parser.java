package com.icy.librouter.parser;

import androidx.annotation.NonNull;

import com.icy.librouter.GotoMethod;
import com.icy.librouter.config.Config;

import java.lang.reflect.Method;

/**
 * Created by Icy on 2017/7/24.
 */

public interface Parser {
    @NonNull
    GotoMethod parseMethod(@NonNull Config config, @NonNull Method method);
}
