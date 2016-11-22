/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1arsir;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tp1arsir.STF.SendFile;

/**
 *
 * @author Epulapp
 */
public class View implements Observer {
    
        private File fileChosen;
        private FileChooser filechs ;
        private JFXButton OpenBtn ;
        private JFXButton SendBtn;
        private JFXButton ReceiveBtn;
        private JFXButton SaveIPBtn;
        private StackPane root;
        private FlowPane pane;
        private JFXTextArea textBox;
        private Label Feedback;
        private String courantIP; 
        
    public View(Stage stage)
    {
        root = new StackPane();
        pane = new FlowPane();
        pane.setOrientation(Orientation.VERTICAL);
        pane.setAlignment(Pos.CENTER);
        Feedback = new Label();
        textBox = new JFXTextArea();
        textBox.setPrefHeight(50);
        textBox.setPrefWidth(150);
        filechs = new FileChooser();
        String style = " -fx-border-color: white; " + "-fx-border-width: 3;" + "-fx-background-color: #6AA6E2;" + "-fx-spacing: 5px;" + "-fx-text-fill: white;";
        
        //OpenBtn
        OpenBtn = new JFXButton();
        OpenBtn.setPrefHeight(50);
        OpenBtn.setPrefWidth(150);
        OpenBtn.setStyle(style);

        OpenBtn.setText("Ouvrir fichier");
        OpenBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
               fileChosen = filechs.showOpenDialog(stage);
            }
        });
        
        //SaveIPBtn
        SaveIPBtn = new JFXButton();
        SaveIPBtn.setPrefHeight(50);
        SaveIPBtn.setPrefWidth(150);
        SaveIPBtn.setStyle(style);

        SaveIPBtn.setText("enregistrer l'IP");
        SaveIPBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
             courantIP = textBox.getText();
            }
        });
        
        //SendBtn
        SendBtn = new JFXButton();
        
        SendBtn.setStyle(style);
        SendBtn.setPrefHeight(50);
        SendBtn.setPrefWidth(150);
        SendBtn.setText("envoyer le fichier");
        SendBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if(fileChosen != null)
                {
                    SendFile.setCourantIP(courantIP);
                    SendFile.SendFile(fileChosen);
                }
            }
        });
        pane.getChildren().add(textBox);
        pane.getChildren().add(SaveIPBtn);
        pane.getChildren().add(OpenBtn);
        pane.getChildren().add(SendBtn);
        pane.getChildren().add(Feedback);
        root.getChildren().add(pane);
        
    }
        
        
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the root
     */
    public StackPane getRoot() {
        return root;
    }
}
