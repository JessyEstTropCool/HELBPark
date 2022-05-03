@echo off

echo Compiling...
start /WAIT /B javac --module-path lib/ --add-modules=ALL-MODULE-PATH -Xdiags:verbose -encoding utf-8 *.java
if %errorlevel% equ 0 ( 
    echo Starting...
    start /WAIT /B java --module-path lib/ --add-modules=ALL-MODULE-PATH %1
) else (
    echo Error code %errorlevel%
    pause
)

echo Deleting classes...
del *.class
echo Done