package fr.istic.m1.fstorm.modules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import fr.istic.m1.fstorm.beans.StormComponent;

/**
 * WriteFinalCFile is a sample module. When the script evaluator encounters
 * the 'WriteFinalCFile' function, it calls the compute method.
 */
public class WriteFinalCFile {
	
	private String filename;
	private List<StormComponent> comps;
	private String odir;

	public WriteFinalCFile (String filename, List<StormComponent> comps, String odir) {
		this.filename = filename;
		this.comps = comps;
		this.odir = odir;
	}
	
	public void compute() {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(filename)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!Files.exists(Paths.get(odir)))
			try {
				Files.createDirectory(Paths.get(odir));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		try {
			Files.deleteIfExists(
				Paths.get(odir, Paths.get(filename).getFileName().toString())
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (StormComponent comp : comps) {
			content += "\n" + comp.getWrapper();
		}
		
		try {
			Files.write(
					Paths.get(odir, Paths.get(filename).getFileName().toString()),
					content.getBytes(), StandardOpenOption.CREATE
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}