
all: ecl

hlp:
	@echo make bld
	@echo make cln

ts: cln
	gradle generateTypeSystem

bld: ts
	$(eval destDir=build/generated-src/jcasgen/main/)
	cp src/main/typesystem/*xml ${destDir}
	gradle build

ecl: bld
	gradle eclipse
	sed -i.bak '/"generated-src\/jcasgen\/main"/d' .classpath

cln:
	rm -rf *~
	gradle clean
	gradle cleanEclipse
	rm -rf bin

cln-all: cln
	rm -rf .classpath
	rm -rf .project
	rm -rf .settings
	rm -rf .gradle

run:
	gradle run
