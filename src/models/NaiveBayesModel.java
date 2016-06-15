package models;

import java.io.IOException;

import evaluation.CreateFilesEvaluation;
import moa.core.InstanceExample;
import moa.streams.ArffFileStream;
import utils.DateTime;
import weka.core.Utils;

public class NaiveBayesModel {
	
	private moa.classifiers.AbstractClassifier nb;
	
	public NaiveBayesModel() {
		
		try {
			nb = new moa.classifiers.bayes.NaiveBayes();
		}catch(Exception e){
			System.err.println("Can't create the classifier!");
		}
	}
	
	public void nbTrain(String pathTrain) throws IOException {
		
		int classIndex = -1;
		DateTime date = new DateTime();
		date.getDate();
		ArffFileStream afsTrain = new ArffFileStream(pathTrain, classIndex);
		afsTrain.prepareForUse();
		nb.setModelContext(afsTrain.getHeader());
		nb.prepareForUse();
		
		try{
			
			date.getInitialTime();
		
			while(afsTrain.hasMoreInstances()){
				nb.trainOnInstance(afsTrain.nextInstance());
			}
			
			date.getEndTime();
			System.out.println("Training Time: "+ date.getStepTime());
			
			}catch(Exception e){
			System.err.println("Can't train the stream!");
		}
	}
		
	public void nbTest(String pathTest, CreateFilesEvaluation create) throws IOException, Exception {
	    	
		int classindex = -1;   //-1 -> last attribute in file
	    DateTime dt = new DateTime();
	    dt.getDate();
	    ArffFileStream afs_test = new ArffFileStream(pathTest, classindex);
	    afs_test.prepareForUse();
	    nb.setModelContext(afs_test.getHeader());
	    nb.prepareForUse();
	        
	    try {
	    	dt.getInitialTime();
	        InstanceExample instance = null;
	        while(afs_test.hasMoreInstances()){
	        	//System.out.print(Utils.maxIndex(hft.getVotesForInstance(instance)));
	            //System.out.println(" -> " + instance.getData().classValue() + "\n");
	        	instance = afs_test.nextInstance();
            	Entity entity = new Entity();
				entity.setRealName(instance.getData().classValue()+"");
				entity.setFinalPrediction((double)Utils.maxIndex(nb.getVotesForInstance(instance))+"");
				entity.setDescription(instance + "");
				create.createFilesEvaluation(entity);
	        }
	        dt.getEndTime();
	        System.out.println("Testing Time: "+ dt.getStepTime());
	    } catch (Exception e) {
	    	System.out.println("Can't test the stream!");
	    }
	}
	
}
