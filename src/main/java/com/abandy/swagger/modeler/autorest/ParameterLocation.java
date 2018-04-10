package com.abandy.swagger.modeler.autorest;

import java.util.Objects;

public enum ParameterLocation {
    None("none"),
    Path("path"),
    Query("query"),
    Header("header"),
    Body("body"),
    FormData("formdata");

    private String location;
    ParameterLocation(String location) {
        this.location = location;
    }

    public static ParameterLocation forString(String location) {
        Objects.requireNonNull(location);
        location = location.toLowerCase();
        for(ParameterLocation ploc : ParameterLocation.values()) {
            if(ploc.location.equals(location)) {
                return ploc;
            }
        }

        throw new RuntimeException(
                String.format("ParameterLocation %s not found.", location));
    }
}
