/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import config.FilesConfig;
import config.Parameters;

import java.io.IOException;

/**
 *
 * @author joao
 */
public class Tables {

    public static void saveMetricsTime(double macro, double micro, double timeTrain, double timeTest, double newClass) throws IOException {
        FilesJ fj = new FilesJ();
        double auxMacro = macro / Parameters.NUM_FOLDS;
        double auxMicro = micro / Parameters.NUM_FOLDS;
        double auxTrain = timeTrain / Parameters.NUM_FOLDS;
        double auxTest = timeTest / Parameters.NUM_FOLDS;
        double auxTrainTest = (timeTrain + timeTest) / Parameters.NUM_FOLDS;
        double auxNewClass = newClass / Parameters.NUM_FOLDS;

        fj.appendFile(FilesConfig.TABLES + "_MAC", "" + 100 * auxMacro);
        fj.appendFile(FilesConfig.TABLES + "_MIC", "" + 100 * auxMicro);
        fj.appendFile(FilesConfig.TABLES + "_Time", "" + auxTrain + "; " + auxTest + "; " + auxTrainTest);
        fj.appendFile(FilesConfig.TABLES + "_NewClass", ""+(int)auxNewClass);

    }

}
