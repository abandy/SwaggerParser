package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class Operation extends BaseSwagger {
    String name;
    List<String> tags = new ArrayList<String>();
    String summary;
    String description;
    String operationId;
    List<String> consumes = new ArrayList<String>();
    List<String> produces = new ArrayList<String>();
    List<SwaggerParameter> parameters = new ArrayList<SwaggerParameter>();
    List<SwaggerResponse> responses = new ArrayList<SwaggerResponse>();
    List<String> schemas = new ArrayList<String>();
    boolean deprecated;
    Path path;

    public Operation parse(JsonObject jsonObj, String key) {
        super.parse(jsonObj, "");
        this.name = key;
        Swagger.addStringArray(jsonObj, "tags", this.tags);
        this.summary = jsonObj.getString("summary", null);
        this.description = jsonObj.getString("description", null);
        this.operationId = jsonObj.getString("operationId", null);
        Swagger.addStringArray(jsonObj, "consumes", this.consumes);
        Swagger.addStringArray(jsonObj, "produces", this.produces);
        Swagger.addObjectArray(jsonObj,
                "parameters",
                new Swagger.ObjectParser<SwaggerParameter>() {
                    @Override
                    public SwaggerParameter parse(JsonObject jsonObj, String key) {
                        return new SwaggerParameter().parse(jsonObj, key);
                    }
                },
                this.parameters);
        Swagger.addObjectProperties(jsonObj,
                "responses",
                new Swagger.ObjectParser<SwaggerResponse>() {
                    @Override
                    public SwaggerResponse parse(JsonObject jsonObj, String key) {
                        return new SwaggerResponse().parse(jsonObj, key);
                    }
                },
                this.responses);
        Swagger.addStringArray(jsonObj, "schemas", this.schemas);
        this.deprecated = jsonObj.getBoolean("deprecated", false);
        return this;
    }

    public void path(Path path) {
        this.path = path;
    }

    public Path path() {
        return this.path;
    }

    public String name() {
        return this.name;
    }

    public List<String> tags() {
        return this.tags;
    }

    public String summary() {
        return this.summary;
    }

    public String description() {
        return this.description;
    }

    public String operationId() {
        return this.operationId;
    }

    public List<String> consumes() {
        return this.consumes;
    }

    public List<String> produces() {
        return this.produces;
    }

    public List<SwaggerParameter> parameters() {
        return this.parameters;
    }

    public List<SwaggerResponse> responses() {
        return this.responses;
    }

    public List<String> schemas() {
        return this.schemas;
    }

    public boolean deprecated() {
        return this.deprecated;
    }
}
