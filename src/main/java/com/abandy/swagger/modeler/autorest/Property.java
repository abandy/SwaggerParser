package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Schema;

import java.util.List;

public class Property extends AbstractVariable {
    String summary;
    boolean isPolymorphicDiscriminator;
    CodeModel codeModel;

    public Property(CodeModel codeModel) {
        this.codeModel = codeModel;
    }

    public void consume(Schema schema) {
        this.name = schema.name();
        this.documentation = schema.description();
        this.readOnly = schema.readOnly();
        List<String> enumValues = schema.enumValues();
        if(enumValues.size() > 0) {
            this.modelType = this.codeModel.getModelFromSchema(schema);
            this.modelTypeName = schema.type();
        }else {
            this.modelTypeName = schema.type();
            KnownType knownType = KnownType.forString(modelTypeName);
            switch (knownType) {
                case Array:
                    if (schema.item() != null) {
                        KnownType itemKnownType = KnownType.forString(schema.item().type());
                        if (itemKnownType == KnownType.Object && schema.item().reference() != null) {
                            this.modelType = this.codeModel.findReference(schema.item().reference());
                        } else if (itemKnownType != KnownType.Array) {
                            this.modelType = new PrimaryType(schema.item().type(), itemKnownType);
                        } else {
                            throw new RuntimeException("Property defined as inline object not supported");
                        }
                    } else {
                        throw new RuntimeException("Property defined as inline object not supported");
                    }

                    break;
                case Object:
                    if (schema.reference() != null) {
                        this.modelType = this.codeModel.findReference(schema.reference());
                    } else {
                        throw new RuntimeException("Property defined as inline object not supported");
                    }
                    break;
                default:
                    this.modelType = new PrimaryType(schema);
            }
        }
    }
}
