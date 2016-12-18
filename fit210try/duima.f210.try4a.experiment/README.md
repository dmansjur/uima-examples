Chapter 4. Running Experiments
======================================================


The uimafit-examples module contains a package org.apache.uima.fit.examples.experiment.pos which demonstrates a very simple experimental setup for testing a part-of-speech tagger. 
You may find this example more accessible if you check out the code from subversion and build it in your own environment.


The documentation for this example can be found in the code itself. 
Please refer to RunExperiment as a starting point. 
The following is copied from the javadoc comments of that file:


RunExperiment demonstrates a very common (though simplified) experimental setup 
in which gold standard data is available for some task 
and you want to evaluate how well your analysis engine works against that data. 
Here we are evaluating BaselineTagger which is a (ridiculously) simple part-of-speech tagger 
against the part-of-speech tags found in src/main/resources/org/apache/uima/fit/examples/pos/sample-gold.txt


The basic strategy is as follows:
 - post the data as is into the default view,
 - parse the gold-standard tokens and part-of-speech tags and put the results into another view we will call GOLD_VIEW,
 - create another view called SYSTEM_VIEW and copy the text and Token annotations from the GOLD_VIEW into this view,
 - run the BaselineTagger on the SYSTEM_VIEW over the copied Token annoations,
 - evaluate the part-of-speech tags found in the SYSTEM_VIEW with those in the GOLD_VIEW.

