PLANTUML := java -jar ~/.vscode-server/extensions/jebbs.plantuml-2.17.5/plantuml.jar

SOURCES = $(wildcard *.puml)
OBJECTS = $(patsubst %.puml, %.svg, $(SOURCES))
ECHO = echo

.PHONY: clean all

all: $(OBJECTS)

%.svg : %.puml
	$(PLANTUML) -tsvg  $< $@

clean : 
	rm *.svg