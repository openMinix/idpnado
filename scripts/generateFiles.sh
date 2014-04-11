#!/bin/bash
set -e

[[ $# -eq 0 ]] || {
	echo "Usage : ./generateFiles.sh"
	exit 1
}

SCRIPT_PATH="`dirname \"$0\"`"
SCRIPT_PATH="`(cd "$SCRIPT_PATH" && pwd)`"
ROOT=$SCRIPT_PATH/../

for i in $(seq 1 20)
do
	${SCRIPT_PATH}/generateRandomFile.sh "user1" $i `expr $i \* 100`
done

for i in $(seq 21 40)
do
	${SCRIPT_PATH}/generateRandomFile.sh "user2" $i `expr $i \* 100`
done

for i in $(seq 41 60)
do
	${SCRIPT_PATH}/generateRandomFile.sh "user1" $i `expr $i \* 100`
done
