package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class SwaggerResponse extends BaseSwagger {
    String name;
    String description;
    Schema schema;
    List<Item> headers = new ArrayList<Item>();

    public SwaggerResponse parse(JsonObject jsonObj, String key) {
        super.parse(jsonObj, "");
        this.name = key;
        this.description = jsonObj.getString("description", null);
        JsonObject schema= jsonObj.getJsonObject("schema");
        if(schema != null) {
            this.schema = new Schema().parse(schema, "");
        }

        Swagger.addObjectArray(jsonObj, "items", new Swagger.ObjectParser<Item>() {
                    public Item parse(JsonObject jsonObj, String key) {
                        return new Item().parse(jsonObj, key);
                    }
                },
                this.headers);
        return this;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public Schema schema() {
        return this.schema;
    }

    public List<Item> headers() {
        return this.headers;
    }
}
