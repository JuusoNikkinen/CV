package tietorakenne;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author juuso
 * @version 20.3.2024
 *
 */
public class Asoylista {
    private String                      tiedostonNimi = "";
    private int lkm = 0;

    /** ASOY taulukko  */
    private final ArrayList<Asoy> alkiot = new ArrayList<Asoy>();


    /**
     * Asoylistan alustaminen
     */
    public Asoylista() {
        // 
    }

    /**
     * Haetaan listassa indeksillä olevat alkiot
     * @param i indeksi josta asoy:ta etsitään
     * @return alkiot
     * @throws IndexOutOfBoundsException jos ei toimi
     */
    public Asoy anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    /**
     * Haetaan asoy asoyid:n perusteella
     * @param asoyid haettava id
     * @return palauttaa asoy:n tai jos ei löydy palauttaa null
     */
    public Asoy annaAsoyIdlla(int asoyid) {
        for (Asoy asoy : alkiot) {
            if (asoy.getAsoyId() == asoyid) {
                return asoy;
            }
        }
        return null;
    }
    
    /**
     * Lisää uuden asoy:n
     * @param asuntoosake lisättävä asoy
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Asoylista asoylista = new Asoylista();
     * Asoy aku1 = new Asoy(), aku2 = new Asoy();
     * asoylista.getLkm() === 0;
     * asoylista.lisaa(aku1); 
     * asoylista.getLkm() === 1;
     * asoylista.lisaa(aku2);
     * asoylista.getLkm() === 2;
     * 
     * asoylista.poista(aku1);
     * asoylista.getLkm() === 1;
     * 
     * asoylista.poista(aku2);
     * asoylista.getLkm() === 0;
     * </pre>
     */
    public void lisaa(Asoy asuntoosake) {
        alkiot.add(asuntoosake);
        this.lkm++;
    }
    
    /**
     * Poistaa asoy:n
     * @param asoy poistettava asoy
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Asoylista asoylista = new Asoylista();
     * Asoy aku1 = new Asoy(), aku2 = new Asoy();
     * asoylista.getLkm() === 0;
     * asoylista.lisaa(aku1); 
     * asoylista.getLkm() === 1;
     * asoylista.lisaa(aku2);
     * asoylista.getLkm() === 2;
     * 
     * asoylista.poista(aku1);
     * asoylista.getLkm() === 1;
     * 
     * asoylista.poista(aku2);
     * asoylista.getLkm() === 0;
     * </pre>
     */
    public void poista(Asoy asoy) {
        if (asoy == null) return;

        Iterator<Asoy> iterator = alkiot.iterator();
        while (iterator.hasNext()) {
            Asoy currentAsoy = iterator.next();
            if (currentAsoy.equals(asoy)) {
                iterator.remove();
                this.lkm--;
                return;
            }
        }
    }

    /**Hakee asoy lukumäärän
     * @return asoy lukumäärä
     */
    public int getAsoyLkm() {
        return this.lkm;
    }


    /**
     * Tallentaa asoylistan tiedostoon.  
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa rekisterin asoy lumumäärän
     * @return asoy lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori listan asoy:den läpikäyntiin
     * @return asoyiteraattori
     */
    public Iterator<Asoy> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kaikki asukkaiden asoy
     * @param tunnusnro asoy tunnusnumero jolle asukkaita haetaan
     * @return tietorakenne asoy-asukas
     */
    public List<Asoy> annaAsoylista(int tunnusnro) {
        ArrayList<Asoy> loydetyt = new ArrayList<Asoy>();
        for (Asoy asoy : alkiot)
            if (asoy.getTunnusNro() == tunnusnro) loydetyt.add(asoy);
        return loydetyt;
    }

    /**
     * @param tunnusnro a
     * @return a
     */
    public Asoy annaAsoy(int tunnusnro) {
        for (Asoy asoy : alkiot)
            if (asoy.getTunnusNro() == tunnusnro) return asoy;
        return null;
    }
    
