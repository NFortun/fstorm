package whatever.test;

import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import java.util.Map;

class Lady_oscarSpout implements IRichSpout {
	private SpoutOutputCollector collector;
	private boolean completed = false;

	private TopologyContext context;

	public native Integer lady_oscar();

	@Override
  	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.context = context;
		this.collector = collector;
	}

	@Override
		public void nextTuple() {
	  	collector.emit(lady_oscar());
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
	
	static { System.loadLibrary("test"); }
}
