package com.icy.librouter;

import com.icy.librouter.adapter.GotoActivityAdapter;
import com.icy.librouter.adapter.GotoAdapter;
import com.icy.librouter.config.Config;
import com.icy.librouter.config.UriConfig;
import com.icy.librouter.parser.DefaultParser;
import com.icy.librouter.parser.Parser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Icy on 2017/7/24.
 */

public class Router {
    private Config config;
    private Parser parser;
    private GotoAdapter gotoAdapter;

    private Router(@NonNull Builder builder) {
        this.config = builder.config;
        this.parser = builder.parser;
        this.gotoAdapter = builder.gotoAdapter;
    }


    @NonNull
    public <T> T create(@NonNull Class<T> routerClass) {
        return (T) Proxy.newProxyInstance(routerClass.getClassLoader(), new Class[]{routerClass}, new RouterInvocationHandler());
    }

    private class RouterInvocationHandler implements InvocationHandler {
        private final Map<Method, GotoMethod> methodCache = new ConcurrentHashMap<>();


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return gotoAdapter.goTo(config, loadGoto(method), args);
        }

        private GotoMethod loadGoto(Method method) {
            GotoMethod result = methodCache.get(method);
            if (result != null) return result;

            synchronized (methodCache) {
                result = parser.parseMethod(config,method);
                methodCache.put(method, result);
            }
            return result;
        }
    }
    public static class Builder {
        private Config config;
        private Parser parser;
        private GotoAdapter gotoAdapter;

        @NonNull
        public Builder config(@NonNull Config config) {
            this.config = config;
            return this;
        }

        @NonNull
        public Builder parser(@Nullable Parser parser) {
            this.parser = parser;
            return this;
        }

        @NonNull
        public Builder adapter(@Nullable GotoAdapter gotoAdapter) {
            this.gotoAdapter = gotoAdapter;
            return this;
        }

        @NonNull
        public Router build() {
            if (config == null) config(new UriConfig());
            if (parser == null) parser(new DefaultParser());
            if (gotoAdapter == null) adapter(new GotoActivityAdapter());
            return new Router(this);
        }
    }

}
