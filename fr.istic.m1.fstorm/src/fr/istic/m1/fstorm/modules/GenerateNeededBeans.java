package fr.istic.m1.fstorm.modules;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m1.fstorm.beans.CBean;
import fr.istic.m1.fstorm.beans.CBeanAttribute;
import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.jni.WE;
import fr.istic.m1.fstorm.utils.GenerateCBean;
import gecos.types.AliasType;
import gecos.types.ArrayType;
import gecos.types.Field;
import gecos.types.PtrType;
import gecos.types.RecordType;
import gecos.types.Type;

public class GenerateNeededBeans {

	private StormComponent comp;
	private String packageName;
	private String BuildDir;

	public GenerateNeededBeans (StormComponent comp, String packageName, String BuildDir) {
		this.comp = comp;
		this.packageName = packageName;
		this.BuildDir = BuildDir;
	}
	
	public void compute() {
		List<CBean> beans = generateBeans();
		
		GenerateCBean cbg = new GenerateCBean(packageName, BuildDir);
		for (CBean cb : beans) {
			cbg.Execute(cb);
		}
	}

	public List<CBean> generateBeans() {
		List<CBean> ret = new ArrayList<CBean>();
			
		CBean ret_cb = getCBeanFromType(comp.getKernel().getSymbol().getType().asFunction().getReturnType());
		if (ret_cb != null) {
			ret.add(ret_cb);
		}
		
		
		for (Type t : comp.getKernel().getSymbol().getType().asFunction().getParameters()) {
			CBean cb;
			if ((cb = getCBeanFromType(t)) != null) {
				ret.add(cb);
			}
		}

		//System.out.println((new WrapperGenerator()).generate(c));
		
		for (CBean cb : ret) {
			//System.out.println(CBeanGenerator.generate(cb));
			WE.getBeanScope().addBean(cb);
		}

		return ret;
	}
	
	public CBean getCBeanFromType(Type t) {
		CBean cb = null;
		if (t instanceof RecordType) {
			cb = new CBean();
			cb.setName(t.asRecord().getName());
			
			ArrayList<CBeanAttribute> l = new ArrayList<>();
			for (Field f : t.asRecord().getFields()) {
				CBeanAttribute a = new CBeanAttribute();
				a.setName(f.getName());
				a.setTypeName(f.getType().toString());
				l.add(a);
			}
			
			cb.setAttributes(l);

		} else if (t instanceof AliasType) {
			cb = getCBeanFromType(t.asAlias().getAlias());
		} else if (t instanceof ArrayType) {
			cb = getCBeanFromType(t.asArray().getBase());
		} else if (t instanceof PtrType) {
			cb = getCBeanFromType(t.asPointer().getBase());
		}
		
		return cb;
	}
}