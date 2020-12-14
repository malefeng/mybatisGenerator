package config;

import com.mysql.cj.util.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @ClassName MyCommentGenerator
 * @Description TODO
 * @Author malf
 * @Date 2020/12/14 16:09
 * @Version 1.0
 **/
public class MyCommentGenerator extends DefaultCommentGenerator {
    /**
     * The properties.
     */
    private Properties properties;

    private SimpleDateFormat dateFormat;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    /**
     * 实体类对应的mapper.xml注释，mapper类不加注释，如有需要参考 DefaultCommentGenerator
     */
    @Override
    public void addComment(XmlElement xmlElement) {
    }

    @Override
    public void addRootComment(XmlElement rootElement) {

    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    protected String getDateString() {
        if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }

    /**
     * 设置class的注解
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**");

//        sb.append(" * This class corresponds to the database table ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, false);

        innerClass.addJavaDocLine(" */");
    }

    /**
     * 类级别
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        StringBuilder sb = new StringBuilder();

        //注释
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * @author mlf");
        topLevelClass.addJavaDocLine(String.format(" * @Date %s",new Date().toString()));
        topLevelClass.addJavaDocLine(" */");

        //注解
        topLevelClass.addAnnotation(String.format("@ApiModel(description = \"%s\")",introspectedTable.getRemarks()));
        topLevelClass.addAnnotation(String.format("@Table(name = \"%s\")",introspectedTable.getFullyQualifiedTable()));
        topLevelClass.addAnnotation("@Data");

        //依赖
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("javax.persistence.Table");
        topLevelClass.addImportedType("javax.persistence.Id");
        topLevelClass.addImportedType("javax.persistence.Column");
    }

    /**
     * 添加枚举的注释
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);

        innerEnum.addJavaDocLine(" */");
    }

    /**
     * 实体类字段注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtils.isNullOrEmpty(introspectedColumn.getRemarks())) {
            return;
        }

        String actualColumnName = introspectedColumn.getActualColumnName();
        if("id".equals(actualColumnName)){
            field.addAnnotation("@Id");
        }
        field.addAnnotation(String.format("@Column(name= \"%s\")",actualColumnName));
        field.addAnnotation(String.format("@ApiModelProperty(value= \"%s\")",introspectedColumn.getRemarks()));
    }

    /**
     * 类注释
     * @param innerClass
     * @param introspectedTable
     * @param markAsDoNotDelete
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**");

        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */");
    }
}
