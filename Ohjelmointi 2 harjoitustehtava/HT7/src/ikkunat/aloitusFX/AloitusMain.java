package ikkunat.aloitusFX;

import java.io.File;

import javafx.application.Application;
//import javafx.application.Platform;
import javafx.stage.Stage;
import tietorakenne.Rekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import tietorakenne.SailoException;


/**
 * @author juuso
 * @version 29.1.2024
 *
 */
public class AloitusMain extends Application {
    
    private Rekisteri rekisteri;
    
    
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("AloitusGUIView.fxml"));
            final Pane root = ldr.load();
            final AloitusMainController aloitusCtrl = (AloitusMainController) ldr.getController(); 
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("aloitus.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Aloitus");        
            rekisteri = new Rekisteri();
            rekisteri.lueTiedostosta("asoyrekisteri");        
            aloitusCtrl.setRekisteri(rekisteri);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void stop() {
        try {
            File directory = new File("asoyrekisteri");
            if (!directory.exists()) {
                directory.mkdirs(); 
            }
            rekisteri.tallenna("asoyrekisteri");
        } catch (SailoException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}