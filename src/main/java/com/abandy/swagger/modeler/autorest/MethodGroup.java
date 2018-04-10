package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Operation;

import java.util.*;

public class MethodGroup {
    List<Method> methods = new ArrayList<>();
    CodeModel codeModel;
    String name;

    public MethodGroup(String name, CodeModel codeModel) {
        this.name = name;
        this.codeModel = codeModel;
    }

    public void consume(String methodName, Operation operation) {
        Objects.requireNonNull(operation);
        Method method = null;
        Optional<Method> optionalMethod = methods.stream()
                .filter(findMethod -> methodName.equals(findMethod.name()))
                .findFirst();
        if(optionalMethod.isPresent()) {
            method = optionalMethod.get();
        }else {
            method = new Method(methodName, this);
            methods.add(method);
        }

        method.consume(operation);
    }

    public List<Method> methods() {
        return this.methods;
    }
}

