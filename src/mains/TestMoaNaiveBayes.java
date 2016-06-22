/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mains;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import config.FilesConfig;
import evaluation.CreateFilesEvaluation;
import evaluation.EvalAlgoritmo;
import models.Entity;
import models.NaiveBayesModel;
import preProcessingFiles.CreateArffFiles;
import preProcessingFiles.CrossValidation;
import preProcessingFiles.InvertedIndex;
import utils.DateTime;
import utils.FilesJ;
import utils.Printer;
import utils.Tables;


/**
 *
 * @author joao
 */
public class TestMoaNaiveBayes {

    /**
     * @param args the command line arguments
     */
	public static void main(String[] args) throws FileNotFoundException, IOException, SAXException, Exception {
        // TODO code application logic here

    	int numFolds = 10;
        double[] metrics = new double[]{0,0}, time = new double[]{0,0}, finalMetrics = new double[8];
        FilesJ fj = new FilesJ();
        
        String  pathTestFile, pathPredictFile, pathResult, pathTable = FilesConfig.TABLES + "_", nameDataSet = fj.pathToTitle2(FilesConfig.IMPUT_CLASSIFIER);
        StringBuilder tableMetricsTime = new StringBuilder(), tableChart = new StringBuilder();
        tableMetricsTime.append("Fold\tt_Train\tt_Test\tMacro\t\t\tMicro \n\n");
        tableChart.append("\nMacro; Micro; t_Train; t_Test; t_Total\n");
        
        CreateArffFiles caf;      
        
        DateTime dt = new DateTime();
        dt.getDate();
        
        
        //obtém os dados de treino e teste para cada fold
        dt.getInitialTime();
        CrossValidation cv = new CrossValidation();
        cv.readFiles(FilesConfig.IMPUT_CLASSIFIER, 10);
        dt.getEndTime();
        System.out.println("##### Tempo leitura Arquivos: " + dt.getStepTime());

      
        double sumTime = 0, totalTrain = 0, totalTest = 0, totalTrainTest = 0, totalNewClass = 0;

        for (int i = 0; i < numFolds; i++) {
        	
        	 /**
             * Arquivos onde serão armazenados logs e arquivos para a avaliação
             */
            
        	{
                pathTestFile = fj.getPahtFile(FilesConfig.TEST_FILE, i);
                pathPredictFile = fj.getPahtFile(FilesConfig.PREDICT_FILE, i);
                pathResult = fj.getPahtFile(FilesConfig.FOLD_RESULT, i);              
                
            }
        	
        	CreateFilesEvaluation createFiles = new CreateFilesEvaluation(pathTestFile, pathPredictFile);

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
             //Antes do treino
            NaiveBayesModel nb = new NaiveBayesModel();
            dt.getStepTime();
            
            //Treino aqui
            nb.nbTrain(FilesConfig.W_TRAIN+".arff");
            
            
            
            //Após o treino
            tableMetricsTime.append(dt.getStepTime()).append("\t");
            System.out.println("##### Tempo Treino: " + dt.getStepTime());
            sumTime += dt.getStepTime();
            totalTrain += dt.getStepTime();
            
            
            //antes do teste
            dt.getStepTime();       
            nb.nbTest(FilesConfig.W_TEST+".arff", createFiles);

            //teste aqui
            
            //Após o teste
            dt.getEndTime();
            tableMetricsTime.append(dt.getStepTime()).append("\t");
            if (i == 0) {
                //  pathTable += test2.getClass().getName() + "_" + nameDataSet;
                //  fj.writeFile(pathTable, "");
            }
            System.out.println("##### Tempo Teste: " + dt.getStepTime());
            sumTime += dt.getStepTime();
            totalTest += dt.getStepTime();
            
            /********************************************************************************************************** 
             ***********************************************************************************************************/
            
            /* ******************************************************************
             * Begin Evaluation
             * ****************************************************************/
            EvalAlgoritmo evA = new EvalAlgoritmo();
            {
                dt.getInitialTime();

                evA.evaluationFold(pathTestFile, pathPredictFile, pathResult);

                // cálculo das metricas gerais do experimento
                {
                    finalMetrics[0] += evA.getMdAcc();
                    finalMetrics[1] += evA.getTxAcerto();
                    finalMetrics[2] += evA.getMdPre();
                    finalMetrics[3] += evA.getMdRe();
                    finalMetrics[4] += evA.getMdF1();
                    finalMetrics[5] += evA.getMicro();
                    finalMetrics[6] += evA.getMacro();
                    finalMetrics[7] += sumTime;
                }

                dt.getEndTime();
                System.out.println(">>>>Tempo Avaliacao: " + dt.getStepTime());
                Printer.printMetrics(evA);
                System.out.println(">>>>> Tempo Fold: " + dt.getTimeFold());
                sumTime += dt.getStepTime();
            }

            /**
             * Tabela de Resultados
             */
            {
                tableMetricsTime.append(evA.getMacro()).append("\t");
                tableMetricsTime.append(evA.getMicro()).append("\t");
                fj.appendFile(pathTable, tableMetricsTime.toString());
                tableMetricsTime = new StringBuilder();
            }
            System.out.println("##### Tempo Fold:  " + (dt.getTimeFold()));
            System.out.println("---------------------> Finished Fold " + i + "<----------------\n");
           
        }

        {
//          finalMetrics[7] = dt.getEndTime();
        	Printer.printerFinalResults(numFolds, finalMetrics);
        	fj.appendFile(pathTable, "Avg Macro: " + finalMetrics[6] / numFolds);
        	fj.appendFile(pathTable, "Avg Micro: " + finalMetrics[5] / numFolds);
        	fj.appendFile(pathTable, "Total Time: " + finalMetrics[7]);
        	fj.appendFile(pathTable, "Total Train: " + totalTrain);
  			fj.appendFile(pathTable, "Total Test: " + totalTest + "\n");

  			{
  				tableChart.append(100 * finalMetrics[6] / numFolds).append("; ");
  				tableChart.append(100 * finalMetrics[5] / numFolds).append("; ");
  				tableChart.append(totalTrain).append("; ");
  				tableChart.append(totalTest).append("; ");
  				tableChart.append(finalMetrics[7]).append("\n");

  			}	

  			Tables.saveMetricsTime(finalMetrics[6], finalMetrics[5], totalTrain, totalTest, (int) totalNewClass);
        }
    }

}
