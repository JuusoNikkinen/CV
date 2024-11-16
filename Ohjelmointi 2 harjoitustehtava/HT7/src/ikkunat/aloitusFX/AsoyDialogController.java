package ikkunat.aloitusFX;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tietorakenne.Asoy;

/**
 * @author juuso
 * @version 1.4.2024
 *
 */
public class AsoyDialogController implements ModalControllerInterface<Asoy>, Initializable {

    
    @FXML private TextField editAsoyNimi;
    @FXML private TextField editAsoyOsoite;
    @FXML private TextField editAsoyPostinumero;
    @FXML private TextField editAsoyPostitoimipaikka;
    @FXML private TextField editAsoyIsannoitsija;
    @FXML private TextField editAsoyHuolto;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();  
    }

    private void alusta() {
        edits = new TextField[] { editAsoyNimi, editAsoyOsoite, editAsoyPostinumero, editAsoyPostitoimipaikka, editAsoyIsannoitsija, editAsoyHuolto };
        
        editAsoyNimi.setOnKeyReleased                 (e -> kasitteleMuutosAsoy(1, editAsoyNimi));
        editAsoyOsoite.setOnKeyReleased               (e -> kasitteleMuutosAsoy(2, editAsoyOsoite));
        editAsoyPostinumero.setOnKeyReleased          (e -> kasitteleMuutosAsoy(3, editAsoyPostinumero));
        editAsoyPostitoimipaikka.setOnKeyReleased     (e -> kasitteleMuutosAsoy(4, editAsoyPostitoimipaikka));
        editAsoyIsannoitsija.setOnKeyReleased         (e -> kasitteleMuutosAsoy(5, editAsoyIsannoitsija));
        editAsoyHuolto.setOnKeyReleased               (e -> kasitteleMuutosAsoy(6, editAsoyHuolto));

    }

    private void kasitteleMuutosAsoy(int k, TextField edit) {
        if (asoyKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1: virhe = asoyKohdalla.setAsoyNimi(s); break;
            case 2: virhe = asoyKohdalla.setAsoyOsoite(s); break;
            case 3: virhe = asoyKohdalla.setAsoyPostinumero(s); break;
            case 4: virhe = asoyKohdalla.setAsoyPostitoimipaikka(s); break;
            case 5: virhe = asoyKohdalla.setAsoyIsannoitsija(s); break;
            case 6: virhe = asoyKohdalla.setAsoyHuolto(s); break;
            default: //
        }
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    /**
     * Tallenna asoytietoihin tehdyt muutokset
     */
    @FXML public void handleTallenna() {
        if (asoyKohdalla != null && asoyKohdalla.getAsoyNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä!");
            return;
        }
        
        ModalController.closeStage(editAsoyNimi);
    }
    
    /**
     * Peruuta tallentamatta asoytietoihin tehtyjä muutoksia
     */
    @FXML public void handlePeruuta() {
        asoyKohdalla = null;
        ModalController.closeStage(editAsoyNimi);
    }
    
    
    //===========================
    private Asoy asoyKohdalla;
    private TextField[] edits;
    
    @FXML private Label labelVirhe;
    
    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        //Dialogs.showMessageDialog(virhe);
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    @Override
    public Asoy getResult() {
        //
        return asoyKohdalla;
    }

    @Override
    public void handleShown() {
        //
        
    }

    @Override
    public void setDefault(Asoy oletus) {
        asoyKohdalla = oletus;
        naytaAsoy(edits, asoyKohdalla);
    }

    /**
     * @param modalityStage x
     * @param oletus x 
     * @return x 
     */
    public static Asoy kysyAsoy(Stage modalityStage, Asoy oletus) {
        return ModalController.showModal(AsoyValikkoController.class.getResource("AsoyDialogView.fxml"), "Asoy", modalityStage, oletus); 
    }

    
    
    /**
     * Täyttää asoy:n tiedot TextField komponentteihin
     * @param edits taulukko TextFieldeistä
     * @param asoy näytettävä asoy
     */
    public static void naytaAsoy(TextField[] edits, Asoy asoy) {
        if (asoy == null) return;
        edits[0].setText(asoy.getAsoyNimi());
        edits[1].setText(asoy.getAsoyOsoite());
        edits[2].setText(asoy.getAsoyPostinumero());
        edits[3].setText(asoy.getAsoyPostitoimipaikka());
        edits[4].setText(asoy.getAsoyIsannoitsija());
        edits[5].setText(asoy.getAsoyHuolto());

    }

}
