package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;

public class SwaggerParameter extends BaseSwagger {
    String reference;
    String name;
    String in;
    String description;
    boolean required;
    DefaultSchema data;

    public SwaggerParameter parse(JsonObject jsonObj, String key) {
        super.parse(jsonObj, key);
        this.reference = jsonObj.getString("$ref", null);
        this.name = jsonObj.getString("name", null);
        this.in = jsonObj.getString("in", null);
        this.description = jsonObj.getString("description", null);
        if("body".equals(this.in)) {
            JsonObject schema = jsonObj.getJsonObject("schema");
            this.data = new Schema().parse(schema, "");
        }else {
            this.data = new Item().parse(jsonObj, "");
        }

        return this;
    }

    public String reference() {
        return this.reference;
    }

    public String name() {
        return this.name;
    }

    public String in() {
        return this.in;
    }

    public String description() {
        return this.description;
    }

    public boolean required() {
        return this.required;
    }

    public DefaultSchema data() {
        return this.data;
    }

}
