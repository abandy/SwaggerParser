package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Schema;

public class PrimaryType extends ModelType {

    String format;
    KnownType knownPrimaryType;

    public PrimaryType(String type, KnownType knownPrimaryType) {
        this.name = type;
        this.knownPrimaryType = knownPrimaryType;
    }

    public PrimaryType(Schema schema) {
        this.name = schema.name();
        this.format = format;
        this.knownPrimaryType = knownPrimaryType;
    }
}
