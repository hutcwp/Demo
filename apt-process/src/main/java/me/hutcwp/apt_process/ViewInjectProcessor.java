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

import me.hutcwp.apt_lib.annotation.BindView;
import me.hutcwp.apt_lib.annotation.OnClick;


/**
 * Created by hutcwp on 2018/4/19.
 */


@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {
    private Messager messager;
    private Elements elementUtils;
    private Map<String, ViewProxyInfo> mProxyMap = new HashMap<String, ViewProxyInfo>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(BindView.class.getCanonicalName());
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
        praseBind(roundEnv);
        praseClick(roundEnv);
        autoCode();
        return true;
    }

    private void autoCode() {
        //统一进行文件生成
        for (String key : mProxyMap.keySet()) {
            ViewProxyInfo viewProxyInfo = mProxyMap.get(key);
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        viewProxyInfo.getProxyClassFullName(),
                        viewProxyInfo.getTypeElement());
                Writer writer = jfo.openWriter();
                writer.write(viewProxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                error(viewProxyInfo.getTypeElement(),
                        "Unable to write injector for type %s: %s",
                        viewProxyInfo.getTypeElement(), e.getMessage());
            }

        }
    }

    private void praseClick(RoundEnvironment roundEnv) {
        Set<? extends Element> elesWithOnClick = roundEnv.getElementsAnnotatedWith(OnClick.class);
        for (Element element : elesWithOnClick) {
            checkAnnotationValid(element, OnClick.class);
            //class type
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            //full class name
            String fqClassName = classElement.getQualifiedName().toString();
            ViewProxyInfo viewProxyInfo = mProxyMap.get(fqClassName);
            if (viewProxyInfo == null) {
                viewProxyInfo = new ViewProxyInfo(elementUtils, classElement);
                mProxyMap.put(fqClassName, viewProxyInfo);
            }

            OnClick clickAnnotation = element.getAnnotation(OnClick.class);
            int id = clickAnnotation.value();
            viewProxyInfo.injectClickMethods.put(id, element);
        }
    }

    private void praseBind(RoundEnvironment roundEnv) {
        Set<? extends Element> elesWithBind = roundEnv.getElementsAnnotatedWith(BindView.class);
        for (Element element : elesWithBind) {

            checkAnnotationValid(element, BindView.class);

            VariableElement variableElement = (VariableElement) element;
            //class type
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            //full class name
            String fqClassName = classElement.getQualifiedName().toString();

            ViewProxyInfo viewProxyInfo = mProxyMap.get(fqClassName);
            if (viewProxyInfo == null) {
                viewProxyInfo = new ViewProxyInfo(elementUtils, classElement);
                mProxyMap.put(fqClassName, viewProxyInfo);
            }

            BindView bindViewAnnotation = variableElement.getAnnotation(BindView.class);
            int id = bindViewAnnotation.value();
            viewProxyInfo.injectVariables.put(id, variableElement);
        }
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
