package me.hutcwp.apt_process;


import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import me.hutcwp.apt_lib.Bind;
import me.hutcwp.apt_lib.OnClick;


/**
 * Created by hutcwp on 2018/4/19.
 */


@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {
    private Messager messager;
    private Elements elementUtils;
    private Map<String, ProxyInfo> mProxyMap = new HashMap<String, ProxyInfo>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Bind.class.getCanonicalName());
        supportTypes.add(OnClick.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "process...");
        mProxyMap.clear();

        Set<? extends Element> elesWithBind = roundEnv.getElementsAnnotatedWith(Bind.class);
        for (Element element : elesWithBind) {
            // 判断是变量类型，并且是访问类型public ，这里会出问题！！！？
            checkAnnotationValid(element, Bind.class);

            VariableElement variableElement = (VariableElement) element;
            //class type
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            //full class name
            String fqClassName = classElement.getQualifiedName().toString();

            ProxyInfo proxyInfo = mProxyMap.get(fqClassName);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(elementUtils, classElement);
                mProxyMap.put(fqClassName, proxyInfo);
            }

            Bind bindAnnotation = variableElement.getAnnotation(Bind.class);
            int id = bindAnnotation.value();
            proxyInfo.injectVariables.put(id, variableElement);
        }

        Set<? extends Element> elesWithOnClick = roundEnv.getElementsAnnotatedWith(OnClick.class);
        for (Element element : elesWithOnClick) {
            // 判断是变量类型，并且是访问类型public ，这里会出问题！！！？
            checkAnnotationValid(element, OnClick.class);

//            VariableElement variableElement = (VariableElement) element;
            //class type
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            //full class name
            String fqClassName = classElement.getQualifiedName().toString();

            ProxyInfo proxyInfo = mProxyMap.get(fqClassName);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(elementUtils, classElement);
                mProxyMap.put(fqClassName, proxyInfo);
            }

            OnClick clickAnnotation = element.getAnnotation(OnClick.class);
            int id = clickAnnotation.value();
            proxyInfo.injectClickMethods.put(id, element);
        }

        //统一进行文件生成
        for (String key : mProxyMap.keySet()) {
            ProxyInfo proxyInfo = mProxyMap.get(key);
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        proxyInfo.getProxyClassFullName(),
                        proxyInfo.getTypeElement());
                Writer writer = jfo.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                error(proxyInfo.getTypeElement(),
                        "Unable to write injector for type %s: %s",
                        proxyInfo.getTypeElement(), e.getMessage());
            }

        }
        return true;
    }

    private boolean checkAnnotationValid(Element annotatedElement, Class clazz) {
        if (annotatedElement.getKind() != ElementKind.METHOD) {
            error(annotatedElement, "%s must be declared on field.", clazz.getSimpleName());
            return false;
        }
        if (ClassValidator.isPrivate(annotatedElement)) {
            error(annotatedElement, "%s() must can not be private.", annotatedElement.getSimpleName());
            return false;
        }

        return true;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
