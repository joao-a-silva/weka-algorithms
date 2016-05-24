package models;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import utils.DateTime;

public class HoeffdingTreeExample {
	private double[] metrics;
    private int numTrees;

    public HoeffdingTreeExample(int numTrees) {
        metrics = new double[2];
        this.numTrees = numTrees;
    }

    public void HTClassifiers(String pathTrain, String pathTest, int numTokens, int numLevels) throws IOException, Exception {
        System.out.println("Features: "+ numTokens);
        
        BufferedReader br = null;
        int numFolds = 10;

        DateTime dt = new DateTime();
        dt.getDate();
        br = new BufferedReader(new FileReader(pathTrain));
        Instances trainData = new Instances(br);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        br.close();

        br = new BufferedReader(new FileReader(pathTest));
        Instances testData = new Instances(br);
        testData.setClassIndex(testData.numAttributes() - 1);
        br.close();

        //MOA m = new MOA();
        Classifier clf = new HoeffdingTree();
        System.out.println("Num tokens " + numTokens);
        int k = (int) ((Math.log(numTokens) / Math.log(2)) + 1);
        System.out.println("k " + k);
        HoeffdingTree hf = new HoeffdingTree();
        hf.setNumTrees(this.numTrees);

        if (numLevels != 0) {
            System.out.println("NumLevels: " + numLevels);
            hf.setMaxDepth(numLevels);
        }
       
        
//        rf.setNumFeatures(1000);
        try {
            dt.getInitialTime();
            hf.trainOnInstanceImpl(trainData);
            dt.getEndTime();
            System.out.println("Training Time: "+ dt.getStepTime());
        } catch (Exception e) {
            System.out.println("Can't create the classifier!");
        }
        
        
//        
//        for (int i = 0; i < testData.size(); i++) {
//            dt.getInitialTime();
//            System.out.println(i+" -> "+nb.classifyInstance(testData.get(i)));
//            dt.getEndTime();
//            System.out.println("Time: "+ dt.getStepTime());
//        }
////        
        System.out.println("");
        
        

//               System.out.println(rf.getTechnicalInformation()
//               );
//        System.out.println("Trees: "+ rf.getNumTrees());
//        System.out.println("Featutes "+rf.getNumFeatures());
        //  System.out.println(Arrays.toString(clf.getOptions()));
        Evaluation evaluation = new Evaluation(trainData);

        try {
            dt.getInitialTime();
            evaluation.evaluateModel(hf, testData);
            dt.getEndTime();
            System.out.println("Test Time: "+ dt.getStepTime());
            
            System.out.println("\nMicroF1=  " + evaluation.unweightedMicroFmeasure());
            System.out.println("MicroF1=  " + evaluation.unweightedMacroFmeasure());

            metrics[0] = evaluation.unweightedMicroFmeasure();
            metrics[1] = evaluation.unweightedMacroFmeasure();

        } catch (Exception e) {
            System.out.println("Can't evaluate the model!");
        }

        
        
        
       
        
//        
    }

    public double[] getMetrics() {
        return metrics;
    }

    public void setMetrics(double[] metrics) {
        this.metrics = metrics;
    }
}
