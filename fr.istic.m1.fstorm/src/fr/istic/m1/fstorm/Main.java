package fr.istic.m1.fstorm;

import java.util.List;
import java.util.Map;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import fr.irisa.cairn.gecos.model.cdtfrontend.CDTFrontEnd;
import fr.irisa.cairn.gecos.model.factory.GecosUserCoreFactory;
import fr.istic.m1.fstorm.beans.FStormParameters;
import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.modules.FindAllPragmas;
import fr.istic.m1.fstorm.modules.GenerateJavaClass;
import fr.istic.m1.fstorm.modules.GenerateNeededBeans;
import fr.istic.m1.fstorm.modules.GenerateWrapper;
import fr.istic.m1.fstorm.modules.ReadCommandLineOptions;
import fr.istic.m1.fstorm.modules.ReadComponents;
import gecos.annotations.PragmaAnnotation;
import gecos.gecosproject.GecosProject;

public class Main implements IApplication {
	@Override
	public Object start(IApplicationContext ctx) throws Exception {
		FStormParameters params;
		Map<?,?> argMap = ctx.getArguments();
		String[] args = (String[]) argMap.get("application.args");
		
		try {
			params = (new ReadCommandLineOptions(args).compute());
			
			if(params.isVersion()) {
				System.out.println("fstorm 1.0\nCode generator F-Storm by FPGA-Storm team at Istic.");
				return IApplication.EXIT_OK;
			}
			
			else if(params.getInputFile() == null)
				return IApplication.EXIT_OK;
			
			String filename = params.getInputFile();
			GecosProject proj = GecosUserCoreFactory.project("fstorm", filename);
			CDTFrontEnd cdt = new CDTFrontEnd(proj);
			cdt.compute();
			List<PragmaAnnotation> pragmas = (new FindAllPragmas(proj)).compute();
			List<StormComponent> comps = (new ReadComponents(params, pragmas)).compute();
			(new GenerateJavaClass(params, comps,
					params.getOdir(),
					params.getPack(),
					filename.substring(0, filename.lastIndexOf('.'))))
			.compute();
			
			for(StormComponent comp : comps) {
				(new GenerateNeededBeans(params, comp, params.getPack(), params.getOdir())).compute();
				(new GenerateWrapper(comp, params.getPack())).compute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {		
	}
}
