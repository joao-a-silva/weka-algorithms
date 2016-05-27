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

    //public static String IMPUT_XML = "files/xmlDados/testeCross/comCodigo/10-Produtos_Com_Codigo-Fold-";
   // public static String IMPUT_CLASSIFIER = "files/imput/comCodigo/10-Produtos_Com_Codigo-Fold-";
//    sem codigo
//    public static String IMPUT_XML = "files/xmlDados/testeCross/semcodigo/14-Produtos-Sem-Codigos-Fold-";
//    public static String IMPUT_CLASSIFIER = "files/imput/semcodigo/14-Produtos-Sem-Codigos-Fold-";

	//     printers
      //public static String IMPUT_XML = "files/xmlDados/testeCross/printers/17-Printers-Fold-";
<<<<<<< HEAD
    //  public static String IMPUT_CLASSIFIER = "files/datasets/printers/17-Printers-Fold-";
=======
      //public static String IMPUT_CLASSIFIER = "files/datasets/printers/17-Printers-Fold-";
>>>>>>> da18b3f705342dd1c41ec89171e58d1ceb72230c

     // public static String IMPUT_XML = "files/xmlDados/testeCross/comCodigo/10-Produtos_Com_Codigo-Fold-";
     // public static String IMPUT_CLASSIFIER = "files/imput/comCodigo/10-Produtos_Com_Codigo-Fold-";
//    sem codigo
<<<<<<< HEAD
//    public static String IMPUT_XML = "files/xmlDados/testeCross/semcodigo/14-Produtos-Sem-Codigos-Fold-";
    public static String IMPUT_CLASSIFIER = "files/imput/semcodigo/14-Produtos-Sem-Codigos-Fold-";
=======
  //    public static String IMPUT_XML = "files/xmlDados/testeCross/semcodigo/14-Produtos-Sem-Codigos-Fold-";
      public static String IMPUT_CLASSIFIER = "files/datasets/uolNonEletronic/14-Produtos-Sem-Codigos-Fold-";
>>>>>>> da18b3f705342dd1c41ec89171e58d1ceb72230c
//     printers
//    public static String IMPUT_XML = "files/xmlDados/testeCross/printers/17-Printers-Fold-";
//    public static String IMPUT_CLASSIFIER = "files/imput/printers/17-Printers-Fold-";

    
//       //books
//    public static String IMPUT_XML = "files/xmlDados/testeCross/printers/17-Printers-Fold-";
//    public static String IMPUT_CLASSIFIER = "files/imput/books/15-Livros-Fold-";

    /* *************************************************************************
     * Arquivos - Experimento só com Regras
     *************************************************************************
     */
    public static String RULES_100 = "files/experiments/rules/rules100/rulesFold_";
    public static String RULES_LOWER_SUPPORT = "files/experiments/rules/rulesLowSopport/rulesFold_";
    public static String RULES_TEST_FILE = "files/evaluation/rules/test/test_";
    public static String RULES_PREDICT_FILE = "files/evaluation/rules/predict/predict_";
    public static String RULES_FOLD_RESULT = "files/evaluation/rules/resultFold/fold_";

    /* *************************************************************************
     * Arquivos - Experimento com SVM
     *************************************************************************
     */
    public static String SVM_TREINO = "files/experiments/SVM/train/trainFold_";
    public static String SVM_TESTE = "files/experiments/SVM/test/testFold_";
    public static String SVM_UNCLASSIFIED = "files/experiments/SVM/unclassified/uncFold_";
    public static String SVM_CLASSE_MAPEADAS = "files/experiments/SVM/classes/classesMapeadasFold_";
    public static String SVM_RULES_TEST = "files/experiments/SVM/rulesTest/rulesFold_";
    public static String SVM_RULES_TRAIN = "files/experiments/SVM/rules100/rulesFold_";
    public static String SVM_RULES_LOWWER_SUPPORT = "files/experiments/SVM/rulesLowSupport/rulesFold_";
    public static String SVM_TEMP_FILES = "files/experiments/SVM/temp/unclassified.arff";
    public static String SVM_TEST_FILE = "files/evaluation/svm/test/test_";
    public static String SVM_PREDICT_FILE = "files/evaluation/svm/predict/predict_";
    public static String SVM_FOLD_RESULT = "files/evaluation/svm/resultFold/fold_";


    /* *************************************************************************
     * Arquivos Jaccard
     ***************************************************************************
     */
    public static String JACCARD_TEST_FILE = "files/evaluation/jaccard/test/test_";
    public static String JACCARD_PREDICT_FILE = "files/evaluation/jaccard/predict/predict_";
    public static String JACCARD_FOLD_RESULT = "files/evaluation/jaccard/resultFold/fold_";
    
    /* *************************************************************************
     * Arquivos Cosseno
     ***************************************************************************
     */
    public static String COSINE_TEST_FILE = "files/evaluation/cosine/test/test_";
    public static String COSINE_PREDICT_FILE = "files/evaluation/cosine/predict/predict_";
    public static String COSINE_FOLD_RESULT = "files/evaluation/cosine/resultFold/fold_";

    /* *************************************************************************
     * Arquivos Weka
     ***************************************************************************
     */
    

    public static String W_TRAIN = "files/datasets/weka/training";
    public static String W_TEST = "files/datasets/weka/test";


    
     /* *************************************************************************
     * Arquivos JavaMl
     ***************************************************************************
     */
    
    public static String TRAINJML = "files/imput/jml/train";
    public static String TESTJML = "files/imput/jml/test";
    
    public static String JML_TEST_FILE = "files/evaluation/jml/test/test_";
    public static String JML_PREDICT_FILE = "files/evaluation/jml/predict/predict_";
    public static String JML_FOLD_RESULT = "files/evaluation/jml/resultFold/fold_";
    
}
