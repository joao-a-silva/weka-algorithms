package mains;


import java.io.FileNotFoundException;
import java.util.Arrays;

import com.yahoo.labs.samoa.instances.ArffLoader;
import com.yahoo.labs.samoa.instances.Instance;

import moa.core.InstanceExample;
import utils.FilesJ;

public class TestNaiveBayes {

	public static void main(String[] args) throws FileNotFoundException {
		
		int numAmostrasCorretas = 0;
		moa.classifiers.AbstractClassifier bayes = new moa.classifiers.bayes.NaiveBayes();
		moa.streams.ArffFileStream streamTrain = new moa.streams.ArffFileStream("/home/diego/workspaceIniciacao/wekaAlgorithms/files/datasets/weka/training.arff", -1);
		moa.streams.ArffFileStream streamTest = new moa.streams.ArffFileStream("/home/diego/workspaceIniciacao/wekaAlgorithms/files/datasets/weka/test.arff", -1);
		//System.out.println(stream.getHeader());
		
		streamTrain.prepareForUse();
		bayes.setModelContext(streamTrain.getHeader());
		bayes.prepareForUse();
		
		streamTest.prepareForUse();
		bayes.setModelContext(streamTest.getHeader());
		
		
		while(streamTrain.hasMoreInstances()){   
		
			InstanceExample trainInst = streamTrain.nextInstance();
			//System.out.println(trainInst);
			bayes.trainOnInstance(trainInst);
			
		}
		
		while(streamTest.hasMoreInstances()){
			
			InstanceExample instanceTest = streamTest.nextInstance();
			
			//bayes.getPredictionForInstance(instanceTest).getClass()){
			
			
			System.out.println(Arrays.toString(bayes.getVotesForInstance(instanceTest)));
		}
		
		//System.out.println(numAmostrasCorretas);
	}

}
