package com.abandy.swagger.modeler;

import com.abandy.swagger.modeler.autorest.CodeModel;
import com.abandy.swagger.modeler.swagger.Swagger;
import com.abandy.swagger.modeler.swagger.SwaggerParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoRestModelBuilder {
    public List<CodeModel> buildModels(String[] swaggerUrls)
            throws IOException {
        List<CodeModel> codeModels = new ArrayList<>();
        SwaggerParser swaggerParser = new SwaggerParser();
        SwaggerModelBuilder swaggerModelBuilder = new SwaggerModelBuilder();
        for(String swaggerUrl : swaggerUrls) {
            codeModels.add(this.buildModel(swaggerModelBuilder.buildModel(swaggerUrl)));
        }

        return codeModels;
    }

    private CodeModel buildModel(List<Swagger> swaggers) {
        CodeModel codeModel = new CodeModel();
        for(Swagger swagger : swaggers) {
            codeModel.consume(swagger);
        }

        return codeModel;
    }
}
