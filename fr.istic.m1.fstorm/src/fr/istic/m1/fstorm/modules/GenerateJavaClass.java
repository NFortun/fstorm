package fr.istic.m1.fstorm.modules;

import java.util.List;

import fr.istic.m1.fstorm.beans.FStormParameters;
import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.beans.StormComponentType;
import fr.istic.m1.fstorm.utils.GenerateJavaBolt;
import fr.istic.m1.fstorm.utils.GenerateJavaSpout;

public class GenerateJavaClass {
	private FStormParameters appParam;
	private List<StormComponent> comps;
	private String lib;
	private String odir;
	private String pack;
	
	public GenerateJavaClass(FStormParameters params, List<StormComponent> comps, String odir, String pack, String lib) {
		this.appParam=params;
		this.comps = comps;
		this.lib = lib;
		this.odir = odir;
		this.pack = pack;
	}
	
	public void compute() {
		GenerateJavaSpout genSpout = new GenerateJavaSpout(pack, odir, lib);
		GenerateJavaBolt genBolt = new GenerateJavaBolt(pack, odir, lib);
		
		for(StormComponent comp : comps) {
			if(comp.getNodeType() == StormComponentType.SPOUT) {
				genSpout.Execute(comp);
			}
			
			else {
				genBolt.Execute(comp);
			}
			System.out.println("File generated for "+comp.getKernelName());
		}
	}
}
