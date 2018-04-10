package com.abandy.swagger.modeler.autorest;

import java.util.Objects;

public enum HttpMethod {
    None("none"),
    Get("get"),
    Post("post"),
    Put("put"),
    Patch("patch"),
    Delete("delete"),
    Head("head"),
    Options("options");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod forString(String method) {
        Objects.requireNonNull(method);
        method = method.toLowerCase();
        for(HttpMethod ploc : HttpMethod.values()) {
            if(ploc.method.equals(method)) {
                return ploc;
            }
        }

        throw new RuntimeException(
                String.format("HttpMethod %s not found.", method));
    }

    }
