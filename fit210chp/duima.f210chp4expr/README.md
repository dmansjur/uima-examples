Chapter 4. Running Experiments
======================================================

The uimafit-examples module contains a package org.apache.uima.fit.examples.experiment.pos 
which demonstrates a very simple experimental setup for testing a part-of-speech tagger. 
You may find this example more accessible if you check out the code from subversion 
and build it in your own environment.


The documentation for this example can be found in the code itself. 
Please refer to S0RunExperiment as a starting point. 
The following is copied from the javadoc comments of that file:


RunExperiment demonstrates a very common (though simplified) experimental setup 
in which gold standard data is available for some task 
and you want to evaluate how well your analysis engine works against that data. 
Here we are evaluating S2cBaselineTagger which is a (ridiculously) simple part-of-speech tagger 
against the part-of-speech tags found in 
src/main/resources/org/apache/uima/fit/examples/pos/sample-gold.txt


The basic strategy is as follows:
 - S1LineReader : post the data as is into the default view,
 - S2aGoldTagger : parse the gold-standard tokens and part-of-speech tags and put the results into another view we will call GOLD_VIEW,
 - S2bSentenceAndTokenCopier : create another view called SYSTEM_VIEW and copy the text and Token annotations from the GOLD_VIEW into this view,
 - S2cBaselineTagger : run the BaselineTagger on the SYSTEM_VIEW over the copied Token annoations,
 - S2dEvaluator : evaluate the part-of-speech tags found in the SYSTEM_VIEW with those in the GOLD_VIEW,
 - S2eXmiWriter : save the final cas into a file system. 

N.B. 
There is one extra AE between S2aGoldTagger and S2bSentenceAndTokenCopier, i.e. ViewTextCopierAnnotator.class. 
ViewTextCopierAnnotator is available at org.apache.uima.fit.component.ViewTextCopierAnnotator;
It is used to copy text between views. 
 
N.B. 
View names are specified as static members in the S0RunExperiment class. 
Thus, one may specify the names as S0RunExperiment.GOLD_VIEW and S0RunExperiment.SYSTEM_VIEW. 
The default view is specified in the UIMA framework org.apache.uima.cas.CAS.NAME_DEFAULT_SOFA




