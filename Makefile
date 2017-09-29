# GNU Makefile

.SUFFIXES: .html .md .pdf
.DEFAULT: html
all: html note quiz

SLIDES   := $(basename $(notdir $(wildcard slide/lx*.md)))
HTML_TMP := $(addprefix docs/tmp/,   $(addsuffix .html, $(SLIDES)))
HTML     := $(addprefix docs/slide/, $(addsuffix .html, $(SLIDES)))
PDF      := $(addprefix docs/pdf/,   $(addsuffix .pdf,  $(SLIDES)))

NOTES    := $(basename $(notdir $(wildcard note/*.md)))
NOTEHTML := $(addprefix docs/note/, $(addsuffix .html, $(NOTES)))

QUIZ     := $(basename $(notdir $(wildcard quiz/*.md)))
QUIZHTML := $(addprefix docs/quiz/, $(addsuffix .html, $(QUIZ)))

clean:
	rm -f $(HTML_TMP) $(HTML) $(PDF) $(NOTEHTML) $(QUIZHTML)

# Markdown -> HTML is achieved in two-stages.
html: server docs/index.html $(HTML)

HTML_DEP = docs/dev/kw.js lib/phantom.js slide/slide.yaml

docs/slide/%.html: $(HTML_DEP) slide/%.md
	$(eval slide := $(basename $(notdir $@)))
	$(eval md    := $(addprefix slide/,     $(addsuffix .md,   $(slide))))
	$(eval html1 := $(addprefix docs/tmp/,  $(addsuffix .html, $(slide))))
	$(eval html2 := $(addprefix docs/slide/, $(addsuffix .html, $(slide))))

	@# Firstly, Pandoc generates a temporary HTML file:
	@# slide/*.md -> tmp/*.html
	@echo "pandoc: $(slide) => $(html1)"
	@pandoc slide/slide.yaml $(md) \
	  --include-in-header=slide/slide.header \
	  --to=revealjs --slide-level=2 \
	  --standalone \
	  --output=$(html1) \
 	  -V revealjs-url=/lecture/lib/reveal.js-3.5.0 \
 	  -V theme=serif \
	  -V slideNumber=true \
	  --mathjax \
	  --smart

	@# Then, PhantomJS is used to patch the temporary HTML and finishes it.
	@# tmp/*.html -> ../*.html
	@echo "phantomjs: $(html1) => $(html2)"
	@phantomjs lib/phantom.js $(slide) $(html2)
	@echo

note: $(NOTEHTML)
docs/note/%.html: note/%.md
	$(eval note := $(basename $(notdir $@)))
	$(eval md   := $(addprefix note/,      $(addsuffix .md,   $(note))))
	$(eval html := $(addprefix docs/note/, $(addsuffix .html, $(note))))

	@echo "pandoc: $(note) => $(html)"
	@pandoc note/note.yaml $(md) \
	  --include-in-header=note/note.header \
	  --standalone --to=html --output=$(html) \
	  --smart

quiz: $(QUIZHTML)
docs/quiz/%.html: quiz/%.md
	$(eval quiz := $(basename $(notdir $@)))
	$(eval md   := $(addprefix quiz/,      $(addsuffix .md,   $(quiz))))
	$(eval html := $(addprefix docs/quiz/, $(addsuffix .html, $(quiz))))

	@echo "pandoc: $(quiz) => $(html)"
	@pandoc quiz/quiz.yaml $(md) \
	  --include-in-header=quiz/quiz.header \
	  --standalone --to=html --output=$(html) \
	  --highlight-style=monochrome \
	  --smart

server:
	@wget --quiet --spider "http://localhost:8080/" || (cd $(HOME)/Sites; php -S localhost:8080 &)

shutdown:
	@killall php
