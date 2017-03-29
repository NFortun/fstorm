package fr.istic.m1.fstorm.modules

import fr.istic.m1.fstorm.beans.StormComponent
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.ArrayList

class GenerateJavaSpout {
	private String packageName;
	
	new (String pn) {
		this.packageName = pn;
	}

	def GenerateJava(StormComponent component) { 
		val kparams = component.kernel.listParameters();
		var params = new ArrayList<String>();
		for (var i = 0; i < kparams.size(); i++)
			params.add(component.paramTypes.get(i) + ' ' + kparams.get(i).name)
			
		var argsToC = new ArrayList<String>();
		for (var i = 0; i < kparams.size(); i++)
			argsToC.add('(' + component.paramTypes.get(i) + ')' + " tuple.get(" + i + ')')		
		
		'''	
		package «packageName»;
		
		import org.apache.storm.tuple.Fields;
		import org.apache.storm.tuple.Values;		
		import org.apache.storm.topology.IRichSpout;
		import org.apache.storm.topology.OutputFieldsDeclarer;		
		import org.apache.storm.spout.SpoutOutputCollector;
		import org.apache.storm.task.TopologyContext;
		import java.util.Map;
		
		
		
		class «component.kernelName.toFirstUpper()»_Spout implements IRichSpout {
			private SpoutOutputCollector collector;
			private boolean completed = false;
			
			private TopologyContext context;
			
			public native «component.returnType» «component.kernelName»(«FOR arg : params SEPARATOR ',' »«arg»«ENDFOR»);
		  
		  	@Override
		  	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		    	this.context = context;
		    	this.collector = collector;
		  	}
		  
		    @Override
		    	public void nextTuple() {
		      	collector.emit(«component.kernelName»(«FOR arg : params SEPARATOR ',' »«arg»«ENDFOR»));
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
		}
		
		static { System.loadLibrary(«component.kernelName»); }		
	'''
	}
	
	def Execute(StormComponent cmp) {
		val java = GenerateJava(cmp) 
		Files.write(Paths.get(cmp.kernelName + "_Spout.java"), java.toString().bytes, StandardOpenOption.CREATE)
	} 
	
}
