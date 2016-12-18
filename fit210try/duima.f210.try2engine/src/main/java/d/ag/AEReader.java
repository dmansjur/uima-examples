package d.ag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.DoubleArray;

import d.ts.MRCandidateAnswer;


public class AEReader extends JCasAnnotator_ImplBase {

	public static final String PARAM_DAT_LOC = "data_location";

	@ConfigurationParameter(name = PARAM_DAT_LOC)
	private String sLocDat;




	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		try {
			//String sLoc = "src/main/resources/letor4/mq8b/Fold1/train.txt";
			File fin = new File(sLocDat);
			FileInputStream fis = new FileInputStream(fin);

			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {				
				//System.out.println(line);
				
				String [] sArr = line.split(" ");
				
				
				// [] label
				int iLabel = Integer.parseInt(sArr[0]);
				
				// [] Qid
				String [] sArrQid = sArr[1].split(":");
				int iQid   = Integer.parseInt(sArrQid[1]);
				
				// [] values
				DoubleArray da = new DoubleArray(jCas, sArr.length-2 );
				for(int i=2; i<sArr.length; i++) {
					String [] sIdVal = sArr[i].split(":");
					if( sIdVal.length == 2) {
					//System.out.println(sIdVal[0]);
						da.set(i-2,Double.parseDouble(sIdVal[1]));
					}
				}
				
				MRCandidateAnswer mrca = new MRCandidateAnswer(jCas);
				mrca.setLabel(iLabel);
				mrca.setQid(iQid);
				mrca.setFactor(da);
				mrca.addToIndexes(jCas);
			}

			br.close();
		} catch(Exception e) {
			e.printStackTrace(); 
		}
	}

}
