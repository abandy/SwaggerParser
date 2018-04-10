package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Schema;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ModelType implements IModelType {
    String documentation;
    String summary;
    List<String> myReservedNames;
    String qualifier;
    String refName;
    Map<String, Object> extensions;
    Set<String> locallyUsedNames;
    CodeModel codeModel;
    boolean isConstant;
    String defaultValue;
    String extendedDocumentation;
    String declarationName;
    String className;
    String name;

    public String name() {
        return this.name;
    }

    public String extendedDocumentation() {
        return this.extendedDocumentation;
    }

    public String defaultValue() {
        return this.defaultValue;
    }

    public boolean isConstant() {
        return this.isConstant;
    }

    public String declarationName() {
        return this.declarationName;
    }

    public String className() {
        return this.className;
    }

    public void consume(Schema schema) {
        return;
    }

    public void configure(Schema schema) {
        return;
    }
}

