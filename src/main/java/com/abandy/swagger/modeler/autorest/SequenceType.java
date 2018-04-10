package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Schema;

public class SequenceType extends ModelType {
    String qualifier;
    IModelType elementType;

    public void consume(Schema schema) {
        this.className = this.name = schema.name();
        this.documentation = schema.description();
    }
}
