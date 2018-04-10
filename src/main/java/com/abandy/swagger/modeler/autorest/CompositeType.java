package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.DefaultSchema;
import com.abandy.swagger.modeler.swagger.Schema;

import java.util.*;

public class CompositeType extends ModelType {
    String polymorphicDiscriminator;
    Property polymorphicDiscriminatorProperty;
    boolean isPolymorphic;
    boolean baseIsPolymorphic;
    String basePolymorphicDiscriminator;
    CompositeType baseModelType;
    List<Property> properties = new ArrayList<>();
    CodeModel codeModel;
    boolean doneSecondPass;

    public CompositeType(CodeModel model) {
        this.codeModel = model;
    }

    public void consume(Schema schema) {
        this.name = schema.name();
        this.polymorphicDiscriminator = schema.discriminator();
        this.documentation = schema.description();
    }

    public void configure(Schema schema) {
        if(doneSecondPass) {
            return;
        }

        doneSecondPass = true;
        this.consumeAllOf(schema);
        this.loadAllProperties(schema);
    }

    private void consumeAllOf(Schema schema) {
        for(DefaultSchema defaultSchema : schema.allOf()) {
            if(defaultSchema.reference() != null) {
                this.baseModelType =
                        (CompositeType)this.codeModel.findReference(defaultSchema.reference());

            }
        }
    }

    private void loadAllProperties(Schema schema) {
        Set<String> required = new HashSet<>(schema.required());
        for(Schema propSchema : schema.properties()) {
            KnownType knownType = KnownType.forString(propSchema.type());
            if(knownType == KnownType.Object) {
                this.loadAllProperties(propSchema);
                continue;
            }

            Property property = new Property(this.codeModel);
            property.consume(propSchema);
            if(required.contains(property.name())) {
                property.isRequired(true);
            }

            this.properties.add(property);
        }
    }
}