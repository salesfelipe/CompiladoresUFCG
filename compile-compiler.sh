#!/bin/sh


echo "Compilando o JFLEX: "
echo " "
java -jar libs/jflex-1.6.1.jar --noinputstreamctor -d ./src/compiler/generated ./spec/lexical.flex
echo " "
echo "Compilando o CUP: "
echo " "
java -jar libs/java-cup-11a.jar -compact_red -expect 10000 -package compiler.generated -destdir ./src/compiler/generated -parser Parser ./spec/parser.cup