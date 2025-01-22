package tietorakenne;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * @author juuso
 * @version 11.3.2024
 *
 */
public class Asukkaat {

    private static final int MAX_ASUKKAITA = 10;
    int lkm = 0;
    private Asukas[] alkiot;
    String tiedostonNimi = "";

    /**
     * 
     */
    public Asukkaat() {
        this.alkiot = new Asukas[MAX_ASUKKAITA];
    }


    /**
     * Lisää asukkaan
     * @param asukas lisättävä asukas
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Asukkaat asukkaat = new Asukkaat();
     * Asukas aku1 = new Asukas(), aku2 = new Asukas();
     * asukkaat.getLkm() === 0;
     * asukkaat.lisaa(aku1); 
     * asukkaat.getLkm() === 1;
     * asukkaat.lisaa(aku2);
     * asukkaat.getLkm() === 2;
     * </pre>
     */
    public void lisaa(Asukas asukas) {
        if (lkm >= alkiot.length) { // tarkistaa tarviiko taulukkoa laajentaa
            int uusiTaulukkoKoko = alkiot.length * 2;
            Asukas[] uusiTaulukko = new Asukas[uusiTaulukkoKoko];
            for (int i = 0; i < lkm; i++) {
                uusiTaulukko[i] = alkiot[i];
            }
            alkiot = uusiTaulukko;
        }
        this.alkiot[this.lkm] = asukas;
        this.lkm++;
    }


    /**
     * Poistaa asukkaan
     * @param asukas poistettava asukas
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Asukkaat asukkaat = new Asukkaat();
     * Asukas aku1 = new Asukas(), aku2 = new Asukas();
     * asukkaat.getLkm() === 0;
     * asukkaat.lisaa(aku1); 
     * asukkaat.getLkm() === 1;
     * asukkaat.lisaa(aku2);
     * asukkaat.getLkm() === 2;
     * 
     * asukkaat.poista(aku1);
     * asukkaat.getLkm() === 1;
     * 
     * asukkaat.poista(aku2);
     * asukkaat.getLkm() === 0;
     * </pre>
     * 
     */
    public void poista(Asukas asukas) {
        int tunnus = asukas.getAsukasId();

        int indeksi = -1;
        for (int i = 0; i < this.lkm; i++) {
            if (alkiot[i].getAsukasId() == tunnus) {
                indeksi = i;
                break;
            }
        }

        if (indeksi >= 0) {
            for (int j = indeksi; j < this.lkm - 1; j++) {
                alkiot[j] = alkiot[j + 1];
            }
            lkm--;
        }
    }


    /**
     * Palauttaa viitteen i:teen asukkaaseen
     * @param i monennenko asukkaan viite halutaan
     * @return viite asukkaaseen
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Asukas anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Palauttaa rekisterin asukkaiden lukumäärän
     * @return asukkaiden lukumäärä
     */
    public int getLkm() {
        return this.lkm;
    }


    /**
     * Haetaan kaikki asukkaat asoy:ssä
     * @param asoytunnus asunto-osakkeen tunnus jolla asukkaita haetaan
     * @return tietorakenne jossa viitteet asukkaisiin
     */
    public List<Asukas> annaAsukkaat(int asoytunnus) {
        List<Asukas> loydetyt = new ArrayList<Asukas>();
        for (Asukas asukas : alkiot)
            if (asukas != null && asukas.getAsoyId() == asoytunnus)
                loydetyt.add(asukas);
        return loydetyt;
    }


