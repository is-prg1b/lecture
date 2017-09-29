# GNU Makefile

.SUFFIXES: .html .md .pdf
.DEFAULT: html
all: html note quiz

SLIDES   := $(basename $(notdir $(wildcard slide/lx*.md)))
HTML_TMP := $(addprefix docs/tmp/,  $(addsuffix .html, $(SLIDES)))
HTML     := $(addprefix docs/html/, $(addsuffix .html, $(SLIDES)))
PDF      := $(addprefix docs/pdf/,  $(addsuffix .pdf,  $(SLIDES)))

NOTES    := $(basename $(notdir $(wildcard note/*.md)))
NOTEHTML := $(addprefix docs/note/, $(addsuffix .html, $(NOTES)))

QUIZ     := $(basename $(notdir $(wildcard quiz/*.md)))
QUIZHTML := $(addprefix docs/quiz/, $(addsuffix .html, $(QUIZ)))

clean:
	rm -f $(HTML_TMP) $(HTML) $(NOTES) $(PDF)

# Markdown -> HTML is achieved in two-stages.
html: server docs/index.html $(HTML)

docs/index.html: slide/index.md
	pandoc --smart --to html --standalone --output $@ $^

HTML_DEV = docs/dev/kw.js docs/dev/phantom.js docs/dev/slide.yaml

docs/html/%.html: $(HTML_DEV) slide/%.md
	$(eval slide := $(basename $(notdir $@)))
	$(eval md    := $(addprefix slide/,     $(addsuffix .md,   $(slide))))
	$(eval html1 := $(addprefix docs/tmp/,  $(addsuffix .html, $(slide))))
	$(eval html2 := $(addprefix docs/html/, $(addsuffix .html, $(slide))))

	@# Firstly, Pandoc generates a temporary HTML file:
	@# slide/*.md -> tmp/*.html
	@echo "pandoc:    $(md) => $(html1)"
	@pandoc lib/slide.yaml $(md) \
	  --to=revealjs --slide-level=2 \
	  --standalone \
	  --output=$(html1) \
 	  -V revealjs-url=/lecture/lib/reveal.js-3.5.0 \
 	  -V theme=serif \
	  -V slideNumber=true \
	  --css=/lecture/lib/kw.css \
	  --mathjax \
	  --smart

	@# Then, PhantomJS is used to patch the temporary HTML and finishes it.
	@# tmp/*.html -> ../*.html
	@echo "phantomjs: $(html1) => $(html2)"
	@phantomjs docs/dev/phantom.js $(slide) $(html2)
	@echo

note: $(NOTEHTML)
docs/note/%.html: note/%.md
	$(eval note := $(basename $(notdir $@)))
	$(eval md   := $(addprefix note/,      $(addsuffix .md,   $(note))))
	$(eval html := $(addprefix docs/note/, $(addsuffix .html, $(note))))
	@pandoc docs/dev/note.yaml $(md) \
	  --include-in-header=docs/dev/note.header \
	  --include-after-body=docs/dev/note.footer \
	  --standalone --to=html --output=$(html) \
	  --highlight-style=espresso \
	  --smart

quiz: $(QUIZHTML)
docs/quiz/%.html: quiz/%.md
	$(eval quiz := $(basename $(notdir $@)))
	$(eval md   := $(addprefix quiz/,      $(addsuffix .md,   $(quiz))))
	$(eval html := $(addprefix docs/quiz/, $(addsuffix .html, $(quiz))))
	pandoc $(md) \
	  --include-in-header=docs/dev/quiz.header \
	  --standalone --to=html --output=$(html) \
	  --highlight-style=monochrome \
	  --smart

server:
	@wget --quiet --spider "http://localhost:8080/" || (cd $(HOME)/Sites; php -S localhost:8080 &)

shutdown:
	@killall php
