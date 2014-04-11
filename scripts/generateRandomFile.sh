#!/bin/bash
set -e

[[ $# -eq 3 ]] || {
	echo "Usage : ./generateRandomFile.sh userName fileName size(kb)"
	exit 1
}

SCRIPT_PATH="`dirname \"$0\"`"
SCRIPT_PATH="`(cd "$SCRIPT_PATH" && pwd)`"
ROOT=$SCRIPT_PATH/../

dd if=/dev/urandom of=$ROOT/runtime/$1/$2.tmp bs=1024 count=$3
