@echo off
echo Setting JAVA_HOME
set JAVA_HOME=C:\FT\sdkroot\candidates\java\zulu11.0.6
echo setting PATH
set PATH=%JAVA_HOME%\bin;%PATH%
echo Display java version
java -version

set MEDIATOR=../mediator/target/mediator.jar
set SCALNODE=../scalable-node/target/scalable-node.jar


start java -jar %MEDIATOR%  --server.port=12345
start java -jar  %SCALNODE%  --server.port=13001  --ranges.source.location=localhost:12345/range
start java -jar %SCALNODE%  --server.port=13002  --ranges.source.location=localhost:12345/range

pause
