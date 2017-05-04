package fr.fstorm.exemple;

import java.util.Map;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

class SumBolt implements IRichBolt {
	public OutputCollector collector;

	public native Float sum(Float[]);

	static {
		System.loadLibrary("exemple");
	}

	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple tuple) {
		Float ret = sum((Float[]) tuple.get(0));
	
		collector.emit(new Values(ret));
	}

	public void cleanup() {

	}

	public void declareOutputFiels(OutputDeclarer declarer) {

	}
}
