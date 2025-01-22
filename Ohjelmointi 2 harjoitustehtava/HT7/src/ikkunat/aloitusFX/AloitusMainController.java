package ikkunat.aloitusFX;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tietorakenne.Rekisteri;

/**
 * @author juuso
 * @version 29.1.2024
 *
 */
public class AloitusMainController {

    
    //Aloitus
    @FXML
    private Button textHaeAsoy;
    @FXML private TextField hakuAsoyNimi;

    @FXML
    void handleSelaaAsoy() {
        selaaAsoy(); //ASOYLISTA
    }
    
    @FXML
    void handleHae() {
        haeAsoy(); //ASUKASLISTA
    }
       
    
    
    private Rekisteri rekisteri;
    
    private void haeAsoy() {

        String asoyNimi = hakuAsoyNimi.getText();
        
        if (asoyNimi.isEmpty()) {
            Dialogs.showMessageDialog("Asunto-osakeyhtiötä ei löydy.");
            return;
        }
        
        int verrattavaAsoyId = rekisteri.haeAsoyId(asoyNimi);

        
        if (verrattavaAsoyId != -1) {
            
            ModalController.<Rekisteri,AsukasValikkoController>showModal(AloitusMainController.class.getResource("AsoyValikkoView.fxml"), "ASOY Muokkaus",
                    null, rekisteri,ctrl->ctrl.setHaettuAsoyTiedot(verrattavaAsoyId));
            
        }
        else {
            Dialogs.showMessageDialog("Asunto-osakeyhtiötä ei löydy.");
        }
        
        
    }
    
    private void selaaAsoy() {
        ModalController.showModal(AloitusMainController.class.getResource("MuokkaaAsoyGUIView.fxml"), "ASOY Lista", null, rekisteri);
    }

    /**
     * Asettaa käytettävän rekisterin
     * @param rekisteri käytettävä rekisteri
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
    }
    

}