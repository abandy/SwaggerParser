package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Extension;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AbstractVariable implements IVariable {
    Set<String> locallyUsedNames;
    IModelType modelType;
    String serializedName;
    String modelTypeName;
    String name;
    boolean isConstant;
    boolean isRequired;
    Map<String, Extension> extensions;
    String documentation;
    String defaultValue;
    String collectionFormat;
    List<String> myReservedNames;
    boolean readOnly;

    @Override
    public Set<String> locallyUsedNames() {
        return this.locallyUsedNames;
    }

    @Override
    public IModelType modelType() {
        return this.modelType;
    }

    @Override
    public String serializedName() {
        return this.serializedName;
    }

    @Override
    public String modelTypeName() {
        return this.modelTypeName;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public boolean isConstant() {
        return this.isConstant;
    }

    @Override
    public boolean isRequired() {
        return this.isRequired;
    }

    public void isRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public Map<String, Extension> extensions() {
        return this.extensions;
    }

    @Override
    public String documentation() {
        return this.documentation;
    }

    @Override
    public String defaultValue() {
        return this.defaultValue;
    }

    @Override
    public String collectionFormat() {
        return this.collectionFormat;
    }

    @Override
    public List<String> myReservedNames() {
        return this.myReservedNames;
    }

    public boolean readOnly() {
        return this.readOnly;
    }
}
