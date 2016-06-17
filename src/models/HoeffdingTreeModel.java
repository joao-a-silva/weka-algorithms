package models;

import moa.classifiers.AbstractClassifier;
import moa.classifiers.core.attributeclassobservers.NumericAttributeClassObserver;
import moa.classifiers.trees.HoeffdingTree;
import moa.core.InstanceExample;
import moa.options.ClassOption;
import moa.streams.ArffFileStream;

import java.io.IOException;

import evaluation.CreateFilesEvaluation;
import utils.DateTime;
import weka.core.Utils;

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
        ClassOption option = new ClassOption("numericEstimator",
                'n', "Numeric estimator to use.", NumericAttributeClassObserver.class,
                "VFMLNumericAttributeClassObserver");
        //options.setValueViaCLIString("-n BinaryTreeNumericAttributeClassObserver");
        hft.getOptions().removeOption("numericEstimator");
        hft.getOptions().addOption(option);
        //System.out.println("-----------Options----------");
        //System.out.println(hft.getOptions().getOption("numericEstimator").getValueAsCLIString());
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
    
    public void hftTest(String pathTest, CreateFilesEvaluation create) throws IOException, Exception {
    	
        int classindex = -1;   //-1 -> last attribute in file
        DateTime dt = new DateTime();
        dt.getDate();
        ArffFileStream afs_test = new ArffFileStream(pathTest, classindex);
        afs_test.prepareForUse();
        hft.setModelContext(afs_test.getHeader());
        hft.prepareForUse();
        try {
            dt.getInitialTime();
            InstanceExample instance = null;
            while(afs_test.hasMoreInstances()){
            	//System.out.print(Utils.maxIndex(hft.getVotesForInstance(instance)));
            	//System.out.println(" -> " + instance.getData().classValue() + "\n");
            	instance = afs_test.nextInstance();
            	Entity entity = new Entity();
				entity.setRealName(instance.getData().classValue()+"");
				entity.setFinalPrediction((double)Utils.maxIndex(hft.getVotesForInstance(instance))+"");
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
        
        
