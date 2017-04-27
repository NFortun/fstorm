package fr.istic.m1.fstorm.modules;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import fr.istic.m1.fstorm.beans.FStormParameters;

public class ReadCommandLineOptions {
	private String[] args;
	
	public ReadCommandLineOptions(String[] args) {
		this.args = args;
	}
	
	public FStormParameters compute() throws ParseException {
		FStormParameters params = new FStormParameters();
		Options options = new Options();

		options.addOption("M", "makefile", false, "generate a Makefile");
		options.addOption("o", "odir", true, "set the output directory");
		options.addOption("p", "package", true, "set the Java package");
		options.addOption("v", "verbose", false, "display debug informations");
		options.addOption("V", "version", false, "display fstorm's version");
		
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);
		
		params.setMakefile(cmd.hasOption("M"));
		params.setVerbose(cmd.hasOption("v"));
		params.setVersion(cmd.hasOption("V"));
		
		if(cmd.hasOption("o"))
			params.setOdir(cmd.getOptionValue("o"));
		else
			params.setOdir(".");
		
		if(cmd.hasOption("p"))
			params.setPack(cmd.getOptionValue("p"));
		else
			params.setPack("");
		
		String[] leftOverArgs = cmd.getArgs();
		if(leftOverArgs.length == 1)
			params.setInputFile(leftOverArgs[0]);
		else {
			HelpFormatter help = new HelpFormatter();
			help.printHelp("fstorm.sh", options);
		}
		
		return params;
	}
}
