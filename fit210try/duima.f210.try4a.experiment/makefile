

all:
	@echo make bld
	@echo make cln

ts:
	gradle generateTypeSystem

bld: ts
	gradle build
	cp src/main/typesystem/*xml build/generated-src/jcasgen/main/org/apache/uima/fit/examples/type/

ecl: bld
	gradle eclipse
	sed -i.bak '/"generated-src\/jcasgen\/main"/d' .classpath

cln:
	rm -rf *~
	gradle clean
	gradle cleanEclipse
	rm -rf bin

cln-all: cln
	rm -rf .settings
	rm -rf .gradle

run:
	gradle run
