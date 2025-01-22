package ikkunat.aloitusFX;

import java.io.PrintStream;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import tietorakenne.Rekisteri;





/**
 * @author juuso
 * @version 14.2.2024
 *
 */
public class TulostusGUIController implements ModalControllerInterface <Integer> {
    

    
    @FXML void handleTulosta(@SuppressWarnings("unused") ActionEvent event) {
        //Dialogs.showMessageDialog("Tulostus ei vielä käytössä");
        
        
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTuloste)) {
            //Asukas asukas = new Asukas();
            tulostaAsoyAsukkaat(os);
        }
            

    }

    
    @FXML private TextArea areaTuloste;
    
    @FXML private CheckBox printAsuntoOsake;
    @FXML private CheckBox printNimi;
    @FXML private CheckBox printSyntymavuosi;
    @FXML private CheckBox printPuhelinnumero;
    @FXML private CheckBox printEmail;
    @FXML private CheckBox printOsakeM2;
    @FXML private CheckBox printVastike;
    @FXML private CheckBox printVastikeM2;
    @FXML private CheckBox printVarasto;
    @FXML private CheckBox printSaunaoikeus;
    @FXML private CheckBox printPesutupa;
    @FXML private CheckBox printHallitus;
    @FXML private CheckBox printLiittymisvuosi;

    private Integer asoyId;

    private Rekisteri rekisteri;
    
    /** asettaa rekisterin
     * @param rekisteri käytettävä rekisteri
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
    }

    
    @Override
    public Integer getResult() {
        //
        return null;
    }

    @Override
    public void handleShown() {
        //
        
    }

    @Override
    public void setDefault(Integer oletus) {
        this.asoyId = oletus;
    }
    
    /**
     * @param os tulostevirta (tuloste paneeli)
     */
    public void tulostaAsoyAsukkaat(PrintStream os) {
        
        areaTuloste.clear();
        
        int[] tulostettavatTiedot = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };


        if (printAsuntoOsake.isSelected()) {
            tulostettavatTiedot[0] = 1;
        }
            
        if (printNimi.isSelected()) {
            tulostettavatTiedot[1] = 1;
        }
        
        if (printSyntymavuosi.isSelected()) {
            tulostettavatTiedot[2] = 1;
        }
        
        if (printPuhelinnumero.isSelected()) {
            tulostettavatTiedot[3] = 1;
        }
        
        if (printEmail.isSelected()) {
            tulostettavatTiedot[4] = 1;
        }
        
        if (printOsakeM2.isSelected()) {
            tulostettavatTiedot[5] = 1;
        }
        
        if (printVastike.isSelected()) {
            tulostettavatTiedot[6] = 1;
        }
        
        if (printVastikeM2.isSelected()) {
            tulostettavatTiedot[7] = 1;
        }
        if (printVarasto.isSelected()) {
            tulostettavatTiedot[8] = 1;
        }
        if (printSaunaoikeus.isSelected()) {
            tulostettavatTiedot[9] = 1;
        }
        if (printPesutupa.isSelected()) {
            tulostettavatTiedot[10] = 1;
        }
        if (printHallitus.isSelected()) {
            tulostettavatTiedot[11] = 1;
        }
        if (printLiittymisvuosi.isSelected()) {
            tulostettavatTiedot[12] = 1;
        }
        

        String tuloste = rekisteri.haeAsukastietoTuloste(asoyId, tulostettavatTiedot);

        os.print(tuloste);
        
        
        //os.println("----------------");

    }


    
}