    /**
     * @param hakemisto Mihin hakemistoon tallennetaan
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        File ftied = new File(hakemisto + "/asukkaat.dat");

        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied))) {
            for (int i = 0; i < this.getLkm(); i++) {
                Asukas asukas = this.anna(i);
                fo.println(asukas.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath());
        }

        // throw new SailoException("Ei osata vielä tallentaa tiedostoa :d");
    }


    /**
     * @param hakemisto Asukastiedoston sijaintihakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Asukkaat asukkaat = new Asukkaat();
     *  Asukas aku1 = new Asukas(), aku2 = new Asukas();
     *  aku1.taytaAkuAnkkaTiedoilla();
     *  aku2.taytaAkuAnkkaTiedoilla();
     *  String hakemisto = "testikelmit";
     *  String tiedNimi = hakemisto + "/asukkaat";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  asukkaat.lueTiedostosta(hakemisto); #THROWS SailoException
     *  asukkaat.lisaa(aku1);
     *  asukkaat.lisaa(aku2);
     *  asukkaat.tallenna(hakemisto);
     *  asukkaat = new Asukkaat();            // Poistetaan vanhat luomalla uusi
     *  asukkaat.lueTiedostosta(hakemisto);  // johon ladataan tiedot tiedostosta.
     *  asukkaat.anna(0).toString() === aku1.toString();
     *  asukkaat.anna(1).toString() === aku2.toString();
     *  asukkaat.anna(2); #THROWS IndexOutOfBoundsException
     *  asukkaat.lisaa(aku2);
     *  asukkaat.tallenna(hakemisto);
     *  ftied.delete() === true;
     *  dir.delete() === true;
     * </pre>
     * 
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/asukkaat.dat";

        File ftied = new File(tiedostonNimi);

        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s == null || "".equals(s) || s.charAt(0) == ';')
                    continue;
                Asukas asukas = new Asukas();
                asukas.parse(s);
                lisaa(asukas);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException(
                    "Ei saa luettua tiedostoa " + tiedostonNimi);
        }
        // throw new SailoException("Ei osata vielä lukea tiedostoa " +
        // tiedostonNimi);
    }


    /**
     * Korvaa asukkaan tietorakenteessa. Ottaa asukkaan omistukseensa.
     * Etsii samalla tunnusnumerolla olevaa asukasta.
     * Jos ei löydy, lisätään uutena asukkaana.
     * @param asukas lisättävän asukkaan viite
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Asukkaat asukkaat = new Asukkaat();
     * Asukas aku1 = new Asukas(), aku2 = new Asukas();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * asukkaat.getLkm() === 0;
     * asukkaat.korvaaTaiLisaa(aku1); asukkaat.getLkm() === 1;
     * asukkaat.korvaaTaiLisaa(aku2); asukkaat.getLkm() === 2;
     * Asukas aku3 = aku1.clone();
     * aku3.setNimi("testiankka");
     * asukkaat.korvaaTaiLisaa(aku3);
     * asukkaat.getLkm() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Asukas asukas) {
        int id = asukas.getAsukasId();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getAsukasId() == id) {
                alkiot[i] = asukas;
                return;
            }
        }
        lisaa(asukas);
    }


    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien asukkaiden viitteet.
    * @param hakuehto hakuehto
    * @param k etsittävän kentän indeksi
    * @return tietorakenne löytyneistö asukkaista
    * @example 
     * <pre name="test"> 
     * #THROWS SailoException
     * #import java.util.ArrayList;
     *   Asukkaat asukkaat = new Asukkaat(); 
     *   Asukas asukas1 = new Asukas(); asukas1.parse("1 |1|Ankka Aku   |030201-111C   |A   | 2 |04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja|"); 
     *   Asukas asukas2 = new Asukas(); asukas2.parse("2 |2|Ankka Tupu   |030201-111C   |A   | 2 |04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja|"); 
     *   Asukas asukas3 = new Asukas(); asukas3.parse("3 |3|Susi Sepe    |030201-111C   |A   | 2 |04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja|"); 
     *   Asukas asukas4 = new Asukas(); asukas4.parse("4 |4|Ankka Iines   |030201-111C   |A   | 2 |04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja|"); 
     *   Asukas asukas5 = new Asukas(); asukas5.parse("5 |5|Ankka Roope   |030201-111C   |A   | 2 |04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja|"); 
     *   asukkaat.lisaa(asukas1); asukkaat.lisaa(asukas2); asukkaat.lisaa(asukas3); asukkaat.lisaa(asukas4); asukkaat.lisaa(asukas5);
     *   ArrayList<Asukas> loytyneet;  
     *   loytyneet = (ArrayList<Asukas>)asukkaat.etsi("s",2);  
     *   loytyneet.size() === 2;  
     *   loytyneet.get(0) == asukas3 === true;  
     *   loytyneet.get(1) == asukas4 === true;
     *     
     * 
     * </pre> 
    * 
    */
    public Collection<Asukas> etsi(String hakuehto,
            @SuppressWarnings("unused") int k) {
        Collection<Asukas> loytyneet = new ArrayList<Asukas>();
        for (Asukas asukas : alkiot) {
            if (asukas == null) break;
            if (asukas.getNimi().contains(hakuehto))
                loytyneet.add(asukas);
        }
        return loytyneet;
    }


    /**
     * @param args ei argumentteja
     */
    public static void main(String[] args) {
        Asukkaat asukkaat = new Asukkaat();

        try {
            asukkaat.lueTiedostosta("asukkaat");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }

        Asukas aku = new Asukas();
        Asukas aku2 = new Asukas();

        aku.rekisteroi();
        aku2.rekisteroi();

        aku.tulosta(System.out);
        aku2.tulosta(System.out);

        aku.taytaAkuAnkkaTiedoilla();
        aku2.taytaAkuAnkkaTiedoilla();

        asukkaat.lisaa(aku);
        asukkaat.lisaa(aku2);
        System.out.println(
                "============================= Asukkaat testi =============================");
        for (int i = 0; i < asukkaat.getLkm(); i++) {
            Asukas asukas = asukkaat.anna(i);
            System.out.println("Asukas indeksi: " + i);
            asukas.tulosta(System.out);
        }

        try {
            asukkaat.tallenna("asukkaat");
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }

    }
}
