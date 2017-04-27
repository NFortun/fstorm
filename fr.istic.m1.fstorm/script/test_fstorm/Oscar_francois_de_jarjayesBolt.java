package whatever.test;

import java.util.Map;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

class Oscar_francois_de_jarjayesBolt implements IRichBolt {
	public OutputCollector collector;

	public native void oscar_francois_de_jarjayes(Integer);

	static {
		System.loadLibrary("test");
	}

	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple tuple) {
		collector.emit(oscar_francois_de_jarjayes((Integer) tuple.get(0)));
	}

	public void cleanup() {

	}

	public void declareOutputFiels(OutputDeclarer declarer) {

	}
}
