/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import utils.FilesJ;


/**
 *
 * @author claudiane
 */
public class EvalAlgoritmo {

    private double micro = 0.0;
    private double macro = 0.0;
    private double txAcerto = 0.0;
    private double txErro = 0.0;
    private double notClassifier = 0.0;
    private double mdAcc = 0.0;
    private double mdPre = 0.0;
    private double mdRe = 0.0;
    private double mdF1 = 0.0;
   
   

//    private static String ARQ_TEST = "avaliacao/input/exemploLivro/test.";
//    private static String ARQ_PREDICT = "avaliacao/input/exemploLivro/predict.";
//    private static String ARQ_TEST = "avaliacao/input/exemploDoLivrocom3classes/test.";
//    private static String ARQ_PREDICT = "avaliacao/input/exemploDoLivrocom3classes/predict.";
    private static final String ARQ_TEST = "avaliacao/input/test.";
    private static final String ARQ_PREDICT = "avaliacao/input/predict.";

    //apenas para avaliar o cosseno e jaccard
    //private static String ARQ_PREDICTJAC = "avaliacao/input/JaccardClassificacao-";
//    private static final String ARQ_AVALIACAO_FOLD = "avaliacao/result/Resultado_Avaliacao";
    private static final String ARQ_AVALIACAO_FINAL = "avaliacao/result/Resultado_Avaliacao_Final";

