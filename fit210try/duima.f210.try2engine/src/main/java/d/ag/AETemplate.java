package d.ag;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;



public class AETemplate extends JCasAnnotator_ImplBase {

	public static final String PARAM_DAT_LOC = "data_location";

	@ConfigurationParameter(name = PARAM_DAT_LOC)
	private String sLocDat;

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

	}
	
}