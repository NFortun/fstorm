debug(1);

name = "readcomponents";
proj = CreateGecosProject(name);
# params = ReadCommandLineOptions();
AddSourceToGecosProject(proj, "./test.c");
CDTFrontend(proj);
pragmas = FindAllPragmas(proj);
list=ReadComponents(pragmas);

for comp in list do
	GenerateNeededBeans(comp, "fr.fstorm.exemple", "./exemple");
	GenerateWrapper(comp, "fr.fstorm.exemple");
done;

GenerateJavaClass(list, "./exemple", "fr.fstorm.exemple", "exemple");
WriteFinalCFile("./test.c", "libexemple.c", list, "./exemple");