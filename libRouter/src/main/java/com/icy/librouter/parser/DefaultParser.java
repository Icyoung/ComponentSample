package com.icy.librouter.parser;


import androidx.annotation.NonNull;

import com.icy.librouter.GotoMethod;
import com.icy.librouter.annotation.Bundle;
import com.icy.librouter.annotation.Path;
import com.icy.librouter.annotation.Query;
import com.icy.librouter.annotation.QueryMap;
import com.icy.librouter.annotation.RequestCode;
import com.icy.librouter.annotation.TargetAction;
import com.icy.librouter.annotation.TargetClass;
import com.icy.librouter.annotation.TargetClassName;
import com.icy.librouter.annotation.TargetFlags;
import com.icy.librouter.annotation.TargetPath;
import com.icy.librouter.config.Config;
import com.icy.librouter.util.RouterUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by Icy on 2017/7/24.
 */

public class DefaultParser implements Parser {

    @NonNull
    @Override
    public GotoMethod parseMethod(@NonNull Config config, @NonNull Method method) {
        final Annotation[] methodAnnotations = method.getAnnotations();
        final Annotation[][] parameterAnnotationsArray = method.getParameterAnnotations();

        final GotoMethod goTo = new GotoMethod();
        parseMethodAnnotations(goTo, methodAnnotations, parameterAnnotationsArray);
        parseParameterAnnotation(goTo, parameterAnnotationsArray);

        return goTo;
    }

    protected void parseMethodAnnotations(
            @NonNull GotoMethod goTo, @NonNull Annotation[] methodAnnotations, @NonNull Annotation[][] parameterAnnotationsArray) {

        final HashMap<String, Integer> positions = new HashMap<>();
        for (int i = 0; i < parameterAnnotationsArray.length; i++) {
            final Annotation[] annotations = parameterAnnotationsArray[i];

            for (Annotation annotation : annotations) {
                if (annotation instanceof Path) {
                    positions.put(((Path) annotation).value(), i);
                }
            }
        }

        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof TargetClass) {
                goTo.setTargetClass(((TargetClass) annotation).value());

            } else if(annotation instanceof TargetClassName){
                goTo.setTargetClassName(((TargetClassName) annotation).value());

            } else if (annotation instanceof TargetPath) {
                final TargetPath path = (TargetPath) annotation;
                final String segements[] = path.value().split("[{}]");

                for (int i = 0; i < segements.length; i++) {
                    final String segment = segements[i];

                    if (i % 2 == 0) {
                        if (!RouterUtils.isTextEmpty(segment)) {
                            goTo.addPathSegement(segment);
                        }
                    } else {
                        final Integer position = positions.get(segment);
                        if (position != null) {
                            goTo.addPathSegement(position);
                        } else {
                            throw new RuntimeException(String.format(Locale.getDefault(),
                                    "@Path(\"%s\") not found.", segment));
                        }
                    }
                }

                if (!RouterUtils.isTextEmpty(path.mimeType())) {
                    goTo.setMimeType(path.mimeType());
                }

            } else if (annotation instanceof TargetFlags) {
                goTo.setTargetFlags(((TargetFlags) annotation).value());

            } else if (annotation instanceof TargetAction) {
                goTo.setTargetAction(((TargetAction) annotation).value());

            }else if (annotation instanceof RequestCode) {
                goTo.setRequestCode(((RequestCode) annotation).value());
            }
        }
    }

    protected void parseParameterAnnotation(@NonNull GotoMethod goTo, @NonNull Annotation[][] parameterAnnotationsArray) {
        for (int i = 0; i < parameterAnnotationsArray.length; i++) {
            final Annotation[] annotations = parameterAnnotationsArray[i];

            for (Annotation annotation : annotations) {
                if (annotation instanceof Bundle) {
                    goTo.addBundlePositions(((Bundle) annotation).value(), i);

                } else if (annotation instanceof Query) {
                    goTo.addQueryPositions(((Query) annotation).value(), i);

                } else if (annotation instanceof QueryMap) {
                    goTo.addQueryMapPositions(i);
                }
            }
        }
    }
}
