generateFiles: clean
	./scripts/generateFiles.sh

clean:
	find runtime/ -name "*.tmp"  | xargs rm -f
