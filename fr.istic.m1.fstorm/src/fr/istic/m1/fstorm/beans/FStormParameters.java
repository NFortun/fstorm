package fr.istic.m1.fstorm.beans;

public class FStormParameters {
	private String inputFile;
	private boolean makefile;
	private String odir;
	private String pack;
	private boolean verbose;
	private boolean version;
	
	public String getInputFile() {
		return inputFile;
	}
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	public boolean isMakefile() {
		return makefile;
	}
	public void setMakefile(boolean makefile) {
		this.makefile = makefile;
	}
	public String getOdir() {
		return odir;
	}
	public void setOdir(String odir) {
		this.odir = odir;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public boolean isVerbose() {
		return verbose;
	}
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	public boolean isVersion() {
		return version;
	}
	public void setVersion(boolean version) {
		this.version = version;
	}
}
