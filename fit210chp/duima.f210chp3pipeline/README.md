Chapter 3. Pipelines
==================================================

UIMA is a component-based architecture that allows composing various processing components into a complex processing pipeline.
A pipeline typically involves a collection reader which ingests documents and analysis engines that do the actual processing.

Normally, you would run a pipeline using a UIMA Collection Processing Engine or using UIMA AS.
uimaFIT offers a third alternative that is much simpler to use and well suited for embedding UIMA pipelines into applications or for writing tests.

As uimaFIT does not supply any readers or processing components, we just assume that we have written three components:

TextReader - reads text files from a directory

Tokenizer - annotates tokens

TokenFrequencyWriter - writes a list of tokens and their frequency to a file



We create descriptors for all components and run them as a pipeline:

```sh
CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(TextReader.class, TextReader.PARAM_INPUT, "/home/uimafit/documents");

AnalysisEngineDescription tokenizer = AnalysisEngineFactory.createEngineDescription(Tokenizer.class);
        

AnalysisEngineDescription tokenFrequencyWriter =
  AnalysisEngineFactory.createEngineDescription(
      TokenFrequencyWriter.class,
          TokenFrequencyWriter.PARAM_OUTPUT, "counts.txt");

SimplePipeline.runPipeline(reader, tokenizer, writer);
```

Instead of running the full pipeline end-to-end, we can also process one document at a time and inspect the analysis results:

```sh
CollectionReaderDescription reader =
  CollectionReaderFactory.createReaderDescription(
      TextReader.class,
          TextReader.PARAM_INPUT, "/home/uimafit/documents");

AnalysisEngineDescription tokenizer =
  AnalysisEngineFactory.createEngineDescription(
      Tokenizer.class);

for (JCas jcas : SimplePipeline.iteratePipeline(reader, tokenizer)) {
  System.out.printf("Found %d tokens%n",
      JCasUtil.select(jcas, Token.class).size());
      }

```