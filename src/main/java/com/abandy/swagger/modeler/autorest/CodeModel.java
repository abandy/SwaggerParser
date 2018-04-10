package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.*;

import java.util.*;

public class CodeModel {
    Swagger swagger;
    HashMap<String, ModelType> allModelTypes = new HashMap<>();
    String name;
    String modelsName;
    String apiVersion;
    String baseUrl;
    List<String> methodGroupNames = new ArrayList<>();
    String documentation;
    Map<String, Object> extensions = new HashMap<>();
    String qualifier;
    List<MethodGroup> operations = new ArrayList<>();
    List<EnumType> enumTypes = new ArrayList<>();
    List<CompositeType> modelTypes = new ArrayList<>();
    List<CompositeType> errorTypes = new ArrayList<>();
    List<CompositeType> headerTypes = new ArrayList<>();
    Map<String, Parameter> parameters = new HashMap<>();

    public void consume(Swagger swagger) {
        Objects.requireNonNull(swagger);
        this.swagger = swagger;
        if(this.apiVersion != null) {
            this.apiVersion = swagger.version();
        }

        this.baseUrl = swagger.basePath();

        this.consumeParameters(swagger.parameters());
        this.consumeDefinitions(swagger.definitions());
        this.consumePath(swagger.paths());
    }

    private void consumeParameters(List<SwaggerParameter> parameters) {
        for(SwaggerParameter parameter : parameters) {
            Parameter param = new Parameter(parameter.name(), null)
                    .consume(parameter, this);
            this.parameters.put(parameter.key(), param);
        }
    }

    private void consumeDefinitions(List<Schema> definitions) {
        for(Schema schema : definitions) {
            this.getModelFromSchema(schema);
        }

        for(Schema schema : definitions) {
            ModelType modelType = this.allModelTypes.get(schema.name());
            if(modelType != null) {
                modelType.configure(schema);
            }
        }
    }

    private void consumePath(List<Path> paths) {
        Map<String, MethodGroup> methodGroups = new HashMap<String, MethodGroup>();
        for(Path path : paths) {
            for (Operation operation : path.operations()) {
                String[] groupAndMethodName = this.getGroupAndMethodName(operation);
                if (!methodGroups.containsKey(groupAndMethodName[0])) {
                    methodGroupNames.add(groupAndMethodName[0]);
                    methodGroups.put(groupAndMethodName[0],
                            new MethodGroup(groupAndMethodName[0], this));
                }

                MethodGroup methodGroup = methodGroups.get(groupAndMethodName[0]);
                methodGroup.consume(groupAndMethodName[1], operation);
            }
        }

        this.operations = new ArrayList<>(methodGroups.values());
    }

    private String[] getGroupAndMethodName(Operation operation) {
        Objects.requireNonNull(operation);
        String operationId = operation.operationId();
        Objects.requireNonNull(operationId);
        if(operationId.contains("_")) {
            return operationId.split("_");
        }else {
            String[] parts = operation.path().url().split("/");
            String methodName = "unknown";
            for(int i = parts.length - 1; i > 0; i--) {
                if(!parts[i].startsWith("{")) {
                    methodName = parts[i];
                    break;
                }
            }

            return new String[] {methodName, operationId};
        }
    }

    public ModelType findReference(String reference) {
        if(reference.contains(".json")) {
            //references another doc
            //need to create empty type for basetype
        }else if(reference.startsWith("#/definitions/")) {
            String definitionName = reference.substring("#/definitions/".length());
            if (this.allModelTypes.containsKey(definitionName)) {
                return this.allModelTypes.get(definitionName);
            }
        }

        return null;
    }

    public Parameter findParameterReference(String reference) {
        if(reference.startsWith("#/parameters/")) {
            String definitionName = reference.substring("#/parameters/".length());
            if (this.parameters.containsKey(definitionName)) {
                return this.parameters.get(definitionName);
            }
        }
        return null;
    }

    public ModelType getModelFromSchema(Schema schema) {
        String typeName = schema.type();
        KnownType kpType = KnownType.forString(typeName);
        ModelType modelType = CodeModel.createModelFromType(
                kpType, schema, this);
        modelType.consume(schema);
        if(!this.allModelTypes.containsKey(modelType.name())) {
            this.allModelTypes.put(modelType.name(), modelType);
            if(modelType instanceof EnumType) {
                this.enumTypes.add((EnumType)modelType);
            }else if(modelType instanceof CompositeType) {
                this.modelTypes.add((CompositeType)modelType);
            }
        }
        return modelType;
    }

    private static ModelType createModelFromType(KnownType kpType, Schema schema, CodeModel codeModel) {
        if(schema.enumValues().size() > 0) {
            return new EnumType(codeModel);
        }
        switch(kpType) {
            case Object:
                return new CompositeType(codeModel);
            case Array:
                return new SequenceType();
            default:
                return new PrimaryType(schema.format(), kpType);
        }

    }
}
