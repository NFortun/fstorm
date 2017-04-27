package whatever.test;

import java.util.Map;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

class IdBolt implements IRichBolt {
	public OutputCollector collector;

	public native Integer id(Integer);

	static {
		System.loadLibrary("test");
	}

	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple tuple) {
		collector.emit(id((Integer) tuple.get(0)));
	}

	public void cleanup() {

	}

	public void declareOutputFiels(OutputDeclarer declarer) {

	}
}
