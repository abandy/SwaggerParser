package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Schema;
import com.abandy.swagger.modeler.swagger.SwaggerResponse;

import java.util.Map;

public class Response {
    int httpCode;
    IModelType body;
    IModelType headers;
    Map<String, Object> extensions;
    boolean isNullable;

    public void consume(SwaggerResponse swaggerResponse, CodeModel codeModel) {
        try {
            this.httpCode = Integer.parseInt(swaggerResponse.name());
        }catch(Exception ex) {
            this.httpCode = 0;
        }

        if(swaggerResponse.schema() != null) {
            Schema schema = swaggerResponse.schema();
            KnownType kType = KnownType.forString(schema.type());
            if (schema.isObject()) {
                this.body = codeModel.findReference(schema.reference());
            } else if (!schema.isArray()) {
                this.body = new PrimaryType(schema.type(), kType);
            } else {
                throw new RuntimeException("Response body of inline object or array not allowed at this time.");
            }
        }
    }
}
