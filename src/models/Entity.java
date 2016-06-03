/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author joaoantoniosilva
 */
public class Entity implements Comparable<Entity> {

    private String realName;
    private String instanceID;
    private String description;
    private String finalPrediction, predictedSVM, predictedRules, predictSimNewClass;
    private String rules;
    private Set<String> setRulesClassChosen = new HashSet<>();
    private double simProb, perFinalRule;

  //  private Map<String, ArrayList<Prediction>> topSimilaritys;

    private boolean findRules;

    public Entity() {
        this.findRules = false;
        //this.topSimilaritys = new HashMap<>();
    }

    public Entity(String entityRealName, String entityCode, String entityDescription) {
        this.realName = entityRealName;
        this.instanceID = entityCode;
        this.description = entityDescription;
   //     this.topSimilaritys = new HashMap<>();
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

    public String getFinalPrediction() {
        return finalPrediction;
    }

    public void setFinalPrediction(String finalPrediction) {
        this.finalPrediction = finalPrediction;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getPredictedSVM() {
        return predictedSVM;
    }

    public void setPredictedSVM(String predictedSVM) {
        this.predictedSVM = predictedSVM;
    }

    public String getPredictedRules() {
        return predictedRules;
    }

    public void setPredictedRules(String predictedRules) {
        this.predictedRules = predictedRules;
    }

    public double getSimProb() {
        return simProb;
    }

    public void setSimProb(double simProb) {
        this.simProb = simProb;
    }

    public boolean isFindRules() {
        return findRules;
    }

    public void setFindRules(boolean findRules) {
        this.findRules = findRules;
    }

	@Override
	public int compareTo(Entity o) {
		// TODO Auto-generated method stub
		return 0;
	}

    
    /*
    public ArrayList<Prediction> getTopSimilaritys(String key) {
        return topSimilaritys.get(key);
    }

    public void addSimilaritys(String key, Prediction p) {
        if (this.topSimilaritys.containsKey(key)) {
            this.topSimilaritys.get(key).add(p);
        } else {
            ArrayList<Prediction> aux = new ArrayList<>();
            aux.add(p);
            this.topSimilaritys.put(key, aux);
        }
    }

    public void addClassifier(String key, ArrayList<Prediction> p) {

        if (this.topSimilaritys.containsKey(key)) {
            this.topSimilaritys.get(key).addAll(p);
        } else {
            this.topSimilaritys.put(key, p);
        }

    }

    public void clearPredicitons() {
        this.topSimilaritys.clear();
    }

    public Map<String, ArrayList<Prediction>> getTopSimilaritys() {
        return topSimilaritys;
    }

    public double getPerFinalRule() {
        return perFinalRule;
    }

    public void setPerFinalRule(double perFinalRule) {
        this.perFinalRule = perFinalRule;
    }

    public void cloneEntity(Entity entity) {
        this.realName = entity.getRealName();
        this.instanceID = entity.getInstanceID();
        this.description = entity.getDescription();
    }

    public Set<String> getSetRulesClassChosen() {
        return setRulesClassChosen;
    }

    public void setSetRulesClassChosen(Set<String> setRulesClassChosen) {
        this.setRulesClassChosen = setRulesClassChosen;
    }

    @Override
    public int compareTo(Entity o) {

        if (this.simProb > o.getSimProb()) {
            return 1;
        }

        if (this.simProb < o.getSimProb()) {
            return -1;
        }

        long thisBits = Double.doubleToLongBits(this.simProb);
        long anotherBits = Double.doubleToLongBits(o.getSimProb());

        return (thisBits == anotherBits ? 0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                        1));

    }

    public String getPredictSimNewClass() {
        return predictSimNewClass;
    }

    public void setPredictSimNewClass(String predictSimNewClass) {
        this.predictSimNewClass = predictSimNewClass;
    }
    
    
    */
    

}
