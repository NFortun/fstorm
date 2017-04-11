package fr.istic.m1.fstorm.utils;

import fr.istic.m1.fstorm.beans.StormComponent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class GenerateJavaSpout {
  private String odir;
  
  private String packageName;
  
  private String libName;
  
  public GenerateJavaSpout(final String pn, final String odir, final String libName) {
    this.packageName = pn;
    this.odir = odir;
    this.libName = libName;
  }
  
  public CharSequence GenerateJava(final StormComponent component) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    _builder.append(this.packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.apache.storm.tuple.Fields;");
    _builder.newLine();
    _builder.append("import org.apache.storm.tuple.Values;");
    _builder.newLine();
    _builder.append("import org.apache.storm.topology.IRichSpout;");
    _builder.newLine();
    _builder.append("import org.apache.storm.topology.OutputFieldsDeclarer;");
    _builder.newLine();
    _builder.append("import org.apache.storm.spout.SpoutOutputCollector;");
    _builder.newLine();
    _builder.append("import org.apache.storm.task.TopologyContext;");
    _builder.newLine();
    _builder.append("import java.util.Map;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class ");
    String _kernelName = component.getKernelName();
    String _firstUpper = StringExtensions.toFirstUpper(_kernelName);
    _builder.append(_firstUpper, "");
    _builder.append("Spout implements IRichSpout {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("private SpoutOutputCollector collector;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private boolean completed = false;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private TopologyContext context;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public native ");
    String _returnType = component.getReturnType();
    _builder.append(_returnType, "\t");
    _builder.append(" ");
    String _kernelName_1 = component.getKernelName();
    _builder.append(_kernelName_1, "\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("  \t");
    _builder.append("public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("this.context = context;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("this.collector = collector;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public void nextTuple() {");
    _builder.newLine();
    _builder.append("\t  \t");
    _builder.append("collector.emit(");
    String _kernelName_2 = component.getKernelName();
    _builder.append(_kernelName_2, "\t  \t");
    _builder.append("());");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void declareOutputFields(OutputFieldsDeclarer declarer) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void close() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean isDistributed() {");
    _builder.newLine();
    _builder.append("\t  \t");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void activate() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void deactivate() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void ack(Object msgId) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void fail(Object msgId) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Map<String, Object> getComponentConfiguration() {");
    _builder.newLine();
    _builder.append("\t \t");
    _builder.append("return null;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("static { System.loadLibrary(\"");
    _builder.append(this.libName, "\t");
    _builder.append("\"); }");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public Path Execute(final StormComponent cmp) {
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
        String _kernelName = cmp.getKernelName();
        String _plus = (_kernelName + "Spout.java");
        Path _get_2 = Paths.get(this.odir, _plus);
        Files.deleteIfExists(_get_2);
        String _kernelName_1 = cmp.getKernelName();
        String _plus_1 = (_kernelName_1 + "Spout.java");
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
