package fr.istic.m1.fstorm.utils

import fr.istic.m1.fstorm.beans.StormComponent
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import fr.istic.m1.fstorm.beans.CBean
import fr.istic.m1.fstorm.beans.CBeanAttribute

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
			private «attr.typeName» «attr.name»;
			
			public «attr.typeName» get«attr.name.toFirstUpper»() {
				return this.«attr.name»;
			}
			
			public void set«attr.name.toFirstUpper»(«attr.typeName» «attr.name») {
				this.«attr.name» = «attr.name»;
			}
			«ENDFOR»
		}
		'''
	
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
