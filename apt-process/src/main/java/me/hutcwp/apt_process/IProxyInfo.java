package me.hutcwp.apt_process;

import javax.lang.model.element.TypeElement;

public interface IProxyInfo {
    TypeElement getTypeElement();
    void generateMethods(StringBuilder builder);
    String generateJavaCode();
    String getProxyClassFullName();
}
