package mains;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.streams.generators.RandomRBFGenerator;
import weka.core.Instance;

public class TestHoeffdingTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numInstances = 1000;
		
		Classifier leaner = new HoeffdingTree();
		RandomRBFGenerator stream = new RandomRBFGenerator();
		stream.prepareForUse();
		
		leaner.setModelContext(stream.getHeader());
		leaner.prepareForUse();

		int numberSamplesCorrect = 0;
		int numberSamples = 0;
		boolean isTesting = true;
		while(stream.hasMoreInstances() && numberSamples < numInstances){
			Instance trainInst = stream.nextInstance();
			if(isTesting){
				if(leaner.correctlyClassifies(trainInst)){
					numberSamplesCorrect++;
				}
			}
			numberSamples++;
			leaner.trainOnInstance(trainInst);
		}
		double accuracy = 100.0*(double) numberSamplesCorrect/(double) numberSamples;
		System.out.println(numberSamples + " instances processed with " + accuracy + "% accuracy");
	}

}
