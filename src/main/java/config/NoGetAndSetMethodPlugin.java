package config;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
/**
 * @ClassName 去除set/get方法
 * @Description TODO
 * @Author malf
 * @Date 2020/12/14 18:18
 * @Version 1.0
 **/
public class NoGetAndSetMethodPlugin extends PluginAdapter {
    private FullyQualifiedJavaType dataAnnotation;

    /**
     * LombokPlugin contructor
     */
    public NoGetAndSetMethodPlugin() {
    }

    /**
     * @param warnings
     * @return always true
     */
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * Prevents all getters from being generated.
     * See SimpleModelGenerator
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    /**
     * Prevents all setters from being generated
     * See SimpleModelGenerator
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

}
