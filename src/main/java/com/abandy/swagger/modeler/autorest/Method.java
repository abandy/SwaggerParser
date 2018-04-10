package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Extension;
import com.abandy.swagger.modeler.swagger.Operation;
import com.abandy.swagger.modeler.swagger.SwaggerParameter;
import com.abandy.swagger.modeler.swagger.SwaggerResponse;

import java.util.*;

class Method {
    HttpMethod httpMethod;
    Parameter body;
    Map<String, Response> responses = new HashMap<>();
    Response returnType;
    String description;
    List<String> requestContentType = new ArrayList<>();
    List<String> responseContentTypes = new ArrayList<>();
    Map<String, Extension> extensions = new HashMap<>();
    CodeModel codeModel;
    String url;
    String name;
    MethodGroup methodGroup;
    List<Parameter> parameters = new ArrayList<>();
    boolean deprecated;

    public Method(String name, MethodGroup methodGroup) {
        this.name = name;
        this.methodGroup = methodGroup;
        this.codeModel = this.methodGroup.codeModel;
    }

    public void consume(Operation operation) {
        Objects.requireNonNull(operation);
        this.url = operation.path().url();
        this.httpMethod = HttpMethod.forString(operation.name());
        this.description = operation.description();
        this.deprecated = operation.deprecated();
        for(SwaggerResponse swaggerResponse : operation.responses()) {
            Response response = new Response();
            response.consume(swaggerResponse, this.codeModel);
            this.responses.put(swaggerResponse.name(), response);
            if(response.httpCode == 200) {
                this.returnType = response;
            }
        }

        for(SwaggerParameter swaggerParameter : operation.parameters()) {
            Parameter parameter = new Parameter(swaggerParameter.name(), this);
            parameter.consume(swaggerParameter, this.codeModel);
            this.parameters.add(parameter);
            if(parameter.location == ParameterLocation.Body) {
                this.body = parameter;
            }else if(parameter.location == ParameterLocation.Header &&
                    parameter.modelType() instanceof CompositeType) {
                this.codeModel.headerTypes.add((CompositeType)parameter.modelType());
            }
        }

        this.extensions.putAll(operation.extensions());
        if(operation.consumes().size() > 0) {
            this.requestContentType.addAll(operation.consumes());
        }else if(operation.path().swagger().consumes().size() > 0) {
            this.requestContentType.addAll(operation.path().swagger().consumes());
        }

        if(operation.produces().size() > 0) {
            this.responseContentTypes.addAll(operation.produces());
        }else if(operation.path().swagger().produces().size() > 0) {
            this.responseContentTypes.addAll(operation.path().swagger().produces());
        }
    }

    public String name() {
        return name;
    }

}
