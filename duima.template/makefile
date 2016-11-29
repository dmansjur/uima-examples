

all:
	@echo make bld
	@echo make cln

bld:
	gradle build

ecl:
	gradle eclipse

cln:
	rm -rf *~
	gradle clean
	gradle cleanEclipse
	rm -rf bin

cln-all: cln
	rm -rf .settings
	rm -rf .gradle
