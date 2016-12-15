Chapter 2 Getting Started
============================================

2.1. Adding uimaFIT to your project

The following instructions describe how to add uimaFIT to your project's classpath.

2.1.1. Maven users
If you use Maven, then uimaFIT can be added to your project by simply adding uimaFIT as a project dependency by adding the following snippet of XML to your pom.xml file:

<dependency>
  <groupId>org.apache.uima</groupId>
    <artifactId>uimafit-core</artifactId>
      <version>2.1.0</version>
      </dependency>
      uimaFIT distributions are hosted by Maven Central and so no repository needs to be added to your pom.xml file.

2.1.2. Non-Maven users
If you do not build with Maven, then download uimaFIT from the Apache UIMA downloads page. The file name should be uimafit-2.1.0-bin.zip. Download and unpack this file. The contents of the resulting upacked directory will contain a directory called lib. Add all of the files in this directory to your classpath.

2.2. A simple analysis engine implementation

Here is the complete analysis engine implementation for this example.

```java

public class GetStartedQuickAE
    extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {

  public static final String PARAM_STRING = "stringParam";
    @ConfigurationParameter(name = PARAM_STRING)
      private String stringParam;

  @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        System.out.println("Hello world!  Say 'hi' to " + stringParam);
	  }
	  }

```


  The first thing to note is that the member variable stringParam is annotated with @ConfigurationParameter which tells uimaFIT that this is an analysis engine configuration parameter. It is best practice to create a public constant for the parameter name, here PARAM_STRING The second thing to note is that we extend uimaFIT's version of the JCasAnnotator_ImplBase. The initialize method of this super class calls:

ConfigurationParameterInitializer.initializeConfigurationParameters(
  Object, UimaContext)
  which populates the configuration parameters with the appropriate contents of the UimaContext. If you do not want to extend uimaFIT's JCasAnnotator_ImplBase, then you can call this method directly in the initialize method of your analysis engine or any class that implements Initializable. You can call this method for an instance of any class that has configuration parameters.




2.3. Running the analysis engine

The following lines of code demonstrate how to instantiate and run the analysis engine from a main method:

JCas jCas = JCasFactory.createJCas();

AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(
  GetStartedQuickAE.class,
    GetStartedQuickAE.PARAM_STRING, "uimaFIT");

analysisEngine.process(jCas);
In a more involved example, we would probably instantiate a collection reader and run this analysis engine over a collection of documents. Here, it suffices to simply create a JCas. Line 3 instantiates the analysis engine using AnalysisEngineFactory and sets the string parameter named stringParam to the value uimaFIT. Running this simple program sends the following output to the console:

Hello world!  Say 'hi' to uimaFIT
Normally you would be using a type system with your analysis components. When using uimaFIT, it is easiest to keep your type system descriptors in your source folders and make them known to uimaFIT. To do so, create a file META-INF/org.apache.uima.fit/types.txt in a source folder and add references to all your type descriptors to the file, one per line. You can also use wildcards. For example:

classpath*:org/apache/uima/fit/examples/type/Token.xml
classpath*:org/apache/uima/fit/examples/type/Sentence.xml
classpath*:org/apache/uima/fit/examples/tutorial/type/*.xml



2.4. Generate a descriptor file


The following lines of code demonstrate how a descriptor file can be generated using the class definition:

AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(
  GetStartedQuickAE.class,
    GetStartedQuickAE.PARAM_STRING, "uimaFIT");

analysisEngineDescription.toXML(
  new FileOutputStream("GetStartedQuickAE.xml"));
  If you open the resulting descriptor file you will see that the configuration parameter stringParam is defined with the value set to uimaFIT. We could now instantiate an analysis engine using this descriptor file with a line of code like this:

AnalysisEngineFactory.createEngine("GetStartedQuickAE");
But, of course, we really wouldn't want to do that now that we can instantiate analysis engines using the class definition as was done above!

This chapter, of course, did not demonstrate every feature of uimaFIT which provides support for annotating external resources, creating aggregate engines, running pipelines, testing components, among others.


