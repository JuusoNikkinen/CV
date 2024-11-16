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
import tietorakenne.Asukas;

/**
 * @author juuso
 * @version 1.4.2024
 *
 */
public class AsukasDialogController implements ModalControllerInterface<Asukas>, Initializable {

    
    @FXML private TextField editRappu;
    @FXML private TextField editAsuntoNro;
    @FXML private TextField editNimi;
    @FXML private TextField editHetu;
    @FXML private TextField editPuhelin;
    @FXML private TextField editEmail;
    @FXML private TextField editOsakeM2;
    @FXML private TextField editYhtiovastike;
    @FXML private TextField editYhtiovastikeM2;
    @FXML private TextField editHakkivarasto;
    @FXML private TextField editSaunaoikeus;
    @FXML private TextField editPyykkitupa;
    @FXML private TextField editHallitus;
    @FXML private TextField editLiittymisvuosi;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();  
    }

    private void alusta() {
        edits = new TextField[] {editRappu, editAsuntoNro, editNimi, editHetu, editPuhelin, editEmail, editOsakeM2, 
                editYhtiovastike, editYhtiovastikeM2, editHakkivarasto, editSaunaoikeus, editPyykkitupa, editHallitus, editLiittymisvuosi };
        
        editRappu.setOnKeyReleased          (e -> kasitteleMuutosAsukas(1, editRappu));
        editAsuntoNro.setOnKeyReleased      (e -> kasitteleMuutosAsukas(2, editAsuntoNro));
        editNimi.setOnKeyReleased           (e -> kasitteleMuutosAsukas(3, editNimi));
        editHetu.setOnKeyReleased           (e -> kasitteleMuutosAsukas(4, editHetu));
        editPuhelin.setOnKeyReleased        (e -> kasitteleMuutosAsukas(5, editPuhelin));
        editEmail.setOnKeyReleased          (e -> kasitteleMuutosAsukas(6, editEmail));
        editOsakeM2.setOnKeyReleased        (e -> kasitteleMuutosAsukas(7, editOsakeM2));
        editYhtiovastike.setOnKeyReleased   (e -> kasitteleMuutosAsukas(8, editYhtiovastike));
        editYhtiovastikeM2.setOnKeyReleased (e -> kasitteleMuutosAsukas(9, editYhtiovastikeM2));
        editHakkivarasto.setOnKeyReleased   (e -> kasitteleMuutosAsukas(10, editHakkivarasto));
        editSaunaoikeus.setOnKeyReleased    (e -> kasitteleMuutosAsukas(11, editSaunaoikeus));
        editPyykkitupa.setOnKeyReleased     (e -> kasitteleMuutosAsukas(12, editPyykkitupa));
        editHallitus.setOnKeyReleased       (e -> kasitteleMuutosAsukas(13, editHallitus));
        editLiittymisvuosi.setOnKeyReleased (e -> kasitteleMuutosAsukas(14, editLiittymisvuosi));
    }

    private void kasitteleMuutosAsukas(int k, TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1: virhe = asukasKohdalla.setRappu(s); break;
            case 2: virhe = asukasKohdalla.setAsuntoNro(s); break;
            case 3: virhe = asukasKohdalla.setNimi(s); break;
            case 4: virhe = asukasKohdalla.setHetu(s); break;
            case 5: virhe = asukasKohdalla.setPuhelin(s); break;
            case 6: virhe = asukasKohdalla.setEmail(s); break;
            case 7: virhe = asukasKohdalla.setOsakeM2(s); break;
            //case 8: virhe = asukasKohdalla.setYhtiovastike(s); break;
            case 9: virhe = asukasKohdalla.setYhtiovastikeM2(s); break;
            case 10: virhe = asukasKohdalla.setVarasto(s); break;
            case 11: virhe = asukasKohdalla.setSaunaoikeus(s); break;
            case 12: virhe = asukasKohdalla.setPyykkitupa(s); break;
            case 13: virhe = asukasKohdalla.setHallitus(s); break;
            case 14: virhe = asukasKohdalla.setLiittymisvuosi(s); break;
            default: //
        }
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }
/*
    private void kasitteleMuutosHetu(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setHetu(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosPuhelin(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setPuhelin(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosEmail(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setEmail(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosOsakeM2(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setOsakeM2(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosYhtiovastike(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setYhtiovastike(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosYhtiovastikeM2(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setYhtiovastikeM2(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosHakkivarasto(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setVarasto(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosSaunaoikeus(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setSaunaoikeus(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosPyykkitupa(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setPyykkitupa(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosHallitus(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setHallitus(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }

    private void kasitteleMuutosLiittymisvuosi(TextField edit) {
        if (asukasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = asukasKohdalla.setLiittymivuosi(s);
        if (virhe == null) naytaVirhe(virhe);
        else naytaVirhe(virhe);
    }
*/

    /**
     * Tallenna asukastietoihin tehdyt muutokset
     */
    @FXML public void handleTallenna() {
        if (asukasKohdalla != null && asukasKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä!");
            return;
        }
        
        ModalController.closeStage(editNimi);
    }
    
    /**
     * Peruuta tallentamatta asukastietoihin tehtyjä muutoksia
     */
    @FXML public void handlePeruuta() {
        asukasKohdalla = null;
        ModalController.closeStage(editNimi);
    }
    
    
    //===========================
    private Asukas asukasKohdalla;
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
    public Asukas getResult() {
        //
        return asukasKohdalla;
    }

    @Override
    public void handleShown() {
        //
        
    }

    @Override
    public void setDefault(Asukas oletus) {
        asukasKohdalla = oletus;
        naytaAsukas(edits, asukasKohdalla);
    }

    /**
     * Luodaan asukkaan kysymisdialohi ja palautetaan sama tietua muutettuna tai null
     * @param modalityStage mille ollaan modaalisia (null = sovellukselle)
     * @param oletus mitä dataa käytetään oletuksena
     * @return null jos painetaan cancel, muuten täytetty tietue
     */
    public static Asukas kysyAsukas(Stage modalityStage, Asukas oletus) {
        return ModalController.showModal(AsukasValikkoController.class.getResource("AsukasDialogView.fxml"), "Asukas", modalityStage, oletus); 
    }

    
    
    /**
     * Täyttää asukkaan tiedot TextField komponentteihin
     * @param edits taulukko TextFieldeistä
     * @param asukas näytettävä asukas
     */
    public static void naytaAsukas(TextField[] edits, Asukas asukas) {
        if (asukas == null) return;
        edits[0].setText(asukas.getRappu());
        edits[1].setText(asukas.getAsuntoNro());
        edits[2].setText(asukas.getNimi());
        edits[3].setText(asukas.getHetu());
        edits[4].setText(asukas.getPuhelinnumero());
        edits[5].setText(asukas.getEmail());
        edits[6].setText(asukas.getOsakeM2());
        edits[7].setText(asukas.getYhtiovastike());
        edits[8].setText(asukas.getYhtiovastikeM2());
        edits[9].setText(asukas.getHakkivarasto());
        edits[10].setText(asukas.getSaunaoikeus());
        edits[11].setText(asukas.getPyykkitupa());
        edits[12].setText(asukas.getHallitus());
        edits[13].setText(asukas.getLiittymisvuosi());
    }

}
