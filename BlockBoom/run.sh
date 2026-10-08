#!/bin/sh
# Compile and run Block Boom from the project root (needs JDK 8+)
mkdir -p out
javac -encoding UTF-8 -d out src/Main.java src/model/*.java src/view/*.java src/view/components/*.java src/data/*.java && java -cp out Main
