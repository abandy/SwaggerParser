package com.abandy.swagger.modeler;

import com.abandy.swagger.modeler.swagger.Swagger;
import com.abandy.swagger.modeler.swagger.SwaggerParser;

import java.io.IOException;
import java.util.List;

public class SwaggerModelBuilder {
    public List<Swagger> buildModel(String swaggerUrl)
            throws IOException {
        SwaggerParser swaggerParser = new SwaggerParser();
        SwaggerEntry entry = SwaggerEntry.parse(swaggerUrl);
        return swaggerParser.parse(entry);
    }

}
