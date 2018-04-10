package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Extension;

import java.util.List;
import java.util.Map;
import java.util.Set;

interface IVariable {
    Set<String> locallyUsedNames();
    IModelType modelType();
    String serializedName();
    String modelTypeName();
    String name();
    boolean isConstant();
    boolean isRequired();
    Map<String, Extension> extensions();
    String documentation();
    String defaultValue();
    String collectionFormat();
    List<String> myReservedNames();
}
