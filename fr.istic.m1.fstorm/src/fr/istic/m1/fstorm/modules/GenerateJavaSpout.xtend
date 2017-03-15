package fr.istic.m1.fstorm.modules

import fr.istic.m1.fstorm.beans.StormComponent
import gecos.core.ParameterSymbol
import java.util.List

class GenerateJavaSpout {
	private String packageName;

	def GenerateJava(StormComponent component) '''
		package «packageName»
		
		import org.apache.storm.tuple.Fields;
		import org.apache.storm.tuple.Values;		
		import org.apache.storm.topology.IRichSpout;
		import org.apache.storm.topology.OutputFieldsDeclarer;		
		import org.apache.storm.spout.SpoutOutputCollector;
		import org.apache.storm.task.TopologyContext;
		import java.util.Map;
		
		class «component.kernelName.toFirstUpper()» implements IRichSpout {
			private SpoutOutputCollector collector;
			private boolean completed = false;
			
			private TopologyContext context;
		  
		  	@Override
		  	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		    	this.context = context;
		    	this.collector = collector;
		  	}
		  
		    @Override
		    	public void nextTuple() {
		      	collector.emit(«component.wrapper.symbol.name»());
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
		
		static { System.loadLibrary(«component.libraryName»); }		
	'''
}
