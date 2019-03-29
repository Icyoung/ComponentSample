package com.icy.librouter;


/**
 * Created by Icy on 2017/7/24.
 */

public class RouterFactory {

    public static <T> T createRouterService(Router router, Class<T> clazz){
        return router.create(clazz);
    }
}
