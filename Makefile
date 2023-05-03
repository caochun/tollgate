PLANTUML := java -jar ~/.vscode/extensions/jebbs.plantuml-2.17.5/plantuml.jar

UML_DIR = plantuml
SOURCES = $(wildcard $(UML_DIR)/*.puml)
OBJECTS = $(patsubst $(UML_DIR)/%.puml, $(UML_DIR)/%.svg, $(SOURCES))
ECHO = echo

.PHONY: clean all

all: $(OBJECTS)

$(UML_DIR)/%.svg : $(UML_DIR)/%.puml
	$(PLANTUML) -tsvg  $<

clean : 
	rm $(UML_DIR)/*.svg