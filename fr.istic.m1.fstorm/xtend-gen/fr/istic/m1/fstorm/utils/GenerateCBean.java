package fr.istic.m1.fstorm.utils;

import fr.istic.m1.fstorm.beans.CBean;
import fr.istic.m1.fstorm.beans.CBeanAttribute;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
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
        _builder.append(_typeName, "\t");
        _builder.append(" ");
        String _name_1 = attr.getName();
        _builder.append(_name_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public ");
        String _typeName_1 = attr.getTypeName();
        _builder.append(_typeName_1, "\t");
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
        _builder.append(_typeName_2, "\t");
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
  
  public Path Execute(final CBean cmp) {
    try {
      Path _xblockexpression = null;
      {
        final CharSequence java = this.GenerateJava(cmp);
        Path _get = Paths.get(this.odir);
        boolean _exists = Files.exists(_get);
        boolean _not = (!_exists);
        if (_not) {
          Path _get_1 = Paths.get(this.odir);
          Files.createDirectory(_get_1);
        }
        String _name = cmp.getName();
        String _plus = (_name + ".java");
        Path _get_2 = Paths.get(this.odir, _plus);
        Files.deleteIfExists(_get_2);
        String _name_1 = cmp.getName();
        String _plus_1 = (_name_1 + ".java");
        Path _get_3 = Paths.get(this.odir, _plus_1);
        String _string = java.toString();
        byte[] _bytes = _string.getBytes();
        _xblockexpression = Files.write(_get_3, _bytes, StandardOpenOption.CREATE);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
