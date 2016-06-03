/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import models.Entity;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author joao
 */
public class Evaluation {

    private HashMap<String, Integer> classeQuantidadeDocumento = new HashMap<>();
    private double micro = 0.0;
    private double macro = 0.0;
    private double txAcerto;
    private double txErro;
    private double notClassifier;

    //arquivo de teste e predicao para fazer o calculo das medidas
    public void evaluation(List<Entity> entity) throws IOException {

        this.contaQuantDocsTeste(entity);

        HashMap<String, Integer> classeA = new HashMap<>();
        HashMap<String, Integer> classeB = new HashMap<>();

        HashMap<String, Double> precisao = new HashMap<>();
        HashMap<String, Double> revocacao = new HashMap<>();
        HashMap<String, Double> accuracy = new HashMap<>();
        HashMap<String, Double> fMensurement = new HashMap<>();

        int correct = 0;
        int error = 0;

        for (Entity entityTest : entity) {
            
            if(entityTest.getRealName().equals(entityTest.getFinalPrediction())){
            
                correct++;                
            }else{
                if( entityTest.getFinalPrediction()!= null && !entityTest.getFinalPrediction().equals(entityTest.getRealName()) ){
                   
                  error++;
                }else{
                    if(entityTest.getFinalPrediction() == null){
                        
                        this.notClassifier++;
                    }
                }
            }
            

            if (classeB.containsKey(entityTest.getFinalPrediction())) {
                classeB.put(entityTest.getFinalPrediction(), classeB.get(entityTest.getFinalPrediction()) + 1);
            } else {
                classeB.put(entityTest.getFinalPrediction(), 1);
            }

            if (entityTest.getRealName().equals(entityTest.getFinalPrediction())) {
                if (classeA.containsKey(entityTest.getRealName())) {
                    classeA.put(entityTest.getRealName(), classeA.get(entityTest.getRealName()) + 1);
                } else {
                    classeA.put(entityTest.getRealName(), 1);
                }
              
            } else {
                
            }
        }

        txAcerto = (double) correct / (correct + error);
        txErro = (double) error / (correct + error);

        Integer NT = 0; // Nt
        Double somaA = 0.0;
        Double somaB = 0.0;
        Double somaC = 0.0;

        for (String classID : classeA.keySet()) {
            NT += this.classeQuantidadeDocumento.get(classID);
        }

        for (String classID : classeA.keySet()) {
            Integer a = classeA.get(classID); // nf,t
            Integer b = classeB.get(classID); // nf
            Integer c = this.classeQuantidadeDocumento.get(classID); // nt

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
//        System.out.println("Precisao");
//        System.out.println(sbPrecisao);

        StringBuilder sbRevocacao = new StringBuilder();

        for (String p : revocacao.keySet()) {
            sbRevocacao.append(p + " " + revocacao.get(p) + "\n");
        }
//        System.out.println("Revocacao");
//        System.out.println(sbRevocacao);

        StringBuilder sbAcuracy = new StringBuilder();

        for (String p : accuracy.keySet()) {
            sbAcuracy.append(p + " " + accuracy.get(p) + "\n");
        }
//        System.out.println("Acuracy");
//        System.out.println(sbAcuracy);

        StringBuilder sbMensurement = new StringBuilder();

        for (String p : fMensurement.keySet()) {
            sbMensurement.append(p + " " + fMensurement.get(p) + "\n");
            macro += fMensurement.get(p);
        }

        this.macro = macro / fMensurement.keySet().size();
//        System.out.println(macro);
//        System.out.println("Fmensurement");
//        System.out.println(sbMensurement);

        Double pMicro = somaA / somaB;
        Double rMicro = somaA / somaC;
        this.micro = (2 * pMicro * rMicro) / (pMicro + rMicro);
        System.out.println("MicroF1 " + micro);

    }

    public void contaQuantDocsTeste(List<Entity> entitys) throws IOException {

        String documentLineTest;

        for (Entity entity : entitys) {
            if (classeQuantidadeDocumento.containsKey(entity.getRealName())) {
                classeQuantidadeDocumento.put(entity.getRealName(), classeQuantidadeDocumento.get(entity.getRealName()) + 1);
            } else {
                classeQuantidadeDocumento.put(entity.getRealName(), 1);
            }
        }

        System.out.println("quant. Classes: " + classeQuantidadeDocumento.size());
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

}
