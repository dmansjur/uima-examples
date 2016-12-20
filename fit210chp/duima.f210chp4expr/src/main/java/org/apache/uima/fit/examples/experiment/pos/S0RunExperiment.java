/* 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.uima.fit.examples.experiment.pos;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.ViewTextCopierAnnotator;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

/**
 * 
 * This class demonstrates a very common (though simplified) experimental setup 
 * in which gold standard data is available for some task 
 * and you want to evaluate how well your analysis engine works against that data. 
 * 
 * Here we are evaluating "BaselineTagger" 
 * which is a (ridiculously) simple part-of-speech tagger 
 * against the part-of-speech tags found in
 * "src/main/resources/org/apache/uima/fit/examples/pos/sample-gold.txt".
 * 
 * <p>
 * The basic strategy is as follows:
 * <ul>
 * <li>post the data "as is" into the default view</li>
 * <li>parse the gold-standard tokens 
 * and part-of-speech tags and put the results into another view we will call GOLD_VIEW</li>
 * <li>create another view called SYSTEM_VIEW 
 * and copy the text and Token annotations from the GOLD_VIEW into this view</li>
 * <li>Run the BaselineTagger on the SYSTEM_VIEW over the copied Token annoations</lI>
 * <li>Evaluate the part-of-speech tags found in the SYSTEM_VIEW with those in the GOLD_VIEW</li>
 * </ul>
 * 
 * Please see comments in the code for details on how the UIMA pipeline is set up and run for this
 * task.
 */
public class S0RunExperiment {

	public static final String GOLD_VIEW = "GOLD_VIEW";

	public static final String SYSTEM_VIEW = "SYSTEM_VIEW";

	public static final String VIEW1 = "VIEW1";

	public static final String VIEW2 = "VIEW2";


	public static void main(String[] args) throws UIMAException, IOException {

		// [] The lineReader simply copies the lines from the input file into the default view - one line per CAS
		String samplePosFileName = "src/main/resources/org/apache/uima/fit/examples/pos/sample-gold.txt";
		CollectionReader lineReader = CollectionReaderFactory.createReader(S1LineReader.class,
				S1LineReader.PARAM_INPUT_FILE, samplePosFileName);

		// [] 
		AggregateBuilder builder = new AggregateBuilder();

		// [] The goldTagger parses the data in the default view into Token objects
		// along with their part-of-speech tags which will be added to the GOLD_VIEW
		AnalysisEngineDescription goldTagger = AnalysisEngineFactory.createEngineDescription(S2aGoldTagger.class);
		builder.add(goldTagger);

		// The textCopier creates the SYSTEM_VIEW and set the text of this view
		// to that of the text found in GOLD_VIEW
		AnalysisEngineDescription textCopier = AnalysisEngineFactory.createEngineDescription(
				ViewTextCopierAnnotator.class,
				ViewTextCopierAnnotator.PARAM_SOURCE_VIEW_NAME, GOLD_VIEW,
				ViewTextCopierAnnotator.PARAM_DESTINATION_VIEW_NAME, SYSTEM_VIEW);
		builder.add(textCopier);

		// The sentenceAndTokenCopier copies Token and Sentence annotations in
		// the GOLD_VIEW into the SYSTEM_VIEW
		AnalysisEngineDescription sentenceAndTokenCopier = AnalysisEngineFactory
				.createEngineDescription(S2bSentenceAndTokenCopier.class);
		builder.add(sentenceAndTokenCopier, VIEW1, GOLD_VIEW, VIEW2,SYSTEM_VIEW);


		// The baselineTagger is run on the SYSTEM_VIEW
		AnalysisEngineDescription baselineTagger = AnalysisEngineFactory.createEngineDescription(
				S2cBaselineTagger.class);
		builder.add(baselineTagger, CAS.NAME_DEFAULT_SOFA, SYSTEM_VIEW);

		// The evaluator will compare the part-of-speech tags in the SYSTEM_VIEW
		// with those in the GOLD_VIEW
		AnalysisEngineDescription evaluator = AnalysisEngineFactory.createEngineDescription(
				S2dEvaluator.class);
		builder.add(evaluator);

		// The xWriter writes out the contents of each CAS (one per sentence) to an XMI file. 
		// It is instructive to open one of these XMI files in the CAS Visual Debugger 
		// and look at the contents of each view.
		AnalysisEngineDescription xWriter = AnalysisEngineFactory.createEngineDescription(
				S2eXmiWriter.class, S2eXmiWriter.PARAM_OUTPUT_DIRECTORY, "build/examples/pos/xmi");
		builder.add(xWriter);

		// runs the collection reader and the aggregate AE.
		SimplePipeline.runPipeline(lineReader, builder.createAggregate());
	}
}
