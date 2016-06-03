/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author claudiane
 */
public class Main {

//    private static String fileTeste = "avaliacao/svm/tvsvm.te1";
//    private static String filePredict = "avaliacao/svm/tvsvm.te1.predict";
    
    private static String fileTeste = "saida/avaliacao/regras/test.0";
    private static String filePredict = "saida/avaliacao/regras/predict.0";
    
    

    public static void main(String[] args) throws IOException {

        //arquivo de teste e predicao para fazer o calculo das medidas
        BufferedReader fileTesteBuffered = FileUtil.readFile(fileTeste);
        BufferedReader filePredictBuffered = FileUtil.readFile(filePredict);
        
        FileUtil.contaQuantDocsTeste(fileTeste);

        String documentLinePredict;
        String documentLineTest;
        
        HashMap<String, Integer> classeA = new HashMap<>();
        HashMap<String, Integer> classeB = new HashMap<>();
        HashMap<String, Double> precisao = new HashMap<>();
        HashMap<String, Double> revocacao = new HashMap<>();
        HashMap<String, Double> accuracy = new HashMap<>();
        HashMap<String, Double> fMensurement = new HashMap<>();

        int igual = 0;
        int diferente = 0;
        
        while ((documentLinePredict = filePredictBuffered.readLine()) != null && (documentLineTest = fileTesteBuffered.readLine()) != null) {
            if (documentLinePredict != null && !documentLinePredict.isEmpty() && documentLineTest != null && !documentLineTest.isEmpty()) {

                String docIdPredict = (documentLinePredict.split(" ")[0]);
                String docIdtTest = (documentLineTest.split(" ")[0]);
                
//                Integer docIdPredict = Integer.valueOf(documentLinePredict.split(" ")[0]);
//                Integer docIdtTest = Integer.valueOf(documentLineTest.split(" ")[0]);

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
            }
        }

        Integer NT = 0; // Nt
        Double somaA = 0.0;
        Double somaB = 0.0;
        Double somaC = 0.0;

        for (String classID : classeA.keySet()) {
            NT += FileUtil.classeQuantidadeDocumento.get(classID);
        }

        for (String classID : classeA.keySet()) {
            Integer a = classeA.get(classID); // nf,t
            Integer b = classeB.get(classID); // nf
            Integer c = FileUtil.classeQuantidadeDocumento.get(classID); // nt

            Double p = a / Double.valueOf(b);
            Double r = a / Double.valueOf(c);
            Double acc = (a + (NT - b - c + a)) / Double.valueOf(NT);
            Double fmen = (2 * p * r) / (p + r);

            precisao.put(classID, p);
            revocacao.put(classID, r);
            accuracy.put(classID, acc);
            fMensurement.put(classID, fmen);

            somaA += a;
            somaB += b;
            somaC += c;
        }

        StringBuilder sbPrecisao = new StringBuilder();

        for (String p : precisao.keySet()) {
            sbPrecisao.append(p + " " + precisao.get(p) + "\n");
        }
        System.out.println("Precisao");
        System.out.println(sbPrecisao);

        StringBuilder sbRevocacao = new StringBuilder();

        for (String p : revocacao.keySet()) {
            sbRevocacao.append(p + " " + revocacao.get(p) + "\n");
        }
        System.out.println("Revocacao");
        System.out.println(sbRevocacao);

        StringBuilder sbAcuracy = new StringBuilder();

        for (String p : accuracy.keySet()) {
            sbAcuracy.append(p + " " + accuracy.get(p) + "\n");
        }
        System.out.println("Acuracy");
        System.out.println(sbAcuracy);

        StringBuilder sbMensurement = new StringBuilder();

        Double macro = 0.0;
        for (String p : fMensurement.keySet()) {
            sbMensurement.append(p + " " + fMensurement.get(p) + "\n");
            macro += fMensurement.get(p);
        }

        macro = macro / fMensurement.keySet().size();
        System.out.println(macro);
        System.out.println("Fmensurement");
        System.out.println(sbMensurement);

        Double pMicro = somaA / somaB;
        Double rMicro = somaA / somaC;
        Double micro = (2 * pMicro * rMicro) / (pMicro + rMicro);
        System.out.println(micro);

    }
}
