package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;

public class Tag extends BaseSwagger {
    String name;
    String description;

    public Tag parse(JsonObject jsonObj, String key) {
        super.parse(jsonObj, "");
        this.name = jsonObj.getString("name", null);
        this.description = jsonObj.getString("description", null);
        return this;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }
}
