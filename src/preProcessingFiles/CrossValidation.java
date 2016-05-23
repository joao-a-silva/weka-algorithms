/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preProcessingFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.Entity;
import utils.FilesJ;

import org.xml.sax.SAXException;






/**
 *
 * @author joao
 */
public class CrossValidation {

    private Map<String, ArrayList<Entity>> folds = new HashMap<String, ArrayList<Entity>>();

    /**
     *
     * @param path, diretorio com os arquivos já separados em os folds
     * @throws IOException
     * @throws SAXException
     */
    public void readFiles(String path, int numFold) throws IOException, SAXException {

        FilesJ fj = new FilesJ();

        for (int i = 0; i < numFold; i++) {
            String fold = path+i+".txt";        

            String[] contentLines = fj.getContentFile(fold).toString().split("\n");
            
            ArrayList<Entity> entitysFolds = new ArrayList<>();
            for (int j = 0; j < contentLines.length; j++) {
                String[] line = contentLines[j].split("; ");
                Entity e = new Entity(line[0], line[1], getDescription(line));
                entitysFolds.add(e);
            }
            folds.put(i + "", entitysFolds);

        }

    }

    public List<Entity> getTrain(int fold) {
        ArrayList<Entity> aux = new ArrayList<>();
        Set<String> keys = this.folds.keySet();

        for (String key : keys) {
            if (!key.equals("" + fold)) {
                aux.addAll(this.folds.get(key));
            }
        }

        return aux;
    }

    public List<Entity> getTest(int fold) {
        return this.folds.get(fold + "");
    }

    
    public String getDescription(String[] line) {
        StringBuilder sb = new StringBuilder();
        
        for(int i = 2; i < line.length; i++){
            if(i == line.length-1)sb.append(line[i]);
            else sb.append(line[i]).append("; ");
        }
        return sb.toString();
        
    }

}
