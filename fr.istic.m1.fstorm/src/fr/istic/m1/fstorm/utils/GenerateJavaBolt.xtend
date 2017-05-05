package fr.istic.m1.fstorm.utils

import fr.istic.m1.fstorm.beans.StormComponent
import java.util.ArrayList
import java.io.File
import java.io.FileOutputStream
import java.io.BufferedOutputStream
import backtype.storm.tuple.Tuple
import java.nio.file.Files
import java.nio.file.Paths
import fr.istic.m1.fstorm.jni.WrapperEnvironment

class GenerateJavaBolt {
	String packageName;
	String BuildDir;
	String libName;
	
	new(String packageName, String BuildDir, String libName) {
		this.packageName = packageName;
		this.BuildDir = BuildDir;
		this.libName = libName;
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
		// val p = component.kernel.listParameters();
		var param = new ArrayList<String>();
		for(var i = 0; i < component.paramTypes.size; i++) {
			param.add(component.paramTypes.get(i)/*+ " " +  p.get(i).name*/); // pas besoin du nom de la variable
		}
		
		var argToC = new ArrayList<String>();
		for(var i = 0; i < component.paramTypes.size; i++) {
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
					System.loadLibrary("«libName»");
				}

				public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
					this.collector = collector;
				}

				public void execute(Tuple tuple) {
					«component.returnType» ret = «component.kernelName»(«FOR arg : argToC SEPARATOR ',' »«arg»«ENDFOR»);
			
					«IF component.isFlat && WrapperEnvironment.beanScope.getBean(component.returnType) !== null»
					collector.emit(new Values(
					«FOR attr : WrapperEnvironment.beanScope.getBean(component.returnType).attributes SEPARATOR ','»
					ret.get«attr.name.toFirstUpper»()
					«ENDFOR»
					));
					«ELSE»
					collector.emit(new Values(ret));
				  	«ENDIF»
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
		if(!Files.exists(Paths.get(BuildDir)))
			Files.createDirectory(Paths.get(BuildDir))
		val file = new File( BuildDir + "/" + component.kernelName.toFirstUpper() + "Bolt.java")
		val FileWriter = new BufferedOutputStream(new FileOutputStream(file))
		FileWriter.write(JavaGenerated.toString().bytes)
		FileWriter.flush()
		//println("Chemin du fichier : " + file.absolutePath)
	}
}
