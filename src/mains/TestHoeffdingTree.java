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

	}

}
