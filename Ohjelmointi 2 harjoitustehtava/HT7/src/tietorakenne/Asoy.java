package tietorakenne;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.HetuTarkistus;

import static kanta.HetuTarkistus.rand;


/**
 * @author juuso
 * @version 20.3.2024
 *
 */
public class Asoy implements Cloneable {
    private int tunnusNro;
    private String asoyNimi = "";
    private String asoyOsoite = "";
    private String asoyPostinro = "";
    private String asoyPosttoimpaikka = "";
    private String asoyIsannoitsija = "";
    private String asoyHuolto = "";

    
    private static int seuraavaNro = 1;


    /**
     * 
     */
    public void taytaTestiTiedoilla() {
        tunnusNro = seuraavaNro;
        asoyNimi = "ASOY " + HetuTarkistus.rand(1, 300);
        asoyOsoite = "Esimerkkikatu " + HetuTarkistus.rand(1, 50);
        asoyHuolto = "Huoltoyhtiö";
    }
    
    /**
     * Asoy alustus
     */
    public Asoy() {
        // Vielä ei tarvita mitään
    }

    /**
     * Tietyn asukkaan asoy alustus.  
     * @param asoytunnus x
     */
    public Asoy(int asoytunnus) {
        this.tunnusNro = asoytunnus;
    }
    
    /**
     * @return tunnusnro
     * 
     */
    public int getAsoyId() {
        return tunnusNro;
    }
    
