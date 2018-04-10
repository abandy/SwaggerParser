package com.abandy.swagger.modeler.autorest;

public interface IModelType {
    String name();
    String extendedDocumentation();
    String defaultValue();
    boolean isConstant();
    String declarationName();
    String className();
}
