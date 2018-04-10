package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.DefaultSchema;
import com.abandy.swagger.modeler.swagger.Schema;
import com.abandy.swagger.modeler.swagger.SwaggerParameter;

public class Parameter extends AbstractVariable {
    Method method;
    boolean isClientProperty;
    Property clientProperty;
    boolean isContentTypeHeader;
    ParameterLocation location;

    public Parameter(String name, Method method) {
        this.name = name;
        this.method = method;
    }

    public Parameter consume(SwaggerParameter parameter, CodeModel codeModel) {
        if(parameter.reference() != null) {
            this.copy(codeModel.findParameterReference(parameter.reference()));
        }else {
            this.location = ParameterLocation.forString(parameter.in());
            this.isRequired = parameter.required();
            this.extensions = parameter.extensions();
            DefaultSchema schema = parameter.data();
            if(schema != null) {
                if (schema.reference() != null) {
                    this.modelType = codeModel.findReference(schema.reference());
                    this.modelTypeName = this.modelType.name();
                } else if(schema instanceof Schema){
                    ModelType modelType = codeModel.getModelFromSchema((Schema)schema);
                    modelType.configure((Schema)schema);
                    this.modelType = modelType;
                    this.modelTypeName = this.modelType.name();
                }
            }
        }

        return this;
    }

    public void copy(Parameter parameter) {
        if(parameter != null) {
            this.name = parameter.name;
            this.location = parameter.location;
            this.extensions = parameter.extensions;
            this.isRequired = parameter.isRequired;
        }else {
            int one = 1;
        }
    }
}
