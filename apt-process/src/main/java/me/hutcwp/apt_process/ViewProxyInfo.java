package me.hutcwp.apt_process;

/**
 * Created by hutcwp on 2018/4/19.
 */

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class ViewProxyInfo {
    private String packageName;
    private String className;
    private String proxyClassName;
    private TypeElement typeElement;

    public Map<Integer, VariableElement> injectVariables = new HashMap<>();
    public Map<Integer, Element> injectClickMethods = new HashMap<>();

    public static final String PROXY = "ViewInject";

    public ViewProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        this.packageName = elementUtils.getPackageOf(classElement).getQualifiedName().toString();
        this.className = ClassValidator.getClassName(classElement, packageName);
        this.proxyClassName = className + "$$" + PROXY;
    }

    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import me.hutcwp.apt_api.*;\n");
        builder.append("import android.view.View;\n");
        builder.append("import " + packageName + "." + className + ";\n");
        builder.append('\n');
        builder.append("public class ").append(proxyClassName).append(" implements " + "Inject" + "<" + typeElement.getQualifiedName() + ">");
        builder.append(" {\n");
        generateMethods(builder);
        builder.append('\n');

        builder.append("}\n");
        return builder.toString();
    }


    private void generateMethods(StringBuilder builder) {
        builder.append("@Override\n ");
        builder.append("public void inject( final " + typeElement.getQualifiedName() + " host, Object source ) {\n");

        for (int id : injectVariables.keySet()) {
            VariableElement element = injectVariables.get(id);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();
            builder.append(" if(source instanceof android.app.Activity){\n");
            builder.append("host." + name).append(" = ");
            builder.append("(" + type + ")(((android.app.Activity)source).findViewById( " + id + "));\n");
            builder.append("\n}else{\n");
            builder.append("host." + name).append(" = ");
            builder.append("(" + type + ")(((android.view.View)source).findViewById( " + id + "));\n");
            builder.append("\n};");

            if (injectClickMethods.containsKey(id)) {
                Element clickElement = injectClickMethods.get(id);
                builder.append("host." + name).append(".setOnClickListener(new View.OnClickListener() {\n");
                builder.append(" @Override\n");
                builder.append("public void onClick(View view) {\n");

                String methodName = clickElement.getSimpleName().toString();
                builder.append("  host." + methodName + "();\n");

                builder.append(" }\n");
                builder.append("});\n");
            }
        }

        builder.append("  }\n");
    }

    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }


}