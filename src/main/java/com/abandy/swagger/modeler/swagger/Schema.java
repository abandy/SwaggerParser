package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class Schema extends DefaultSchema {
    String reference;
    String title;
    String maxProperties;
    String minProperties;
    List<String> required = new ArrayList<>();
    List<DefaultSchema> allOf = new ArrayList<>();
    List<Schema> properties = new ArrayList<>();
    List<DefaultSchema> additionalProperties = new ArrayList<>();
    DefaultSchema item;
    String discriminator;
    boolean readOnly;

    public Schema parse(JsonObject jsonObj, String key) {
        super.parse(jsonObj, key);
        if(this.isArray()) {
            this.item = new Schema().parse(jsonObj.getJsonObject("items"), "items");
        }else {
            this.reference = jsonObj.getString("$ref", null);
        }

        this.title = jsonObj.getString("title", null);
        this.maxProperties = jsonObj.getString("maxProperties", null);
        this.minProperties = jsonObj.getString("minProperties", null);
        Swagger.addStringArray(jsonObj, "required", this.required);
        Swagger.addObjectArray(jsonObj, "allOf", new DefaultSchemaParser(), this.allOf);
        Swagger.addObjectProperties(jsonObj, "properties", new SchemaParser(), this.properties);
        Swagger.addObjectProperties(jsonObj, "additionalProperties", new DefaultSchemaParser(), this.additionalProperties);
        this.discriminator = jsonObj.getString("discriminator", null);
        this.readOnly = jsonObj.getBoolean("readOnly", false);
        return this;
    }

    public static class DefaultSchemaParser implements Swagger.ObjectParser<DefaultSchema> {

        @Override
        public DefaultSchema parse(JsonObject jsonObj, String key) {
            return new DefaultSchema().parse(jsonObj, key);
        }
    }

    public static class SchemaParser implements Swagger.ObjectParser<Schema> {

        @Override
        public Schema parse(JsonObject jsonObj, String key) {
            return new Schema().parse(jsonObj, key);
        }
    }

    public String reference() {
        return this.reference;
    }

    public String title() {
        return this.title;
    }

    public String maxProperties() {
        return this.maxProperties;
    }

    public String minProperties() {
        return this.minProperties;
    }

    public List<String> required() {
        return this.required;
    }

    public List<DefaultSchema> allOf() {
        return this.allOf;
    }

    public List<Schema> properties() {
        return this.properties;
    }

    public List<DefaultSchema> additionalProperties() {
        return this.additionalProperties;
    }

    public DefaultSchema item() {
        return this.item;
    }

    public String discriminator() {
        return this.discriminator;
    }

    public boolean readOnly() {
        return this.readOnly;
    }
}
