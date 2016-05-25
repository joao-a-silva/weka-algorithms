/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import utils.DateTime;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;


/**
 *
 * @author claudiane
 */
public class WekaAlgs {

    private double[] metrics, time;


    public WekaAlgs() {
        metrics = new double[2];
        time = new double[2];
    }

    public void wekaClassifiers(String pathTrain, String pathTest, String alg, int numTokens, int numLevels) throws IOException, Exception {
        DateTime dt = new DateTime();
        BufferedReader br = null;

        dt.getDate();
        //load the train data
        br = new BufferedReader(new FileReader(pathTrain));
        Instances trainData = new Instances(br);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        br.close();
        
        //load the test data
        br = new BufferedReader(new FileReader(pathTest));
        Instances testData = new Instances(br);
        testData.setClassIndex(testData.numAttributes() - 1);
        br.close();
      
        //Instance the classifier
        NaiveBayes nb = new NaiveBayes();

        //Train the model
        try {
            dt.getInitialTime();
            nb.buildClassifier(trainData);
            dt.getEndTime();
            this.time[0] = dt.getStepTime();
            System.out.println("Training Time: "+ dt.getStepTime());
            
        } catch (Exception e) {
            System.out.println("Can't create the classifier!");
        }

        //Test and evaluate the model
        Evaluation evaluation = new Evaluation(trainData);

        try {
            dt.getInitialTime();
            evaluation.evaluateModel(nb, testData);
            dt.getEndTime();
            this.time[1] = dt.getStepTime();
            System.out.println("Test Time: "+ dt.getStepTime());            
            System.out.println("\nMicroF1=  " + evaluation.unweightedMicroFmeasure());
            System.out.println("MicroF1=  " + evaluation.unweightedMacroFmeasure());

            metrics[0] = evaluation.unweightedMicroFmeasure();
            metrics[1] = evaluation.unweightedMacroFmeasure();

        } catch (Exception e) {
            System.out.println("Can't evaluate the model!");
        }
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
