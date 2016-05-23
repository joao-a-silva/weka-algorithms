package mains;

import moa.classifiers.trees.HoeffdingTree;
import moa.classifiers.*;

public class TestHoeffdingTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numInstances = 1000;
		
		Classifier leaner = new HoeffingTree();
		RandomRBFGenerator stream = new RandomRBFGenerator();
		stream.prepareForUse();
		
		leaner.setModelContext(stream.getHeader());
		leaner.prepareForUse();

		int numberSamplesCorrect = 0;
		int numberSamples = 0;
		boolean isTesting = True;
		while(stream.hasMoreInstances() && numberSamples < numInstances){
			Instance trainInst = stream.nextInstance();
			if(isTesting){
				if(leaner.correctlyClassifiers(trainInst)){
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
