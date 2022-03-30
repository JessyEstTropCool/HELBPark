#!/bin/bash

javac *.java
java HELBPark
find . -type f -name "*.class" -delete
