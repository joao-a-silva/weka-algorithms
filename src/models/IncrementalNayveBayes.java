/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;

import com.yahoo.labs.samoa.instances.ArffLoader;

import evaluation.CreateFilesEvaluation;
import utils.DateTime;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.UpdateableClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;
import weka.core.*;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.Loader.StructureNotReadyException;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.AddValues;
import weka.filters.unsupervised.attribute.Standardize;

/**
 *
 * @author João
 */
public class IncrementalNayveBayes {

	private double[] metrics, time;

	public IncrementalNayveBayes() {
		metrics = new double[2];
		time = new double[2];
	}

	public void classifier(String pathTrain, String pathTest, CreateFilesEvaluation create) throws IOException, Exception {
		DateTime dt = new DateTime();
		BufferedReader br = null;

		dt.getDate();

		// Load data train
		weka.core.converters.ArffLoader loader = new weka.core.converters.ArffLoader();
		
		//loader.setRetrieval(2);
		loader.setFile(new File(pathTrain));		
		//System.out.println(loader.getStructure().toString());
		Instances structure = loader.getStructure();		
		structure.setClassIndex(structure.numAttributes()-1);
		
		System.out.println(structure.attribute(structure.classIndex()));
		
        /*AddValues addv = new AddValues();
        addv.setInputFormat(structure);
        addv.setAttributeIndex(""+structure.classIndex()); // the class is the last attribute
        addv.setLabels("1000000");
    
        structure = Filter.useFilter(structure,addv);
        System.out.println(structure.attribute(structure.classIndex()));*/
		
		NaiveBayesUpdateable classifier = new NaiveBayesUpdateable();
		try {
			classifier.buildClassifier(structure);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Can't create the model");
		}

		Instance current = null;
		try {
			dt.getInitialTime();
			int i = 0;

			while ((current = loader.getNextInstance(structure)) != null) {
				// System.out.println("Instance "+i);
				classifier.updateClassifier(current);
				// i++;
			}

			dt.getEndTime();
			this.time[0] = dt.getStepTime();
			System.out.println("Training Time: " + dt.getStepTime());

		} catch (Exception e) {
			System.out.println("Can't Training the classifier!");
		}

		// Load test data
		weka.core.converters.ArffLoader loaderTest = new weka.core.converters.ArffLoader();
		loaderTest.setRetrieval(2);
		loaderTest.setFile(new File(pathTest));

		// test the data
	//	try {
		
			int numAcertos = 0;
		
			dt.getInitialTime();
			int i = 0;
			Instances structureTest = loaderTest.getStructure();
			structureTest.setClassIndex(structureTest.numAttributes() - 1);
			while ((current = loaderTest.getNextInstance(structureTest)) != null) {
				// System.out.println("Instance "+i);
				
				/*if(current.classValue() ==  classifier.classifyInstance(current)){
					numAcertos++;
				}*/
				
				Entity entity = new Entity();
				entity.setRealName(current.classValue()+"");
				entity.setFinalPrediction(classifier.classifyInstance(current)+"");
				create.createFilesEvaluation(entity);
				//System.out.println(current.classValue() + " -> " + classifier.classifyInstance(current) + " -> "
					//	+ classifier.distributionForInstance(current)[(int) classifier.classifyInstance(current)]);
			//	classifier.updateClassifier(current);
				i++;
			}
			System.out.println("Number of hits: "+ numAcertos);
			dt.getEndTime();
			this.time[1] = dt.getStepTime();
			System.out.println("Test Time: " + dt.getStepTime());

		//} catch (Exception e) {
	//		System.out.println("Can't evaluate the model!");
	//	}

	/*	try {
		 dt.getInitialTime();
		 int i =0;
		 while ((current = loaderTest.getNextInstance(structure)) != null ) {
		 System.out.println("Instance "+i);
		 System.out.println(current.classValue()+" -> "+classifier.classifyInstance(current) +" -> "+
		 classifier.distributionForInstance(current)[(int)
		 classifier.classifyInstance(current)]);
		 classifier.updateClassifier(current);
		 i++;
		 }
		
		 dt.getEndTime();
		 this.time[0] = dt.getStepTime();
		 System.out.println("Test Time: "+ dt.getStepTime());
		
		 } catch (Exception e) {
		 System.out.println("Can't test the model!");
		 }*/

	}

	public double[] getMetrics() {
		return metrics;
	}

	public void setMetrics(double[] metrics) {
		this.metrics = metrics;
	}

	public double[] getTime() {
		return metrics;
	}

	public void setTime(double[] metrics) {
		this.metrics = metrics;
	}

}
