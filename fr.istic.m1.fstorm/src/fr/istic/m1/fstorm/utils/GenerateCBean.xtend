package fr.istic.m1.fstorm.utils

import fr.istic.m1.fstorm.beans.CBean
import fr.istic.m1.fstorm.beans.CBeanAttribute
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import fr.istic.m1.fstorm.jni.JNIType
import fr.istic.m1.fstorm.jni.WrapperEnvironment

class GenerateCBean {
	private String odir;
	private String packageName;
	
	new (String pn, String odir) {
		this.packageName = pn;
		this.odir = odir;
	}

	def GenerateJava(CBean cbean)
		'''	
		package «packageName»;
		
		class «cbean.name» {
			«FOR CBeanAttribute attr : cbean.attributes»
			private «javaType(attr.typeName)» «attr.name»;

			public «javaType(attr.typeName)» get«attr.name.toFirstUpper»() {
				return this.«attr.name»;
			}
			
			public void set«attr.name.toFirstUpper»(«javaType(attr.typeName)» «attr.name») {
				this.«attr.name» = «attr.name»;
			}
			«ENDFOR»
		}
		'''
	
	def javaType(String s) {
		switch s {
			case "char": "byte"
			case "void"
			,case "short"
			,case "int"
			,case "long"
			,case "float"
			,case "double": s
			case "*(char)": "String"
			default:
				if (WrapperEnvironment.getBeanScope().getBean(s) != null) {
					s
				} else if (s.charAt(0) == '*') {
					javaType(s.substring(2, s.length()-1))+"[]"
				} else if (s.substring(0,2).equals("[]")) {
					javaType(s.substring(3, s.length()-1))+"[]"
				} else {
					null
				}
		}
	}
	
	def Execute(CBean cmp) {
		val java = GenerateJava(cmp)
		if(!Files.exists(Paths.get(odir)))
			Files.createDirectory(Paths.get(odir))
		Files.deleteIfExists(
			Paths.get(odir, cmp.name + ".java")
		)
		Files.write(
			Paths.get(odir, cmp.name + ".java"),
			java.toString().bytes, StandardOpenOption.CREATE
		)
	} 
	
}
