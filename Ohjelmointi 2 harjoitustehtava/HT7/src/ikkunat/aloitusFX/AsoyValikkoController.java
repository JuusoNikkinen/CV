package ikkunat.aloitusFX;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import tietorakenne.Asoy;
import tietorakenne.Rekisteri;

/**
 * @author juuso
 * @version 14.2.2024
 *
 */
public class AsoyValikkoController implements Initializable, ModalControllerInterface <Rekisteri> {

    @FXML
    private Menu Tiedosto;
    
    @FXML private ListChooser<Asoy> chooserAsoylista;

    @FXML
    void handleLisaaAsoy() {
        //Dialogs.showMessageDialog("Ei toimi vielä");
        lisaaAsoy();
    }

    @FXML
    void handleMuokkaaAsoy() {
        //Dialogs.showMessageDialog("Ei toimi vielä");
        muokkaaAsoy();
    }
    
    @FXML
    void handlePoistaAsoy() {
        //Dialogs.showMessageDialog("Ei toimi vielä");
        poistaAsoy();
    }

    @FXML
    void handlePoistu() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }

    @FXML
    void handleTallenna() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }

    @Override
    public Rekisteri getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        alustaAsoyData();
        
    }

    @Override
    public void setDefault(Rekisteri oletus) {
        this.rekisteri = oletus;
        
    }
    
    
    
    
    
    
    //=============================================================================
    private Rekisteri rekisteri;
    private Asoy asoyKohdalla;
    private TextArea areaAsoy = new TextArea();
    
    
    
    @FXML private ScrollPane panelAsoy;
    
    private void hae(int asoynro) {
        chooserAsoylista.clear();

        int index = 0;
        for (int i = 0; i < rekisteri.getAsoyLkm(); i++) {
            Asoy asoy = rekisteri.annaAsoy(i);
            if (asoy.getAsoyId() == asoynro) index = i;
            chooserAsoylista.add(asoy.getAsoyNimi() + "     " + asoy.getAsoyOsoite() + "     " + asoy.getAsoyHuolto(), asoy);
        }
        chooserAsoylista.setSelectedIndex(index);
    }
    
    private void alusta() {
        panelAsoy.setContent(areaAsoy);
        areaAsoy.setFont(new Font("Courier New", 12));
        panelAsoy.setFitToHeight(true);
        
        chooserAsoylista.clear();
        chooserAsoylista.addSelectionListener(e -> naytaAsoy());

    
    }
    
    
    private void naytaAsoy() {
        asoyKohdalla = chooserAsoylista.getSelectedObject();

        if (asoyKohdalla == null) return;

        areaAsoy.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAsoy)) {
            tulostaAsoyAsukkaat(os,asoyKohdalla);
        }
    }
    
    /**
     * @param os Tulostusvirta
     * @param asoy Tulostettava asoy
     */
    public void tulostaAsoyAsukkaat(PrintStream os, final Asoy asoy) {
        //os.println("----------------");
        int asoytunnus = asoy.getTunnusNro();
        String asukaslista = rekisteri.tulostaAsoyAsukaslista(asoytunnus);
        //asoy.tulosta(os);
        os.println(asukaslista);

    }
    
    private void lisaaAsoy() {
        Asoy uusi = new Asoy();
        uusi.rekisteroi();
        uusi.taytaTestiTiedoilla();
        
        rekisteri.lisaa(uusi);

        //hae(uusi.getAsoyId());
        alustaAsoyData();
    }
    
    private void poistaAsoy() {
        asoyKohdalla = chooserAsoylista.getSelectedObject();
        if (asoyKohdalla == null) return;
        
        rekisteri.poista(asoyKohdalla);
        
        alustaAsoyData(); 
    }
    
    private void alustaAsoyData() {
        chooserAsoylista.clear();
        haeAsoylista();
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
            alusta();
    }
    
    private void haeAsoylista() {
        for (int i = 0; i < rekisteri.getAsoyLkm(); i++) {
            Asoy asoy = rekisteri.annaAsoy(i);
            chooserAsoylista.add(asoy.getAsoyNimi() + "     " + asoy.getAsoyOsoite() + "     " + asoy.getAsoyHuolto(), asoy);
        }

        
    }
    
    private void muokkaaAsoy() {
        Asoy asoyKohdalla2 = chooserAsoylista.getSelectedObject();
        if (asoyKohdalla2 == null) return;
        Asoy asoy;
        try {
            asoy = asoyKohdalla2.clone();
            asoy = AsoyDialogController.kysyAsoy(null, asoy);
            if (asoy == null) return;
            rekisteri.korvaaTaiLisaa(asoy);
            hae(asoyKohdalla2.getAsoyId());
        } catch (CloneNotSupportedException e) {
            // 
        }
    }

}


