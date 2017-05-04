package fr.istic.m1.fstorm.modules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import fr.istic.m1.fstorm.beans.StormComponent;

public class WriteFinalCFile {

	private String in_filename;
	private String out_filename;
	private List<StormComponent> comps;
	private String odir;

	public WriteFinalCFile (String in_filename, String out_filename, List<StormComponent> comps, String odir) {
		this.in_filename = in_filename;
		this.out_filename = out_filename;
		this.comps = comps;
		this.odir = odir;
	}
	
	public void compute() {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(in_filename)));
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
				Paths.get(odir, Paths.get(out_filename).getFileName().toString())
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
					Paths.get(odir, Paths.get(out_filename).getFileName().toString()),
					content.getBytes(), StandardOpenOption.CREATE
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}