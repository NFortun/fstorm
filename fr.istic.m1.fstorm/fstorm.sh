ARGS=$*
ECLIPSE_DIR="$HOME/eclipse"
WORKSPACE="$HOME/workspace"

echo "`java -Declipse.pde.launch=true -Dfile.encoding=UTF-8 -classpath ${ECLIPSE_DIR}/plugins/org.eclipse.equinox.launcher_1.3.201.v20161025-1711.jar org.eclipse.equinox.launcher.Main -launcher ${ECLIPSE_DIR}/eclipse -name Eclipse -showsplash 600 -application fr.istic.m1.fstorm.fstorm -data ./runtime-Fstormapplication -configuration "file:${WORKSPACE}/.metadata/.plugins/org.eclipse.pde.core/FStorm application/" -dev "file:${WORKSPACE}/.metadata/.plugins/org.eclipse.pde.core/FStorm application/dev.properties" -os linux -ws gtk -arch x86_64 -nl fr_FR ${ARGS}`"
