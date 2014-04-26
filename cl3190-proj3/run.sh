#!/bin/bash

chmod 777 run.sh
javac *.java
java RuleGen $1 $2 $3 
