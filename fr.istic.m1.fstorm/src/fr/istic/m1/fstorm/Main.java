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
			
			if(params.getInputFile() == null)
				return IApplication.EXIT_OK;
			
			GecosProject proj = GecosUserCoreFactory.project("fstorm", params.getInputFile());
			CDTFrontEnd cdt = new CDTFrontEnd(proj);
			cdt.compute();
			List<PragmaAnnotation> pragmas = (new FindAllPragmas(proj)).compute();
			List<StormComponent> comps = (new ReadComponents(pragmas)).compute();
			(new GenerateJavaClass(comps, params.getOdir(), params.getPack(), "test")).compute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {		
	}
}
