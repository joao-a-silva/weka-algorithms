package models;

import moa.classifiers.AbstractClassifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.streams.ArffFileStream;
import weka.classifiers.evaluation.Evaluation;
//import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import com.yahoo.labs.samoa.instances.Instance;
//import com.yahoo.labs.samoa.instances.Instances;

import config.FilesConfig;
import utils.DateTime;

public class HoeffdingTreeModel {
	
	private AbstractClassifier hft;
	
	public HoeffdingTreeModel(){
		try{
			hft = new HoeffdingTree();
		}
		catch(Exception e){
			System.out.println("Can't create the classifier!");
		}
	}
	
    
    public void hftTrain(String pathTrain) throws IOException, Exception {
    	
        int classindex = -1;   //-1 -> last attribute in file
        DateTime dt = new DateTime();
        dt.getDate();
        ArffFileStream afs_train = new ArffFileStream(pathTrain, classindex);
        afs_train.prepareForUse();
        hft.setModelContext(afs_train.getHeader());
        hft.prepareForUse();
        try {
            dt.getInitialTime();
            while(afs_train.hasMoreInstances()){
            	hft.trainOnInstance(afs_train.nextInstance());
            }
            dt.getEndTime();
            System.out.println("Training Time: "+ dt.getStepTime());
        } catch (Exception e) {
            System.out.println("Can't train the stream!");
        }
    }
    
    public void hftTest(String pathTest) throws IOException, Exception {
    	
        int classindex = -1;   //-1 -> last attribute in file
        DateTime dt = new DateTime();
        dt.getDate();
        ArffFileStream afs_test = new ArffFileStream(pathTest, classindex);
        afs_test.prepareForUse();
        hft.setModelContext(afs_test.getHeader());
        hft.prepareForUse();
        try {
            dt.getInitialTime();
            while(afs_test.hasMoreInstances()){
            	hft.getVotesForInstance(afs_test.nextInstance());
            }
            dt.getEndTime();
            System.out.println("Testing Time: "+ dt.getStepTime());
        } catch (Exception e) {
            System.out.println("Can't test the stream!");
        }
    }
}
        
        
