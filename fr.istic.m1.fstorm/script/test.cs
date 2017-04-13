debug(1);

name = "readcomponents";
proj = CreateGecosProject(name);
# params = ReadCommandLineOptions();
AddSourceToGecosProject(proj, "./test.c");
CDTFrontend(proj);
pragmas = FindAllPragmas(proj);
list=ReadComponents(pragmas);
GenerateJavaClass(list, "./test_fstorm", "whatever.test", "test");
