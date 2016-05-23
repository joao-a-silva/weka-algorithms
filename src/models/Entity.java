/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author joaoantoniosilva
 */
public class Entity {
    
    private String realName;
    private String instanceID;
    private String description;
    private String predictName;
    private String rules;
    private String probSVM;
    private String classSVM;
    private double simJaccard;
    private double simCosino;
    
    
    
    

    public Entity() {
        this.simJaccard = 0.0;
    }
    
    
    

    public Entity(String entityRealName, String entityCode, String enityDescription) {
        this.realName = entityRealName;
        this.instanceID = entityCode;
        this.description = enityDescription;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPredictName() {
        return predictName;
    }

    public void setPredictName(String predictName) {
        this.predictName = predictName;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getProbSVM() {
        return probSVM;
    }

    public void setProbSVM(String probSVM) {
        this.probSVM = probSVM;
    }

    public String getClassSVM() {
        return classSVM;
    }

    public void setClassSVM(String classSVM) {
        this.classSVM = classSVM;
    }

    public double getSimJaccard() {
        return simJaccard;
    }

    public void setSimJaccard(double simJaccard) {
        this.simJaccard = simJaccard;
    }

    public double getSimCosino() {
        return simCosino;
    }

    public void setSimCosino(double simCosino) {
        this.simCosino = simCosino;
    }
    
    
    
    
    
    
}