    /**
     *
     * @param pathTest arquivo de teste
     * @param pathPredict arquivo predict
     * @param pathOut
     * @throws IOException
     */
    public void evaluationFold(String pathTest, String pathPredict, String pathOut) throws IOException {
        StringBuilder out = new StringBuilder();

        FileUtil.classeQuantidadeDocumento = new HashMap<>();

        //arquivo de teste e predicao para fazer o calculo das medidas
        BufferedReader fileTesteBuffered = FileUtil.readFile(pathTest);
        BufferedReader filePredictBuffered = FileUtil.readFile(pathPredict);

        //BufferedReader filePredictBufferedCOS = FileUtil.readFile(ARQ_PREDICTJAC + i+".txt");
        FileUtil.contaQuantDocsTeste(pathTest);

        String documentLinePredict;
        String documentLineTest;

        HashMap<String, Integer> classeA = new HashMap<>();
        HashMap<String, Integer> classeB = new HashMap<>();
        HashMap<String, Integer> gabarito = new HashMap<>();
        HashMap<String, Double> precisao = new HashMap<>();
        HashMap<String, Double> revocacao = new HashMap<>();
        HashMap<String, Double> accuracy = new HashMap<>();
        HashMap<String, Double> fMensurement = new HashMap<>();

        int igual = 0;
        int diferente = 0;
        int numDocsTotal = 0;

        while ((documentLinePredict = filePredictBuffered.readLine()) != null && (documentLineTest = fileTesteBuffered.readLine()) != null) {
            //while ((documentLinePredict = filePredictBufferedCOS.readLine()) != null && (documentLineTest = fileTesteBuffered.readLine()) != null) {
            if (documentLinePredict != null && !documentLinePredict.isEmpty() && documentLineTest != null && !documentLineTest.isEmpty()) {

                String docIdPredict = (documentLinePredict.split(" ")[0]);
                // para obter a classe predict do cosseno
                //String docIdPredict = (documentLinePredict.split(" ")[6]);
                //tratamento apenas para o cosseno
                docIdPredict = docIdPredict.replace(";", "");

                String docIdtTest = (documentLineTest.split(" ")[0]);

                if (classeB.containsKey(docIdPredict)) {
                    classeB.put(docIdPredict, classeB.get(docIdPredict) + 1);
                } else {
                    classeB.put(docIdPredict, 1);
                }

                if (docIdtTest.equals(docIdPredict)) {
                    if (classeA.containsKey(docIdtTest)) {
                        classeA.put(docIdtTest, classeA.get(docIdtTest) + 1);
                    } else {
                        classeA.put(docIdtTest, 1);
                    }
                    igual++;
                } else {
                    diferente++;
                }

                //contruindo a hash de docs totais
                if (gabarito.containsKey(docIdtTest)) {
                    gabarito.put(docIdtTest, gabarito.get(docIdtTest) + 1);
                } else {
                    gabarito.put(docIdtTest, 1);
                }

                numDocsTotal++;
            }

        }

        Integer NT = 0; // Nt
        Double somaNft = 0.0;
        Double somaNf = 0.0;
        Double somaNt = 0.0;

        Double somaAcc = 0.0;
        Double somaPre = 0.0;
        Double somaRe = 0.0;
        Double somaF1 = 0.0;

        //atenção aqui
        for (String classID : gabarito.keySet()) {
            NT += FileUtil.classeQuantidadeDocumento.get(classID);
            //System.out.println("NT: "+NT);
        }
//        System.out.println("NT: " + NT);

//
        out.append("Class;Accuracy;Precision;Recall;F1\n");
        for (String classID : gabarito.keySet()) {
            Integer nft = classeA.get(classID); // nf,t = treino e classificador associou a classe Cp
            if (nft == null) {
                nft = 0;

            }
            Integer nf = classeB.get(classID); // nf = num de docs treino associado a classe Cp pelo classificador
            Integer nt = FileUtil.classeQuantidadeDocumento.get(classID); // nt= num de docs do conjunto de treino associado a classe Cp

            if (nf == null) {

                nf = 0;
            }

//            System.out.println(" ################# Classe: " + classID);
//            System.out.println("nft: " + nft);
//            System.out.println("nf: " + nf);
//            System.out.println("nt: " + nt);
            Double p = 0.0;
            Double r = 0.0;

            if (nf != 0) {

                p = nft / Double.valueOf(nf);
            }

            if (nt == null) {

                nt = 0;
            }

            if (nt != 0) {

                r = nft / Double.valueOf(nt);
            }

            Double acc = (nft + (NT - nf - nt + nft)) / Double.valueOf(NT);
            Double pr = p + r;
            Double fmen = 0.0;

            if (pr != 0) {

                fmen = (2 * p * r) / (p + r);

            }

//                System.out.println("p: "+p);
//                System.out.println("r: "+r);
//                System.out.println("acc: "+acc);
//                System.out.println("fmen: "+fmen);
            // adicionando no objeto
            precisao.put(classID, p);
            revocacao.put(classID, r);
            accuracy.put(classID, acc);
            fMensurement.put(classID, fmen);

            // valores para o calculo da micF1
            somaNft += nft;
            somaNf += nf;
            somaNt += nt;

            // soma das medidas por classe para o cálculo da média
            somaAcc += acc;
            somaPre += p;
            somaRe += r;
            somaF1 += fmen;

            out.append(classID + ";" + acc + ";" + p + ";" + r + ";" + fmen).append("\n");
        }

        this.txAcerto = igual / Double.valueOf(NT);

        Double macro = 0.0;
        for (String p : fMensurement.keySet()) {
            macro += fMensurement.get(p);
        }

        this.macro = macro / fMensurement.keySet().size();

        Double pMicro = somaNft / somaNf;
        Double rMicro = somaNft / somaNt;
        this.micro = (2 * pMicro * rMicro) / (pMicro + rMicro);

        this.mdAcc = (somaAcc / gabarito.size());
        this.mdPre = (somaPre / gabarito.size());
        this.mdRe = (somaRe / gabarito.size());
        this.mdF1 = (somaF1 / gabarito.size());

//        System.out.println("Media Ac:" + mdAcc );
//        System.out.println("Media Precisao:"+ mdPre);
//        System.out.println("Media Revocaoa: "+ mdRe);
//        System.out.println("Media F1: "+ mdF1);
//        System.out.println("Macro;" + this.macro);
//        System.out.println("Micro;" + this.micro);
//        System.out.println("TxAcerto;" + this.txAcerto + ";" + igual + "/" + NT);

        //Adicionando no final do arquivo a média,as medidas macro e micro e tx de acertos por fold
        out.append("Media;" + mdAcc + ";" + mdPre + ";" + mdRe + ";" + mdF1);
        out.append("\nMacro;" + this.macro);
        out.append("\nMicro;" + this.micro);
        out.append("\nTxAcerto;" + this.txAcerto + ";" + igual + "/" + NT);

        FilesJ fj = new FilesJ();
        fj.writeFile(pathOut, out.toString());
//
//        out.close();
    }

    
    public double getMicro() {
        return micro;
    }

    public double getMacro() {
        return macro;
    }

    public double getTxAcerto() {
        return txAcerto;
    }

    public double getTxErro() {
        return txErro;
    }

    public double getNotClassifier() {
        return notClassifier;
    }

    public double getMdAcc() {
        return mdAcc;
    }

    public double getMdPre() {
        return mdPre;
    }

    public double getMdRe() {
        return mdRe;
    }

    public double getMdF1() {
        return mdF1;
    }
    
    

}
