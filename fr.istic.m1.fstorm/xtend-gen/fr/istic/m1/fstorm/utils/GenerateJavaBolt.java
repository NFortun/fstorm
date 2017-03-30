package fr.istic.m1.fstorm.utils;

import backtype.storm.tuple.Tuple;
import fr.istic.m1.fstorm.beans.StormComponent;
import gecos.core.ParameterSymbol;
import gecos.core.Procedure;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class GenerateJavaBolt {
  private String packageName;
  
  private String BuilDir;
  
  public GenerateJavaBolt(final String packageName, final String BuildDir) {
    this.packageName = packageName;
    this.BuilDir = this.BuilDir;
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
      Procedure _kernel = component.getKernel();
      final EList<ParameterSymbol> p = _kernel.listParameters();
      ArrayList<String> param = new ArrayList<String>();
      for (int i = 0; (i < p.size()); i++) {
        List<String> _paramTypes = component.getParamTypes();
        String _get = _paramTypes.get(i);
        String _plus = (_get + " ");
        ParameterSymbol _get_1 = p.get(i);
        String _name = _get_1.getName();
        String _plus_1 = (_plus + _name);
        param.add(_plus_1);
      }
      ArrayList<String> argToC = new ArrayList<String>();
      for (int i = 0; (i < p.size()); i++) {
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
      String _kernelName_2 = component.getKernelName();
      _builder.append(_kernelName_2, "\t\t");
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
      _builder.append("collector.emit(");
      String _kernelName_3 = component.getKernelName();
      _builder.append(_kernelName_3, "\t\t");
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
      _builder.append("));");
      _builder.newLineIfNotEmpty();
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
      String _kernelName = component.getKernelName();
      String _firstUpper = StringExtensions.toFirstUpper(_kernelName);
      String _plus = (_firstUpper + "Bolt.java");
      final File file = new File(_plus);
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
