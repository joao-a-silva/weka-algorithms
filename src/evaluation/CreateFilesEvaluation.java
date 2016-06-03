/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import models.Entity;
import java.io.IOException;
import java.util.Map;
import utils.FilesJ;



/**
 *
 * @author joao
 */
public class CreateFilesEvaluation {

    private double correct;
    private double error;
    private double notClassifier;
    private String pathNewClass, pathTest, pathPredict;
    private Map<String, String> instanciasClasse;

    public CreateFilesEvaluation(String pathNewClass, String pathTest, String pathPredict) throws IOException {
        this.correct = 0.0;
        this.error = 0.0;
        this.notClassifier = 0.0;
        this.pathTest = pathTest;
        this.pathPredict = pathPredict;
        FilesJ fj = new FilesJ();
        fj.writeFile(pathNewClass, "");
        fj.writeFile(pathTest, "");
        fj.writeFile(pathPredict, "");

    }
    
    
    public CreateFilesEvaluation(String pathPredict, String pathTest) throws IOException {
        this.correct = 0.0;
        this.error = 0.0;
        this.notClassifier = 0.0;
        this.pathTest = pathTest;
        this.pathPredict = pathPredict;
        FilesJ fj = new FilesJ();
        fj.writeFile(pathTest, "");
        fj.writeFile(pathPredict, "");

    }

    public void savePredictionsNewClass(Entity entity) throws IOException {
        StringBuilder outPredictions = new StringBuilder();
        outPredictions.append(entity.getRealName()).append("; ").append(entity.getFinalPrediction()).append("; ");
        FilesJ rf = new FilesJ();
        rf.appendFile(this.pathNewClass, outPredictions.toString());
    }
    
    

    public void createFilesEvaluation(Entity entity) throws IOException {
        StringBuilder outTestFile = new StringBuilder();

        outTestFile.append(entity.getRealName()).append(" ").append(entity.getDescription());

        FilesJ rf = new FilesJ();
        rf.appendFile(this.pathTest, outTestFile.toString());
        if (entity.getFinalPrediction() != null) {
            rf.appendFile(this.pathPredict, entity.getFinalPrediction());
        } else {
            rf.appendFile(this.pathPredict, "null");
        }

    }
    
    
    

    public double getCorrect() {
        return correct;
    }

    public double getError() {
        return error;
    }

    public double getNotClassifier() {
        return notClassifier;
    }

    public void setInstanciasClasse(Map<String, String> instanciasClasse) {
        this.instanciasClasse = instanciasClasse;
    }

   /* public void setRulesClasse(Map<String, String> rulesClasse) {
        this.rulesClasse = rulesClasse;
    }*/

}
