

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
	sed -i.bak '/"generated-src\/jcasgen\/main"/d' .classpath

cln-tld:
	find ./ -name "*~" -exec rm {} \;
