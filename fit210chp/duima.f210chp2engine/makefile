

all:
	@echo make bld
	@echo make cln
	@echo
	@echo make run1
	@echo make run2


ts:
	gradle generateTypeSystem

bld: ts
	gradle build

ecl:
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

run1:
	gradle run1

run2:
	gradle run2