    @SuppressWarnings("unused")
    private void setAsoyId(int nro) {
        tunnusNro = nro;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    /**
     * @return asoynimi
     * @example
     * <pre name="test">
     *   Asoy asoy1 = new Asoy();
     *   asoy1.taytaTestiTiedoilla();
     *   asoy1.getAsoyNimi() =R= "ASOY.*";
     * </pre>
     */
    public String getAsoyNimi() {
        return asoyNimi;
    }
    /**
     * @param s asetettava nimi
     * @return null
     */
    public String setAsoyNimi(String s) {
        this.asoyNimi = s;
        return null;
    }
    
    /**
     * 
     * täyttää asoytiedot 
     * @param asoynro viite asoy:hyn joka lisätään
     */
    public void vastaaPitsinNyplays(int asoynro) {
        tunnusNro = asoynro;
        asoyNimi = "Esim" + rand(0, 99);
        asoyOsoite = "Katu" + rand(1, 50);
        asoyPostinro = "99999";
        asoyPosttoimpaikka = "Ankkalinna";
        asoyIsannoitsija = "Tailor Isännöinti Oy";
        asoyHuolto = "Nauha Oy";
    }

    /**
     * Tulostetaan asoy tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(tunnusNro + " " + asoyNimi + " " + asoyOsoite + " " + 
        asoyPostinro  + " " + asoyPosttoimpaikka + " " + asoyIsannoitsija  + " " + asoyHuolto);
    }

    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Antaa asoy:lle seuraavan vapaan rekisterinumeron.
     * @return asoy:n tunnusNro
     * @example
     * <pre name="test">
     *   Asoy asoy1 = new Asoy();
     *   asoy1.getTunnusNro() === 0;
     *   asoy1.rekisteroi();
     *   Asoy asoy2 = new Asoy();
     *   asoy2.rekisteroi();
     *   int n1 = asoy1.getTunnusNro();
     *   int n2 = asoy2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    /**
     * Palautetaan asoy:n tunnusnumero
     * @return asoy:n id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }

    private void setTunnusNro(int nro) {
        tunnusNro = nro;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    /**
     * Palautetaan asoy:n huoltoyhtiön nimi
     * @return asoy:n huoltoyhtiö
     * @example
     * <pre name="test">
     *   Asoy asoy1 = new Asoy();
     *   asoy1.taytaTestiTiedoilla();
     *   asoy1.getAsoyHuolto() =R= "Huoltoyhtiö";
     * </pre>
     */
    public String getAsoyHuolto() {
        return asoyHuolto;
    }
    /**
     * @param s asetettava huolto
     * @return null
     */
    public String setAsoyHuolto(String s) {
        this.asoyHuolto = s;
        return null;
    }
    /**
     * Palautetaan asoy:n osoite
     * @return asoy:n osoite
     * @example
     * <pre name="test">
     *   Asoy asoy1 = new Asoy();
     *   asoy1.taytaTestiTiedoilla();
     *   asoy1.getAsoyOsoite() =R= "Esimerkkikatu.*";
     * </pre>
     */
    public String getAsoyOsoite() {
        return asoyOsoite;
    }
    /**
     * @param s asetettava asoite
     * @return null
     */
    public String setAsoyOsoite(String s) {
        this.asoyOsoite = s;
        return null;
    }
    /**
     * @return haettava postinumero
     */ 
    public String getAsoyPostinumero() {
        return asoyPostinro;
    }
    /**
     * @param s asetettava postinumero
     * @return null
     */
    public String setAsoyPostinumero(String s) {
        this.asoyPostinro = s;
        return null;
    }
    /**
     * @return asoy:n postitoimipaikka
     * 
     */
    public String getAsoyPostitoimipaikka() {
        return asoyPosttoimpaikka;
    }
    /**
     * @param s asetettava postitoimipaikka
     * @return null
     */
    public String setAsoyPostitoimipaikka(String s) {
        this.asoyPosttoimpaikka = s;
        return null;
    }
    /**
     * @return haettava isannoitsija
     */
    public String getAsoyIsannoitsija() {
        return asoyIsannoitsija;
    }
    /**
     * @param s asetettava isannoitsija
     * @return null
     */
    public String setAsoyIsannoitsija(String s) {
        this.asoyIsannoitsija = s;
        return null;
    }

    /**
     * Palauttaa asoyn tiedot merkkijonona |-merkeillä eroteltuina
     * @return asukastiedot
     * @example
     * <pre name="test">
     * Asoy asoy = new Asoy();
     * asoy.parse("3|   ASOY Ankkalinna |  Katu 2  |10800|Ankkalinna     |   Isännöinti Siipiveikot   |   Huolto Räpylä");
     * asoy.toString().startsWith("3|ASOY Ankkalinna|Katu 2|10800|Ankkalinna|Isännöinti Siipiveikot|Huolto Räpylä") === true;
     </pre>
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                asoyNimi + "|" +
                asoyOsoite  + "|" +
                asoyPostinro  + "|" +
                asoyPosttoimpaikka + "|" +
                asoyIsannoitsija + "|" +
                asoyHuolto;
    }
    
    /**
     * @param rivi asukkaat.dat tiedoston rivi
     * @example
     * <pre name="test">
     * Asoy asoy = new Asoy();
     * asoy.parse("3|   ASOY Ankkalinna |  Katu 2  |10800|Ankkalinna     |   Isännöinti Siipiveikot   |   Huolto Räpylä");
     * asoy.toString().startsWith("3|ASOY Ankkalinna|Katu 2|10800|Ankkalinna|Isännöinti Siipiveikot|Huolto Räpylä") === true;
     </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        asoyNimi = Mjonot.erota(sb, '|', asoyNimi);
        asoyOsoite = Mjonot.erota(sb, '|', asoyOsoite);
        asoyPostinro = Mjonot.erota(sb, '|', asoyPostinro);
        asoyPosttoimpaikka = Mjonot.erota(sb, '|', asoyPosttoimpaikka);
        asoyIsannoitsija = Mjonot.erota(sb, '|', asoyIsannoitsija);
        asoyHuolto = Mjonot.erota(sb, '|', asoyHuolto);
    }
    
    /**
     * Asoyn kloonaaminen
     * @return Object kloonattu asoy
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Asoy asoy1 = new Asoy();
     *   asoy1.parse("   3  |  Ankka Aku   | 123");
     *   
     *   Asoy kopio = asoy1.clone();
     *   kopio.toString() === asoy1.toString();
     *   
     *   asoy1.parse("   4  |  Ankka Tupu   | 123");
     *   kopio.toString().equals(asoy1.toString()) === false;
     * </pre>
     * 
     */
    @Override
    public Asoy clone() throws CloneNotSupportedException {
        Asoy uusi;
        uusi = (Asoy)super.clone();
        return uusi;
    }
    /**
     * Testiohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Asoy asuntyhtio = new Asoy();
        asuntyhtio.vastaaPitsinNyplays(2);
        asuntyhtio.tulosta(System.out);
    }

}

