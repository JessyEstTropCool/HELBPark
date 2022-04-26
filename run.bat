@echo off

echo Compiling...
start /WAIT /B javac -Xdiags:verbose -encoding utf-8 *.java
if %errorlevel% equ 0 ( 
    echo Starting...
    start /WAIT /B java Form
) else (
    echo Error code %errorlevel%
    pause
)

del *.class
pause

::javac --module-path javafx/lib/ --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web HELBPark.java
::java --module-path javafx/lib/ --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web HELBPark.java