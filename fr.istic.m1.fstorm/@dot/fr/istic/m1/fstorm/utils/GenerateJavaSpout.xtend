package fr.istic.m1.fstorm.utils

import fr.istic.m1.fstorm.beans.StormComponent
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class GenerateJavaSpout {
	private String odir;
	private String packageName;
	private String libName;
	
	new (String pn, String odir, String libName) {
		this.packageName = pn;
		this.odir = odir;
		this.libName = libName;
	}

	def GenerateJava(StormComponent component)
		'''	
		package «packageName»;
		
		import org.apache.storm.tuple.Fields;
		import org.apache.storm.tuple.Values;
		import org.apache.storm.topology.IRichSpout;
		import org.apache.storm.topology.OutputFieldsDeclarer;
		import org.apache.storm.spout.SpoutOutputCollector;
		import org.apache.storm.task.TopologyContext;
		import java.util.Map;
		
		class «component.kernelName.toFirstUpper()»Spout implements IRichSpout {
			private SpoutOutputCollector collector;
			private boolean completed = false;

			private TopologyContext context;

			public native «component.returnType» «component.kernelName»();

			@Override
		  	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
				this.context = context;
				this.collector = collector;
			}

			@Override
				public void nextTuple() {
			  	collector.emit(«component.kernelName»());
			}

			@Override
			public void declareOutputFields(OutputFieldsDeclarer declarer) {
			}

			@Override
			public void close() {
			}

			public boolean isDistributed() {
			  	return false;
			}

			@Override
			public void activate() {
			}

			@Override
			public void deactivate() {
			}

			@Override
			public void ack(Object msgId) {
			}

			@Override
			public void fail(Object msgId) {
			}

			@Override
			public Map<String, Object> getComponentConfiguration() {
			 	return null;
			}
			
			static { System.loadLibrary("«libName»"); }
		}
		'''
	
	def Execute(StormComponent cmp) {
		val java = GenerateJava(cmp)
		if(!Files.exists(Paths.get(odir)))
			Files.createDirectory(Paths.get(odir))
		Files.deleteIfExists(
			Paths.get(odir, cmp.kernelName.toFirstUpper() + "Spout.java")
		)
		Files.write(
			Paths.get(odir, cmp.kernelName.toFirstUpper() + "Spout.java"),
			java.toString().bytes, StandardOpenOption.CREATE
		)
	} 
	
}
