@echo off
cd C:\Dev\Java\Delegate\
echo Building module:delegate-api
call mvn clean install -pl delegate-api -B

echo Building module:delegate-core
call mvn clean install -pl delegate-core -B

echo Building module:delegate-bukkit
call mvn clean install -pl delegate-bukkit -B
echo Done