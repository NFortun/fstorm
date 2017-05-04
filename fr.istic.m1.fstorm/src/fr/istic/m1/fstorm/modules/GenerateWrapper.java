package fr.istic.m1.fstorm.modules;

import java.util.ArrayList;
import java.util.List;

import gecos.types.AliasType;
import gecos.types.ArrayType;
import gecos.types.PtrType;
import gecos.types.RecordType;
import gecos.types.Type;
import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.beans.StormComponentType;
import fr.istic.m1.fstorm.jni.*;

public class GenerateWrapper {
	
	private StormComponent _inArg;
	private String _pkg;
	
	public GenerateWrapper (StormComponent inArg, String pkg) {
		_inArg = inArg;
		_pkg = pkg;
	}
	
	public void compute() {
		WrapperEnvironment.Init();
		
		if (_inArg.getNodeType() == StormComponentType.SPOUT) {
			JType jret_type = JNIType.javaFromString(_inArg.getReturnType());
			StringBuffer buffer_final = new StringBuffer();
			String className = _pkg.replaceAll("\\.", "_") + "_"
					+ _inArg.getKernelName().substring(0, 1).toUpperCase()
					+ _inArg.getKernelName().substring(1, _inArg.getKernelName().length())
					+ "Spout";
			
			// prototype
			buffer_final.append("JNIEXPORT "+jret_type.getJniType()+" JNICALL Java_"+className+"_"+_inArg.getKernelName()+"(");
			buffer_final.append("JNIEnv* "+WrapperEnvironment.getScope().getEnvironment()+", jclass "+WrapperEnvironment.getScope().getCallingClass());
			buffer_final.append(") {\n");
			// corps de la fonction
			CType ret_type = JNIType.cFromString(getTypeName(_inArg.getKernel().getSymbol().getType().asFunction().getReturnType()));
			Variable ret = new Variable(ret_type, _inArg.getKernelName()+"()");
			Variable jret = ret.toJava(jret_type);
			WrapperEnvironment.getBuffer().append("return "+jret.getName()+";\n");
			
			// ecriture du corps et fermeture de la fonction
			buffer_final.append(WrapperEnvironment.getBuffer().toString());
			buffer_final.append("\n}\n");
			
			System.out.println(buffer_final.toString());
			_inArg.setWrapper(buffer_final.toString());
		} else {
			String className = _pkg.replaceAll("\\.", "_") + "_"
					+ _inArg.getKernelName().substring(0, 1).toUpperCase()
					+ _inArg.getKernelName().substring(1, _inArg.getKernelName().length())
					+ "Bolt";
			
			StringBuffer buffer_final = new StringBuffer();

			List<Variable> jparams = new ArrayList<>();
			List<CType> cparam_types = new ArrayList<>();
			List<Variable> cparams = new ArrayList<>();
			
			// type de retour du wrapper
			JType jret_type = JNIType.javaFromString(_inArg.getReturnType());
			
			if (jret_type == null) {
				System.out.println(_inArg.getReturnType()+" is not a valid type");
				System.out.println(WrapperEnvironment.getBeanScope().getAllBeans());
			}
			// prototype
			buffer_final.append("JNIEXPORT "+jret_type.getJniType()+" JNICALL Java_"+className+"_"+_inArg.getKernelName()+"(");
			buffer_final.append("JNIEnv* "+WrapperEnvironment.getScope().getEnvironment()+", jclass "+WrapperEnvironment.getScope().getCallingClass());
			
			// ecriture des parametres
			for (String t : _inArg.getParamTypes()) {
				Variable n = new Variable(JNIType.javaFromString(t), null, true);
				jparams.add(n);
				buffer_final.append(", "+n.getJType().getJniType()+" "+n.getName());
			}
			buffer_final.append(") {\n");

			for (Type t : _inArg.getKernel().getSymbol().getType().asFunction().getParameters()) {
				cparam_types.add(JNIType.cFromString(getTypeName(t)));
			}

			// conversion des parametres
			int offset = 0;
			for (int i = 0; i < jparams.size(); ++i) {
				int id = i+offset;
				cparams.addAll(jparams.get(i).toKernel(cparam_types.get(id)));
				if (jparams.get(i).getJType() instanceof JArray) {
					++offset;
				}
			}
			
			CType ret_type = JNIType.cFromString(getTypeName(_inArg.getKernel().getSymbol().getType().asFunction().getReturnType()));

			// string de l'appel du kernel
			String str = "";
			str += _inArg.getKernelName()+"("; 
			for (int i = 0; i < cparams.size(); ++i) {
				str += (i != 0 ? ", ":"")+cparams.get(i).getName();
			}
			str += ")";
			
			// appel du kernel, et récupération du retour si non void
			Variable ret = null, jret = null;
			if (!(ret_type instanceof CVoid) && ret_type != null) {
				// conversion du retour
				ret = new Variable(ret_type, str);
				jret = ret.toJava(jret_type);
			} else {
				WrapperEnvironment.getBuffer().append(str);
			}
			
			// release des eventuels tableaux/strings
			offset = 0;
			for (int i = 0; i < jparams.size(); ++i) {
				int id = i+offset;
				
				JType ptype = jparams.get(i).getJType();
				if (ptype instanceof JNIReleaseable) {
					((JNIReleaseable) ptype).release(jparams.get(i).getName(), cparams.get(id).getName());
				}

				if (jparams.get(i).getJType() instanceof JArray) {
					++offset;
				}
			}

			// return si non void bolt
			if (!(ret_type instanceof CVoid) && ret_type != null) {
				WrapperEnvironment.getBuffer().append("return "+jret.getName()+";\n");
			}
			
			// fin de wrapper
			buffer_final.append(WrapperEnvironment.getBuffer().toString());
			buffer_final.append("\n}\n");
			
			System.out.println(buffer_final.toString());
			_inArg.setWrapper(buffer_final.toString());
		}
	}
	
	// retourne le type d'un gecos.Type sous forme de String
	public String getTypeName(Type t) {
		String cb = null;
		if (t instanceof RecordType) {
			cb = t.asRecord().getName(); 
		} else if (t instanceof AliasType) {
			cb = t.asAlias().getName();
		} else if (t instanceof ArrayType) {
			cb = "[]("+getTypeName(t.asArray().getBase())+")";
		} else if (t instanceof PtrType) {
			cb = "*("+getTypeName(t.asPointer().getBase())+")";
		} else {
			cb = t.toString();
		}
		
		return cb;
	}
}