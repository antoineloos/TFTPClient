/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javafx.application.Platform;

/**
 *
 * @author Epulapp
 */
public final class Logger {
    
    
    
    public static void LogToUI(String messge)
    {
        Platform.runLater(new Runnable() {
          @Override public void run() {

          }
        });
    }
}
