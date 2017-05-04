package fr.istic.m1.fstorm.utils;

import backtype.storm.tuple.Tuple;
import fr.istic.m1.fstorm.beans.CBean;
import fr.istic.m1.fstorm.beans.CBeanAttribute;
import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.jni.BeanScope;
import fr.istic.m1.fstorm.jni.WrapperEnvironment;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class GenerateJavaBolt {
  private String packageName;
  
  private String BuildDir;
  
  private String libName;
  
  public GenerateJavaBolt(final String packageName, final String BuildDir, final String libName) {
    this.packageName = packageName;
    this.BuildDir = BuildDir;
    this.libName = libName;
  }
  
  public ArrayList<String> ArgToString(final Tuple tuple) {
    ArrayList<String> _xblockexpression = null;
    {
      ArrayList<String> arg = new ArrayList<String>();
      for (int i = 0; (i < tuple.size()); i++) {
        int _size = tuple.size();
        int _minus = (_size - 1);
        boolean _equals = (i == _minus);
        if (_equals) {
          Object _value = tuple.getValue(i);
          String _string = _value.toString();
          arg.add(_string);
        } else {
          Object _value_1 = tuple.getValue(i);
          String _string_1 = _value_1.toString();
          String _plus = (_string_1 + ",");
          arg.add(_plus);
        }
      }
      _xblockexpression = arg;
    }
    return _xblockexpression;
  }
  
  public CharSequence GenerateJava(final StormComponent component) {
    CharSequence _xblockexpression = null;
    {
      ArrayList<String> param = new ArrayList<String>();
      for (int i = 0; (i < component.getParamTypes().size()); i++) {
        List<String> _paramTypes = component.getParamTypes();
        String _get = _paramTypes.get(i);
        param.add(_get);
      }
      ArrayList<String> argToC = new ArrayList<String>();
      for (int i = 0; (i < component.getParamTypes().size()); i++) {
        List<String> _paramTypes = component.getParamTypes();
        String _get = _paramTypes.get(i);
        String _plus = ("(" + _get);
        String _plus_1 = (_plus + ") ");
        String _plus_2 = (_plus_1 + 
          "tuple.get(");
        String _plus_3 = (_plus_2 + Integer.valueOf(i));
        String _plus_4 = (_plus_3 + ")");
        argToC.add(_plus_4);
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package ");
      _builder.append(this.packageName, "");
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("import java.util.Map;");
      _builder.newLine();
      _builder.append("import org.apache.storm.tuple.Fields;");
      _builder.newLine();
      _builder.append("import org.apache.storm.tuple.Values;");
      _builder.newLine();
      _builder.append("import org.apache.storm.task.OutputCollector;");
      _builder.newLine();
      _builder.append("import org.apache.storm.task.TopologyContext;import org.apache.storm.topology.IRichBolt;");
      _builder.newLine();
      _builder.append("import org.apache.storm.topology.OutputFieldsDeclarer;");
      _builder.newLine();
      _builder.append("import org.apache.storm.tuple.Tuple;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("class ");
      String _kernelName = component.getKernelName();
      String _firstUpper = StringExtensions.toFirstUpper(_kernelName);
      _builder.append(_firstUpper, "");
      _builder.append("Bolt implements IRichBolt {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("public OutputCollector collector;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("public native ");
      String _returnType = component.getReturnType();
      _builder.append(_returnType, "\t");
      _builder.append(" ");
      String _kernelName_1 = component.getKernelName();
      _builder.append(_kernelName_1, "\t");
      _builder.append("(");
      {
        boolean _hasElements = false;
        for(final String arg : param) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate(",", "\t");
          }
          _builder.append(arg, "\t");
        }
      }
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("static {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("System.loadLibrary(\"");
      _builder.append(this.libName, "\t\t");
      _builder.append("\");");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("public void prepare(Map conf, TopologyContext context, OutputCollector collector) {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("this.collector = collector;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("public void execute(Tuple tuple) {");
      _builder.newLine();
      _builder.append("\t\t");
      String _returnType_1 = component.getReturnType();
      _builder.append(_returnType_1, "\t\t");
      _builder.append(" ret = ");
      String _kernelName_2 = component.getKernelName();
      _builder.append(_kernelName_2, "\t\t");
      _builder.append("(");
      {
        boolean _hasElements_1 = false;
        for(final String arg_1 : argToC) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate(",", "\t\t");
          }
          _builder.append(arg_1, "\t\t");
        }
      }
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      {
        if (((component.isFlat()).booleanValue() && (WrapperEnvironment.getBeanScope().getBean(component.getReturnType()) != null))) {
          _builder.append("\t\t");
          _builder.append("collector.emit(new Values(");
          _builder.newLine();
          {
            BeanScope _beanScope = WrapperEnvironment.getBeanScope();
            String _returnType_2 = component.getReturnType();
            CBean _bean = _beanScope.getBean(_returnType_2);
            List<CBeanAttribute> _attributes = _bean.getAttributes();
            boolean _hasElements_2 = false;
            for(final CBeanAttribute attr : _attributes) {
              if (!_hasElements_2) {
                _hasElements_2 = true;
              } else {
                _builder.appendImmediate(",", "\t\t");
              }
              _builder.append("\t\t");
              _builder.append("ret.get");
              String _name = attr.getName();
              String _firstUpper_1 = StringExtensions.toFirstUpper(_name);
              _builder.append(_firstUpper_1, "\t\t");
              _builder.append("()");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("\t\t");
          _builder.append("));");
          _builder.newLine();
        } else {
          _builder.append("\t\t");
          _builder.append("collector.emit(new Values(ret));");
          _builder.newLine();
        }
      }
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("public void cleanup() {");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("public void declareOutputFiels(OutputDeclarer declarer) {");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public void Execute(final StormComponent component) {
    try {
      final CharSequence JavaGenerated = this.GenerateJava(component);
      Path _get = Paths.get(this.BuildDir);
      boolean _exists = Files.exists(_get);
      boolean _not = (!_exists);
      if (_not) {
        Path _get_1 = Paths.get(this.BuildDir);
        Files.createDirectory(_get_1);
      }
      String _kernelName = component.getKernelName();
      String _firstUpper = StringExtensions.toFirstUpper(_kernelName);
      String _plus = ((this.BuildDir + "/") + _firstUpper);
      String _plus_1 = (_plus + "Bolt.java");
      final File file = new File(_plus_1);
      FileOutputStream _fileOutputStream = new FileOutputStream(file);
      final BufferedOutputStream FileWriter = new BufferedOutputStream(_fileOutputStream);
      String _string = JavaGenerated.toString();
      byte[] _bytes = _string.getBytes();
      FileWriter.write(_bytes);
      FileWriter.flush();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
