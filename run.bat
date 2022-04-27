@echo off

echo Compiling...
start /WAIT /B javac --module-path lib/ --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web -Xdiags:verbose -encoding utf-8 *.java
if %errorlevel% equ 0 ( 
    echo Starting...
    start /WAIT /B java --module-path lib/ --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web HELBPark
) else (
    echo Error code %errorlevel%
    pause
)

del *.class

::javac --module-path libs/ --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web *.java
::java --module-path libs/ --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web Form