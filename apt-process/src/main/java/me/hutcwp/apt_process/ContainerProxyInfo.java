package me.hutcwp.apt_process;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import me.hutcwp.apt_lib.annotation.InitAttrConfig;

public class ContainerProxyInfo implements IProxyInfo {

    public ProcessingEnvironment processingEnv;
    private String packageName;
    private String className;
    private String proxyClassName;
    private TypeElement typeElement;

    private List<InitAttrConfig> configs = new ArrayList<>();

    private static final String PROXY = "FragmentInject";

    public ContainerProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        this.packageName = elementUtils.getPackageOf(classElement).getQualifiedName().toString();
        this.className = ClassValidator.getClassName(classElement, packageName);
        this.proxyClassName = className + "$$" + PROXY;
    }

    @Override
    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    @Override
    public TypeElement getTypeElement() {
        return typeElement;
    }

    @Override
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import me.hutcwp.apt_api.*;\n");
        generateImport(builder);
        builder.append("import " + packageName + "." + className + ";\n");
        builder.append('\n');
        builder.append("public class ").append(proxyClassName).append(" implements " + "Inject" + "<" + typeElement.getQualifiedName() + ">");
        builder.append(" {\n");
        generateMethods(builder);
        builder.append('\n');
        builder.append("}\n");
        return builder.toString();
    }

    private void generateImport(StringBuilder builder) {
        for (InitAttrConfig config : configs) {

            builder.append("import " + packageName + "." + className + ";\n");
        }
    }

    @Override
    public void generateMethods(StringBuilder builder) {
        builder.append("@Override\n ");
        builder.append("public void inject( final " + typeElement.getQualifiedName() + " host, Object source ) {\n");
        generateComponent(builder);
        builder.append("\n");
        builder.append("  }\n");
    }

    private void generateComponent(StringBuilder builder) {
        for (InitAttrConfig config : configs) {
            try {
                config.component();
            } catch (MirroredTypeException e) {
                TypeMirror typeMirror = e.getTypeMirror();

                /**
                 * 通过这个方法来获取具体实现类型！！！
                 */
                Types typeutils = processingEnv.getTypeUtils();
                TypeElement classTypeElement = (TypeElement) typeutils.asElement(typeMirror);
//                TypeElement e =(TypeElement) typeutils.asElement(typeMirror);
                builder.append("host.autoLoadComponent( +" + config.resourceId() + ", new " + classTypeElement.getQualifiedName() + "() );\n");
            }
        }
    }

    public void setConfigs(List<InitAttrConfig> configs) {
        this.configs = configs;
    }

}
