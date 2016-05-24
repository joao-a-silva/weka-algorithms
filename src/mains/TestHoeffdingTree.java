package mains;

import java.util.ArrayList;

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
	 */
	public static void main(String[] args) {
		
		int numTrees = Integer.parseInt(args[2]);
        int numLevesl = Integer.parseInt(args[3]);
        
        switch (args[1]) {
            
            case "-a":
                main(new String[] {args[0],"-p", args[2], args[3]});
                main(new String[] {args[0],"-e", args[2], args[3]});
                main(new String[] {args[0],"-ne", args[2], args[3]});
                main(new String[] {args[0],"-b", args[2], args[3]});
                break;

            case "-p":
                FilesConfig.IMPUT_CLASSIFIER = "files/imput/printers/17-Printers-Fold-";

                if (!args[0].equals("-a")) {
                    break;
                }

            case "-e":
                FilesConfig.IMPUT_CLASSIFIER = "files/imput/comCodigo/10-Produtos_Com_Codigo-Fold-";
                if (!args[0].equals("-a")) {
                    break;
                }

            case "-ne":
                FilesConfig.IMPUT_CLASSIFIER = "files/imput/semcodigo/14-Produtos-Sem-Codigos-Fold-";
                if (!args[0].equals("-a")) {
                    break;
                }
                
                
                case "-b":
                FilesConfig.IMPUT_CLASSIFIER = "files/imput/books/15-Livros-Fold-";
                if (!args[0].equals("-a")) {
                    break;
                }
        }

        
        
        int numFolds = 10;
        double[] metrics = new double[2];
        metrics[0] = 0.0;
        metrics[1] = 0.0;

        DateTime dt = new DateTime();
        dt.getDate();

        dt.getInitialTime();
       // PreProcessingFiles ppf = new PreProcessingFiles();

        //obtém os dados de treino e teste para cada fold
        CrossValidation cv = new CrossValidation();

        for (int i = 0; i < numFolds; i++) {
            System.out.println("Carregando Arquivos Fold " + i);
            cv.readFiles(FilesConfig.IMPUT_CLASSIFIER, i + 1);
        }

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

            // Gera o modelo de treino com o SVM
            dt.getInitialTime();
            InvertedIndex cj = new InvertedIndex();
            cj.insertInvertedFile(dataTrain);
//            cj.insertInvertedFile(test);
            cj.atualizaHashIdf();

            caf = new CreateArffFiles(cj);

            caf.criarArquivoArff(dataTrain, 0, FilesConfig.W_TRAIN, true);
            caf.criarArquivoArff(dataTest, 0, FilesConfig.W_TEST, false);

            HoeffdingTreeExample hf = new HoeffdingTreeExample(numTrees);

            hf.HTClassifiers(FilesConfig.W_TRAIN + ".arff", FilesConfig.W_TEST + ".arff", cj.getNumTokens(), numLevesl);

            metrics[0] += hf.getMetrics()[0];
            metrics[1] += hf.getMetrics()[1];

//            svm.trainLibLinear(FilesConfig.SVM_TREINO+i+".arff");
            dt.getEndTime();
            
            System.out.println("##### Tempo Treinamento RF:  " + dt.getStepTime());
            dt.getTimeFold();

 

        }

        System.out.println("<>---------------------------------------------------<>");
        System.out.println("MicroF1: " + metrics[0] / 10);
        System.out.println("MacroF1: " + metrics[1] / 10);

        System.out.println("##### Tempo Total: " + dt.getTotalTime());
        
        
        FilesJ fj = new FilesJ();       
        fj.appendFile("files/evaluation/weka/metricsTime", metrics[0]/10+", "+metrics[1]/10+", "+ dt.getTotalTime()+"\n");
        
        

//        System.out.println(out.toString());
	}

}
