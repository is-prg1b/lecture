# GNU Makefile

.SUFFIXES: .html .md .pdf
.DEFAULT: html
all: html

BASE     := localhost:8082

SLIDES   := $(basename $(notdir $(wildcard slide/day*.md)))
HTML_TMP := $(addprefix docs/tmp/,  $(addsuffix .html, $(SLIDES)))
HTML     := $(addprefix docs/html/, $(addsuffix .html, $(SLIDES)))
PDF      := $(addprefix docs/pdf/,  $(addsuffix .pdf,  $(SLIDES)))

clean:
	rm -f $(HTML_TMP) $(HTML) $(PDF)

# Markdown -> HTML is achieved in two-stages.
html: server docs/index.html $(HTML)

docs/index.html: slide/index.md
	pandoc --to html --standalone --output $@ $^

HTML_DEV = docs/dev/kw.js docs/dev/phantom.js docs/dev/slide.yaml

docs/html/%.html: $(HTML_DEV) slide/%.md
	$(eval slide := $(basename $(notdir $@)))
	$(eval md    := $(addprefix slide/,     $(addsuffix .md,   $(slide))))
	$(eval html1 := $(addprefix docs/tmp/,  $(addsuffix .html, $(slide))))
	$(eval html2 := $(addprefix docs/html/, $(addsuffix .html, $(slide))))

	@# Firstly, Pandoc generates a temporary HTML file:
	@# slide/*.md -> tmp/*.html
	@echo "pandoc:    $(md) => $(html1)"
	@pandoc docs/dev/slide.yaml $(md) \
	  --to=revealjs --slide-level=2 \
	  --standalone \
	  --output=$(html1) \
 	  -V revealjs-url=../lib/reveal.js-3.5.0 \
 	  -V theme=serif \
	  --css=../lib/kw.css \
	  --smart

	@# Then, PhantomJS is used to patch the temporary HTML and finishes it.
	@# tmp/*.html -> ../*.html
	@echo "phantomjs: $(html1) => $(html2)"
	@phantomjs docs/dev/phantom.js $(slide) $(html2)
	@echo

pdf: $(PDF)

pdf/%.pdf: docs/%.html
	$(eval slide := $(basename $(notdir $@)))
	$(eval pdf := $(addprefix docs/pdf/, $(addsuffix .pdf, $(slide))))
	$(eval url := $(addprefix http://$(BASE)/, $(addsuffix .html, $(slide))))

	decktape $(url) $(pdf)

server:
	wget --quiet --spider "http://$(BASE)/" || (cd docs; php -S $(BASE) &)

shutdown:
	killall php
