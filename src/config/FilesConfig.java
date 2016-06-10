/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author joaoantoniosilva
 */
public class FilesConfig {

    /**
     * *************************************************************************
     * Bases de dados
     * *************************************************************************
     */
//    Com codigo
   // public static String IMPUT_CLASSIFIER = "files/datasets/comCodigo/10-Produtos_Com_Codigo-Fold-";
	
	
//    sem codigo
//    public static String IMPUT_CLASSIFIER = "files/datasets/uolNonEletronic/14-Produtos-Sem-Codigos-Fold-";

	//     printers
    public static String IMPUT_CLASSIFIER = "files/datasets/printers/17-Printers-Fold-";

 
    /* *************************************************************************
     * Arquivos Weka/MOA
     ***************************************************************************
     */
    
    public static String W_TRAIN = "files/datasets/weka/training";
    public static String W_TEST = "files/datasets/weka/test";
    
    
    
     /* *************************************************************************
     * Files for the Evaluation
     ***************************************************************************
     */
    
    public static String TEST_FILE ="files/evaluation/newClass/test/test_";
    public static String PREDICT_FILE ="files/evaluation/newClass/predict/predict_";
    public static String FOLD_RESULT ="files/evaluation/newClass/resultFold/fold_";
    public static String PREDICT_NEW_FILE = "files/evaluation/newClass/predict/predict_new_";
    public static String TABLES = "files/evaluation/tablesMetricsTime/tables";
    
    
    
}
