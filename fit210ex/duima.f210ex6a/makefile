

all:
	@echo make bld
	@echo make cln

ts:
	gradle generateTypeSystem

bld: ts
	gradle build -x test


cln:
	gradle clean
	gradle cleanEclipse

ecl:
	gradle eclipse


cln-tld:
	find ./ -name "*~" -exec rm {} \;
