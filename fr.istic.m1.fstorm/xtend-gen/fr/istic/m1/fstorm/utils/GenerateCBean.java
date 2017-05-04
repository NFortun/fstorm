package fr.istic.m1.fstorm.utils;

import fr.istic.m1.fstorm.beans.CBean;
import fr.istic.m1.fstorm.beans.CBeanAttribute;
import java.nio.file.Path;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class GenerateCBean {
  private String odir;
  
  private String packageName;
  
  public GenerateCBean(final String pn, final String odir) {
    this.packageName = pn;
    this.odir = odir;
  }
  
  public CharSequence GenerateJava(final CBean cbean) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    _builder.append(this.packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("class ");
    String _name = cbean.getName();
    _builder.append(_name, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      List<CBeanAttribute> _attributes = cbean.getAttributes();
      for(final CBeanAttribute attr : _attributes) {
        _builder.append("\t");
        _builder.append("private ");
        String _typeName = attr.getTypeName();
        String _javaType = this.javaType(_typeName);
        _builder.append(_javaType, "\t");
        _builder.append(" ");
        String _name_1 = attr.getName();
        _builder.append(_name_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public ");
        String _typeName_1 = attr.getTypeName();
        String _javaType_1 = this.javaType(_typeName_1);
        _builder.append(_javaType_1, "\t");
        _builder.append(" get");
        String _name_2 = attr.getName();
        String _firstUpper = StringExtensions.toFirstUpper(_name_2);
        _builder.append(_firstUpper, "\t");
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("return this.");
        String _name_3 = attr.getName();
        _builder.append(_name_3, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public void set");
        String _name_4 = attr.getName();
        String _firstUpper_1 = StringExtensions.toFirstUpper(_name_4);
        _builder.append(_firstUpper_1, "\t");
        _builder.append("(");
        String _typeName_2 = attr.getTypeName();
        String _javaType_2 = this.javaType(_typeName_2);
        _builder.append(_javaType_2, "\t");
        _builder.append(" ");
        String _name_5 = attr.getName();
        _builder.append(_name_5, "\t");
        _builder.append(") {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("this.");
        String _name_6 = attr.getName();
        _builder.append(_name_6, "\t\t");
        _builder.append(" = ");
        String _name_7 = attr.getName();
        _builder.append(_name_7, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public String javaType(final String s) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field WrapperEnvironment is undefined"
      + "\ngetBeanScope cannot be resolved"
      + "\ngetBean cannot be resolved"
      + "\n!= cannot be resolved");
  }
  
  public Path Execute(final CBean cmp) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field StandardOpenOption is undefined"
      + "\nCREATE cannot be resolved");
  }
}
