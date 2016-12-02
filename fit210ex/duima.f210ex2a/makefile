

all:
	@echo make bld
	@echo make cln

ts:
	gradle generateTypeSystem

bld: ts
	gradle build


ecl:
	gradle eclipse
	sed -i.bak '/"generated-src\/jcasgen\/main"/d' .classpath


cln:
	rm -rf *~
	find ./ -name "*~" -exec rm {} \;
	rm -rf bin
	rm -rf build
	gradle clean
	gradle cleanEclipse

# clean type system
cln-ts:
	@echo " remove the generated type system"


cln-all: cln cln-ts
	rm -rf .settings
	rm -rf .gradle
