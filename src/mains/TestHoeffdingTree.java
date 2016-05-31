package mains;

import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import config.FilesConfig;
import models.Entity;
import models.HoeffdingTreeExample;
import preProcessingFiles.CreateArffFiles;
import preProcessingFiles.CrossValidation;
import preProcessingFiles.InvertedIndex;

import utils.DateTime;




public class TestHoeffdingTree {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		int numFolds = 10;
        double[] metrics = new double[]{0,0};
        double[] time = new double[]{0,0};
        
        
        DateTime dt = new DateTime();
        dt.getDate();
        //obtém os dados de treino e teste para cada fold
        dt.getInitialTime();
        CrossValidation cv = new CrossValidation();
        cv.readFiles(FilesConfig.IMPUT_CLASSIFIER, 10);
        dt.getEndTime();
        System.out.println("##### Tempo leitura Arquivos: " + dt.getStepTime());

        CreateArffFiles caf;

        for (int i = 0; i < numFolds; i++) {

            System.out.println("-------------------Inicio Fold " + i + " -----------------------------");
            dt.initTimeFold();

            //Instâncias para o treino            
            ArrayList<Entity> dataTrain = (ArrayList<Entity>) cv.getTrain(i);
            System.out.println("Intancais Treino: " + dataTrain.size());
            //Instâncias para o teste
            ArrayList<Entity> dataTest = (ArrayList<Entity>) cv.getTest(i);
            System.out.println("Intancias Teste: " + dataTest.size());

            // Indexa o treino para salvar o arquivo .arff
            dt.getInitialTime();
            InvertedIndex invertedIndex = new InvertedIndex();
            invertedIndex.insertInvertedFile(dataTrain);
            invertedIndex.atualizaHashIdf();
            dt.getEndTime();
            System.out.println("Tempo Indexação: " + dt.getStepTime());

            // Cria os arquivos de treino e teste para o fold i     
            dt.getInitialTime();
            caf = new CreateArffFiles(invertedIndex);
            caf.criarArquivoArff(dataTrain, 0, FilesConfig.W_TRAIN, true);
            caf.criarArquivoArff(dataTest, 0, FilesConfig.W_TEST, false);
            dt.getEndTime();
            System.out.println("Tempo criação arquivos weka: " + dt.getStepTime());
            
            
            /**********************************************************************************************************
             ********************************* Inserir aqui o modelo ************************************************** 
             **********************************************************************************************************/
            HoeffdingTreeExample hf = new HoeffdingTreeExample();

            hf.HTClassifiers(FilesConfig.W_TRAIN + ".arff", FilesConfig.W_TEST + ".arff", invertedIndex.getNumTokens());
            
            /********************************************************************************************************** 
             ***********************************************************************************************************/
            
            System.out.println("##### Tempo Fold:  " + (dt.getTimeFold()));
        }

        System.out.println("<>---------------------------------------------------<>");
        System.out.println(" Media MicroF1: " + metrics[0] / 10);
        System.out.println("Media MacroF1: " + metrics[1] / 10);
        System.out.println("Media Tempo Treino: " + time[0] / 10);
        System.out.println("Media Tempo Teste: " + time[1] / 10);

        System.out.println("##### Tempo Total: " + dt.getTotalTime());
//        System.out.println(out.toString());
	}

}
