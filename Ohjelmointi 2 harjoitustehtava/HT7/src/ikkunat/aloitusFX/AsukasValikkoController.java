package ikkunat.aloitusFX;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import tietorakenne.Asoy;
import tietorakenne.Asukas;
import tietorakenne.Rekisteri;

/**
 * @author juuso
 * @version 14.2.2024
 *
 */
public class AsukasValikkoController
        implements Initializable, ModalControllerInterface<Rekisteri> {

    @FXML
    private StringGrid<String> gridAsukaslista;
    @FXML
    private ScrollPane panelAsukas;

    @FXML
    private TextField editRappu;
    @FXML
    private TextField editAsuntoNro;
    @FXML
    private TextField editNimi;
    @FXML
    private TextField editHetu;
    @FXML
    private TextField editPuhelin;
    @FXML
    private TextField editEmail;
    @FXML
    private TextField editOsakeM2;
    @FXML
    private TextField editYhtiovastike;
    @FXML
    private TextField editYhtiovastikeM2;
    @FXML
    private TextField editHakkivarasto;
    @FXML
    private TextField editSaunaoikeus;
    @FXML
    private TextField editPyykkitupa;
    @FXML
    private TextField editHallitus;
    @FXML
    private TextField editLiittymisvuosi;
    @FXML
    private TextField hakuAsukasNimi;

    @Override
    public Rekisteri getResult() {
        return null;
    }


    @Override
    public void handleShown() {
        alustaAsukasData();
    }


    @Override
    public void setDefault(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;

    }


    @FXML
    private void handleMuokkaaAsoy() {
        muokkaaAsukas();
    }


    @FXML
    private void handlePoistaAsoy() {
        // Dialogs.showMessageDialog("Poistaminen ei vielä käytössä");
        poistaAsukas();
    }


    // drop-down valikko

    @FXML
    void handleLisaaAsunto() {
        // Dialogs.showMessageDialog("Ei toimi vielä");
        lisaaAsukas();
    }

    @FXML
    void handleTietoja() {
        Dialogs.showMessageDialog("Tekijä: Juuso Nikkinen");
    }


    @FXML
    void handleTulosta() {
        Asoy asoy;
        asoy = rekisteri.haeAsoyIdlla(haettuAsoyId);

        ModalController.<Integer, TulostusGUIController>showModal(AsukasValikkoController.class.getResource(
                "TulostusGUIView.fxml"), "ASOY Tulostus", null, asoy.getAsoyId(), ctrl->ctrl.setRekisteri(rekisteri));
    }


    @FXML
    void haeAsukasNimella() {
        haeAsukkaat();
    }


    @FXML
    void haeAsukasRappu() {
        //
    }

    // ===============================================================================================================================================================

    @FXML
    private ListChooser<Asukas> chooserAsukkaat;

    private Rekisteri rekisteri;
    ///////////////// private TextArea areaAsukas = new TextArea();
    private Asukas asukasKohdalla;
    private int kaynnistysNro = 0;
    private TextField[] edits;
    /**
     * Asoy:n Id jota käsitellään
     */
    public int haettuAsoyId = 1;
    private String haettuAsoyNimi;
    private String haettuAsoyOsoite;
    private String haettuAsoyPostinumero;
    private String haettuAsoyPostitoimipaikka;
    private String haettuAsoyHuolto;
    private String haettuAsoyIsannoitsija;

    @FXML private Label TextAsoyNimi;
    @FXML private Label TextAsoyOsoite;
    @FXML private Label TextAsoyPostinumero;
    @FXML private Label TextAsoyPostitoimipaikka;
    @FXML private Label TextAsoyHuolto;
    @FXML private Label TextAsoyIsannoitsija;

    

    

    
/*
    @SuppressWarnings("unused")
    private void hae(int asukasnro) {
        chooserAsukkaat.clear();

        int index = 0;
        for (int i = 0; i < rekisteri.getAsukkaita(); i++) {
            Asukas asukas = rekisteri.annaAsukas(i);
            if (asukas.getAsukasId() == asukasnro)
                index = i;
            chooserAsukkaat.add(asukas.getNimi() + "   " + asukas.getAsunto(),
                    asukas);
        }
        chooserAsukkaat.setSelectedIndex(index);
    }*/


    private void lisaaAsukas() {
        Asukas uusi = new Asukas();
        uusi.rekisteroi();
        uusi.taytaAkuAnkkaTiedoilla(haettuAsoyId);

        rekisteri.lisaa(uusi);

        // hae(uusi.getAsukasId());
        alustaAsukasData();
    }


    private void poistaAsukas() {
        asukasKohdalla = chooserAsukkaat.getSelectedObject();
        if (asukasKohdalla == null)
            return;

        rekisteri.poista(asukasKohdalla);

        alustaAsukasData();
    }


    private void alustaAsukasData() {
        chooserAsukkaat.clear();
        haeAsukkaat();

        edits = new TextField[] { editRappu, editAsuntoNro, editNimi, editHetu,
                editPuhelin, editEmail, editOsakeM2, editYhtiovastike,
                editYhtiovastikeM2, editHakkivarasto, editSaunaoikeus,
                editPyykkitupa, editHallitus, editLiittymisvuosi };
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (kaynnistysNro == 0) {
            // this.rekisteri = new Rekisteri();
            kaynnistysNro++;
            alusta();
        }

    }


    private void alusta() {
        panelAsukas.setFitToHeight(true);

        chooserAsukkaat.clear();
        chooserAsukkaat.addSelectionListener(e -> naytaAsukas());
    }


    private void haeAsukkaat() {
        if (rekisteri.getAsukkaita() == 0)
            return;

        String kriteeri = hakuAsukasNimi.getText();

        chooserAsukkaat.clear();

        if (kriteeri.isEmpty()) {
            for (int i = 0; i < rekisteri.getAsukkaita(); i++) {
                Asukas asukas = rekisteri.annaAsukas(i);
                
                if (asukas.getAsoyId() == haettuAsoyId) {
                    chooserAsukkaat.add(
                        asukas.getNimi() + "   " + asukas.getAsunto(), asukas);
                }
                

            }
            return;
        }
        
        for (int i = 0; i < rekisteri.getAsukkaita(); i++) {
            Asukas asukas = rekisteri.annaAsukas(i);
            if (asukas.getNimi().toLowerCase()
                    .contains(kriteeri.toLowerCase()) && asukas.getAsoyId() == haettuAsoyId) {
                chooserAsukkaat.add(
                        asukas.getNimi() + "   " + asukas.getAsunto(), asukas);
            }
        }
    }


    private void naytaAsukas() {
        asukasKohdalla = chooserAsukkaat.getSelectedObject();
        if (asukasKohdalla == null)
            return;
        AsukasDialogController.naytaAsukas(edits, asukasKohdalla);
    }


    private void muokkaaAsukas() {
        Asukas asukasKohdalla2 = chooserAsukkaat.getSelectedObject();
        if (asukasKohdalla2 == null)
            return;
        Asukas asukas;
        try {
            asukas = asukasKohdalla2.clone();
            asukas = AsukasDialogController.kysyAsukas(null, asukas);
            if (asukas == null)
                return;
            rekisteri.korvaaTaiLisaa(asukas);
            haeAsukkaat();
        } catch (CloneNotSupportedException e) {
            //
        }
    }


    /**
     * @param os tulostusvirta
     * @param asukas asukasolio
     */
    public void tulosta(PrintStream os, final Asukas asukas) {
        os.println("----------------------------------------------");
        asukas.tulosta(os);
        os.println("----------------------------------------------");

    }


    /**
     * Asettaa tiedot haetulle asoylle
     * @param tunnus asoyid
     */
    public void setHaettuAsoyTiedot(int tunnus) {

        String[] asoyTiedot = rekisteri.haeAsoyTiedot(tunnus);
        haettuAsoyId = tunnus;
        haettuAsoyNimi = asoyTiedot[0];
        haettuAsoyOsoite = asoyTiedot[1];
        haettuAsoyPostinumero = asoyTiedot[2];
        haettuAsoyPostitoimipaikka = asoyTiedot[3];
        haettuAsoyHuolto = asoyTiedot[4];
        haettuAsoyIsannoitsija = asoyTiedot[5];
        
        TextAsoyNimi.setText(haettuAsoyNimi);
        TextAsoyOsoite.setText(haettuAsoyOsoite);
        TextAsoyPostinumero.setText(haettuAsoyPostinumero);
        TextAsoyPostitoimipaikka.setText(haettuAsoyPostitoimipaikka);
        TextAsoyHuolto.setText(haettuAsoyHuolto);
        TextAsoyIsannoitsija.setText(haettuAsoyIsannoitsija);
        
    }



}
