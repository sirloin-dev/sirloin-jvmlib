@IF "%DEBUG%" == "" @ECHO OFF

ECHO Publishing sirloin-jvmlib-annotation...
cmd /c gradlew.bat :sirloin-jvmlib-annotation:publishToMavenLocal

ECHO Publishing sirloin-jvmlib-crypto...
cmd /c gradlew.bat :sirloin-jvmlib-crypto:publishToMavenLocal

ECHO Publishing sirloin-jvmlib-net...
cmd /c gradlew.bat :sirloin-jvmlib-net:publishToMavenLocal

ECHO Publishing sirloin-jvmlib-text...
cmd /c gradlew.bat :sirloin-jvmlib-text:publishToMavenLocal

ECHO Publishing sirloin-jvmlib-time...
cmd /c gradlew.bat :sirloin-jvmlib-time:publishToMavenLocal

ECHO Publishing sirloin-jvmlib-util...
cmd /c gradlew.bat :sirloin-jvmlib-util:publishToMavenLocal

ECHO Publishing sirloin-jvmlib-test...
cmd /c gradlew.bat :sirloin-jvmlib-test:publishToMavenLocal