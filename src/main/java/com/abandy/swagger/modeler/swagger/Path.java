package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class Path extends BaseSwagger {
    String url;
    String reference;
    Operation get;
    Operation put;
    Operation post;
    Operation delete;
    Operation option;
    Operation head;
    Operation patch;
    List<SwaggerParameter> parameters = new ArrayList<SwaggerParameter>();
    List<Operation> operations = new ArrayList<Operation>();
    Swagger swagger;

    public Path(Swagger swagger) {
        this.swagger = swagger;
    }

    public Path parse(JsonObject jsonObj, String key) {
        super.parse(jsonObj, "");
        this.url = key;
        this.reference = jsonObj.getString("$ref", null);
        this.get = this.getOperation(jsonObj, "get");
        this.put = this.getOperation(jsonObj, "get");
        this.post = this.getOperation(jsonObj, "post");
        this.delete = this.getOperation(jsonObj, "delete");
        this.option = this.getOperation(jsonObj, "option");
        this.head = this.getOperation(jsonObj, "head");
        this.patch = this.getOperation(jsonObj, "patch");
        Swagger.addObjectArray(jsonObj,
            "parameters",
            new Swagger.ObjectParser<SwaggerParameter>() {
                @Override
                public SwaggerParameter parse(JsonObject jsonObj, String key) {
                    return new SwaggerParameter().parse(jsonObj, key);
                }
            },
            this.parameters);
        return this;
    }

    public Operation getOperation(JsonObject jsonObj, String key) {
        JsonObject opObj = jsonObj.getJsonObject(key);
        if(opObj != null) {
            Operation operation = new Operation().parse(opObj, key);
            operation.path(this);
            this.operations.add(operation);
            return operation;
        }

        return null;
    }

    public String url() {
        return this.url;
    }

    public String reference() {
        return this.reference;
    }

    public Operation get() {
        return this.get;
    }

    public Operation put() {
        return this.put;
    }

    public Operation post() {
        return this.post;
    }

    public Operation delete() {
        return this.delete;
    }

    public Operation option() {
        return this.option;
    }

    public Operation head() {
        return this.head;
    }

    public Operation patch() {
        return this.patch;
    }

    public List<SwaggerParameter> parameters() {
        return this.parameters;
    }

    public List<Operation> operations() {
        return this.operations;
    }

    public Swagger swagger() { return this.swagger; }
}
