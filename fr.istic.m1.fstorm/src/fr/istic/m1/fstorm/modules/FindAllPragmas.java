package fr.istic.m1.fstorm.modules;

import java.util.List;

import fr.irisa.cairn.tools.ecore.query.EMFUtils;
import gecos.annotations.PragmaAnnotation;
import gecos.gecosproject.GecosProject;

/**
 * FindAllPragmas is a sample module. When the script evaluator encounters the
 * 'FindAllPragmas' function, it calls the compute method.
 */
public class FindAllPragmas {

	private GecosProject proj;

	public FindAllPragmas(GecosProject proj) {
		this.proj = proj;
	}

	public List<PragmaAnnotation> compute() {
		return EMFUtils.eAllContentsInstancesOf(proj, PragmaAnnotation.class);
	}
}