    /**
     * @param hakemisto Mihin hakemistoon tallennetaan
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        File ftied = new File(hakemisto + "/asoylista.dat");
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied))) {
            for (int i = 0; i < this.getLkm(); i++) {
                Asoy asoy = this.anna(i);
                fo.println(asoy.toString());
            }
        }
        catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath());
        }
        
        //throw new SailoException("Ei osata vielä tallentaa tiedostoa :d");
    }
    
    /**
     * @param hakemisto Asukastiedoston sijaintihakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Asoylista asoylista = new Asoylista();
     *  Asoy aku1 = new Asoy(), aku2 = new Asoy();
     *  aku1.taytaTestiTiedoilla();
     *  aku2.taytaTestiTiedoilla();
     *  String hakemisto = "testikelmit";
     *  String tiedNimi = hakemisto + "/asoylista";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  asoylista.lueTiedostosta(hakemisto); #THROWS SailoException
     *  asoylista.lisaa(aku1);
     *  asoylista.lisaa(aku2);
     *  asoylista.tallenna(hakemisto);
     *  asoylista = new Asoylista();            // Poistetaan vanhat luomalla uusi
     *  asoylista.lueTiedostosta(hakemisto);  // johon ladataan tiedot tiedostosta.
     *  asoylista.anna(0).toString() === aku1.toString();
     *  asoylista.anna(1).toString() === aku2.toString();
     *  asoylista.anna(2); #THROWS IndexOutOfBoundsException
     *  asoylista.lisaa(aku2);
     *  asoylista.tallenna(hakemisto);
     *  ftied.delete() === true;
     *  dir.delete() === true;
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/asoylista.dat";
        
        File ftied = new File(tiedostonNimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s == null || "".equals(s) || s.charAt(0) == ';') continue;
                Asoy asoy = new Asoy();
                asoy.parse(s);
                lisaa(asoy);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + tiedostonNimi);
        }
        //throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Korvaa asoy:n tietorakenteessa. Ottaa asoy:n omistukseensa.
     * Jos ei löydy, lisätään uusi asoy
     * @param asoy korvattava tai lisättävä asoy
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
    public void korvaaTaiLisaa(Asoy asoy) {
        int id = asoy.getAsoyId();
        for (int i = 0; i < lkm; i++) {
            if (alkiot.get(i).getAsoyId() == id) {
                alkiot.set(i, asoy);
                return;
            }
        }
        lisaa(asoy);
    }

    /**
     * Etsii asoy:n id-numeron nimen perusteella.
     * @param nimi haettavan asoy:n nimi
     * @return palauttaa asoy:n id-numeron. Jos ei löydy, palauttaa arvon -1
     */
    public int haeAsoyId(String nimi) {
        for (Asoy asoy : alkiot)
            if (asoy.getAsoyNimi().toLowerCase().equals( nimi.toLowerCase() )|| asoy.getAsoyNimi().toLowerCase().equals (("asoy " + nimi.toLowerCase()))) {
                return asoy.getAsoyId();
            }
        return -1;
    }
    
    /**
     * Testiohjelma asoylistalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Asoylista asoylista1 = new Asoylista();
        
        try {
            asoylista1.lueTiedostosta("asoylista");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        
        Asoy testi1 = new Asoy();
        testi1.vastaaPitsinNyplays(2);
        Asoy testi2 = new Asoy();
        testi2.vastaaPitsinNyplays(1);
        Asoy testi3 = new Asoy();
        testi3.vastaaPitsinNyplays(2);
        Asoy testi4 = new Asoy();
        testi4.vastaaPitsinNyplays(2);

        asoylista1.lisaa(testi1);
        asoylista1.lisaa(testi2);
        asoylista1.lisaa(testi3);
        asoylista1.lisaa(testi3);
        asoylista1.lisaa(testi4);

        
        System.out.println("============= Asoylista testi =================");

        List<Asoy> asoylista2 = asoylista1.annaAsoylista(2);

        for (Asoy asoy : asoylista2) {
            System.out.print(asoy.getTunnusNro() + " ");
            asoy.tulosta(System.out);
        }

        try {
            asoylista1.tallenna("asoylista");
        } catch (SailoException e) {
            e.printStackTrace();
        }
        
        
    }
}
