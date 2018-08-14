package me.hutcwp.apt_process;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import me.hutcwp.apt_lib.annotation.InitAttrConfig;
import me.hutcwp.apt_lib.annotation.InitAttrConfigs;

@AutoService(Processor.class)
public class ContainerProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Map<String, ContainerProxyInfo> mProxyMap = new HashMap<String, ContainerProxyInfo>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(InitAttrConfigs.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "process...");
        mProxyMap.clear();
        praseAnnotation(roundEnvironment);
        generateCode();
        return true;
    }

    private void praseAnnotation(RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(InitAttrConfigs.class);
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            //class name
            String fqClassName = typeElement.getQualifiedName().toString();
            ContainerProxyInfo proxyInfo = mProxyMap.get(fqClassName);
            if (proxyInfo == null) {
                proxyInfo = new ContainerProxyInfo(elementUtils, typeElement);
                mProxyMap.put(fqClassName, proxyInfo);
            }

            proxyInfo.processingEnv = processingEnv;
            InitAttrConfigs initAttrConfigs = typeElement.getAnnotation(InitAttrConfigs.class);
            proxyInfo.setConfigs(Arrays.asList(initAttrConfigs.value()));

        }
    }

    private void generateCode() {
        //统一进行文件生成
        for (String key : mProxyMap.keySet()) {
            ContainerProxyInfo proxyInfo = mProxyMap.get(key);
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        proxyInfo.getProxyClassFullName(),
                        proxyInfo.getTypeElement());
                Writer writer = jfo.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {

            }
        }
    }

}
