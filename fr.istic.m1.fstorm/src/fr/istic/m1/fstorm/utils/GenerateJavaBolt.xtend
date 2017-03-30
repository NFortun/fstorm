package fr.istic.m1.fstorm.utils

import fr.istic.m1.fstorm.beans.StormComponent
import java.util.ArrayList
import java.io.File
import java.io.FileOutputStream
import java.io.BufferedOutputStream
import backtype.storm.tuple.Tuple

class GenerateJavaBolt {
	String packageName;
	String BuilDir;
	
	new(String packageName, String BuildDir) {
		this.packageName = packageName;
		this.BuilDir = BuilDir;
	}


	def ArgToString(Tuple tuple) {
		var arg = new ArrayList<String>();
		for(var i = 0; i < tuple.size; i++) {
			if(i == tuple.size - 1) {
				arg.add(tuple.getValue(i).toString());
			}
			
			else {
				arg.add(tuple.getValue(i).toString()+',');
			}
		}
		
		arg
	}
	
	def GenerateJava(StormComponent component) {
		val p = component.kernel.listParameters();
		var param = new ArrayList<String>();
		for(var i = 0; i < p.size; i++) {
			param.add(component.paramTypes.get(i) + " " +  p.get(i).name);
		}
		
		var argToC = new ArrayList<String>();
		for(var i = 0; i < p.size; i++) {
			argToC.add("(" + component.paramTypes.get(i) + ") " +
				"tuple.get(" + i + ")");
		}
		'''
			package «packageName»;

			import java.util.Map;
			import org.apache.storm.tuple.Fields;
			import org.apache.storm.tuple.Values;
			import org.apache.storm.task.OutputCollector;
			import org.apache.storm.task.TopologyContext;import org.apache.storm.topology.IRichBolt;
			import org.apache.storm.topology.OutputFieldsDeclarer;
			import org.apache.storm.tuple.Tuple;

			class «component.kernelName.toFirstUpper()»Bolt implements IRichBolt {
				public OutputCollector collector;

				public native « component.returnType» «component.kernelName»(«FOR arg : param SEPARATOR ',' »«arg»«ENDFOR»);

				static {
					System.loadLibrary("«component.kernelName»");
				}

				public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
					this.collector = collector;
				}

				public void execute(Tuple tuple) {
					collector.emit(«component.kernelName»(«FOR arg : argToC SEPARATOR ',' »«arg»«ENDFOR»));
				}

				public void cleanup() {

				}

				public void declareOutputFiels(OutputDeclarer declarer) {

				}
			}
		'''
	}

	def Execute(StormComponent component) {
		//System.out.println("Lancement de la génération")
		val JavaGenerated = GenerateJava(component)
		val file = new File( component.kernelName.toFirstUpper() + "Bolt.java")
		val FileWriter = new BufferedOutputStream(new FileOutputStream(file))
		FileWriter.write(JavaGenerated.toString().bytes)
		FileWriter.flush()
		//println("Chemin du fichier : " + file.absolutePath)
	}
}
