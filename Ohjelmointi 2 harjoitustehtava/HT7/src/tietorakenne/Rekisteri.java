package tietorakenne;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author juuso
 * @version 11.3.2024
 * 
 * 
 * 
 * 
 * 
 */
public class Rekisteri {
    
    private Asukkaat asukkaat = new Asukkaat();
    private Asoylista asoylista = new Asoylista();
    
    /**
     * Rekisterin tietojen alustaminen
     */
    public Rekisteri() {
        //
    }
    
    /**
     * Lisää uuden asukkaan
     * @param asukas lisättävä asukas
     */
    public void lisaa(Asukas asukas) {
        asukkaat.lisaa(asukas);
    }
    
    /**
     * @param asukas poistettava asukas
     */
    public void poista(Asukas asukas) {
        asukkaat.poista(asukas);
    }
    
    /**
     * Lisätään rekisteriin uusi asoy
     * @param asoy lisättävä asoy
     */
    public void lisaa(Asoy asoy) {
        asoylista.lisaa(asoy);
    }
    
    /**
     * Poistaa asoy:n rekisteristä
     * @param asoy poistettava asoy
     */
    public void poista(Asoy asoy) {
        asoylista.poista(asoy);
    }
    
    /**
     * Korvaa asukkaan tietorakenteessa. Ottaa asukkaan omistukseensa.
     * @param asukas korvattava asukas
     */ 
    public void korvaaTaiLisaa(Asukas asukas) {
        asukkaat.korvaaTaiLisaa(asukas);
    }
    
    /**
     * Korvaa asoyn tietorakenteessa. Ottaa asukkaan omistukseensa.
     * @param asoy käsiteltävä asoy
     */
    public void korvaaTaiLisaa(Asoy asoy) {
        asoylista.korvaaTaiLisaa(asoy);
    }
    
    /**
     * Hakee asukkaiden lukumäärän
     * @return Asukkaiden lukumäärä
     */
    public int getAsukkaita() {
        return asukkaat.getLkm();
    }
    
    /**
     * @return ASOY lukumäärä
     */
    public int getAsoyLkm() {
        return asoylista.getAsoyLkm();
    }
    
    /**
     * Antaa rekisterin i:n asukkaan
     * @param i monesko jäsen (asukas), alkaa 0:sta
     * @return palauttaa asukkaan 
     */
    public Asukas annaAsukas(int i) {
        return asukkaat.anna(i);
    }
    
    /**
     * @param asoy jonka asukkaita haetaan
     * @return palauttaa asukkaat
     */
    public List <Asukas> annaAsukkaat(Asoy asoy) {
        return asukkaat.annaAsukkaat(asoy.getTunnusNro());
    }
    
    /**
     * @param i indeksi jolta asoy:ta etsitään
     * @return palauttaa asoy:n
     */
    public Asoy annaAsoy(int i) {
        return asoylista.anna(i);
    }
    
    /**
     * @param asoyid jolla asoy:ta etsitään
     * @return palauttaa asoy:n
     */
    public Asoy haeAsoyIdlla(int asoyid) {
        return asoylista.annaAsoyIdlla(asoyid);
    }
    

    /**
     * Lukee rekisterin tiedot tiedostosta
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        File dir = new File(nimi);
        dir.mkdir();
        
        asukkaat = new Asukkaat();
        asoylista = new Asoylista();
        
        asukkaat.lueTiedostosta(nimi);
        asoylista.lueTiedostosta(nimi);
    }
    
    /**
     * @param nimi Rekisterin hakemiston nimi
     * @throws SailoException heittää poikkeuksen jos ei toimi
     */
    public void tallenna(String nimi) throws SailoException {
        String virhe = "";
        
        try {
            asukkaat.tallenna(nimi);
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
           asoylista.tallenna(nimi); 
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
        
    }
    
    
    /**
     * @param asoytunnus asoyid jonka asukkaita haetaan
     * @return lista asukkaisat
     */
    
    
    public String tulostaAsoyAsukaslista(int asoytunnus) {
        String palautettava = "";
        Asoy asoy = asoylista.annaAsoy(asoytunnus);
        //if (asoy == null) return;
        List<Asukas> asukkaat2 = annaAsukkaat(asoy);
        for (Asukas asukas : asukkaat2) {
            palautettava += asukas.tulostaString();

        }
        return palautettava;
    }
    
    
    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien asukkaiden viitteet
     * @param hakuehto jolla haetaan
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     */ 

    public Collection<Asukas> etsi(String hakuehto, int k) {
        return asukkaat.etsi(hakuehto, k);
    }

    /**
     * Hakee asoy:n nimen perusteella
     * @param nimi jolla haetaan
     * @return löydetyn asoy:n id-tunnus
     */
    public int haeAsoyId(String nimi) {
        return asoylista.haeAsoyId(nimi);
    }
    
    /**
     * Hakee annetus asoy:n tiedot
     * @param tunnus asoyid
     * @return palauttaa asoy:n tiedot taulukossa
     * @example
     * <pre name="test">
     * Rekisteri rekisteri = new Rekisteri();
     * Asoy asoy = new Asoy();
     * asoy.vastaaPitsinNyplays(1);
     * asoy.rekisteroi();
     * rekisteri.lisaa(asoy);
     
     * int asoyId = asoy.getTunnusNro();
     * 
     * String[] arvot = rekisteri.haeAsoyTiedot(asoyId);
     
     * arvot[0] === asoy.getAsoyNimi();
     * arvot[1] === asoy.getAsoyOsoite();
     * arvot[2] === asoy.getAsoyPostinumero();
     * arvot[3] === asoy.getAsoyPostitoimipaikka();
     * arvot[4] === asoy.getAsoyHuolto();
     * arvot[5] === asoy.getAsoyIsannoitsija();
     * 
     * </pre>
     */

    public String[] haeAsoyTiedot(int tunnus) {
        Asoy asoy = asoylista.annaAsoy(tunnus);
        String asoyNimi = asoy.getAsoyNimi();
        String asoyOsoite = asoy.getAsoyOsoite();
        String asoyPostinumero = asoy.getAsoyPostinumero();
        String asoyPostitoimipaikka = asoy.getAsoyPostitoimipaikka();
        String asoyHuolto = asoy.getAsoyHuolto();
        String asoyIsannoitsija = asoy.getAsoyIsannoitsija();
        
        String[] asoyTiedot = { asoyNimi, asoyOsoite, asoyPostinumero, asoyPostitoimipaikka, asoyHuolto, asoyIsannoitsija };
        return asoyTiedot;
    }

    /**
     * @param asoyid tunnus
     * @param tiedot tulostettavat tiedot taulukkona
     * @return tulostettavat tiedot merkkijonona
     */
    public String haeAsukastietoTuloste(int asoyid, int[] tiedot) {
        String tuloste = "";
        Asoy asoy = asoylista.annaAsoy(asoyid);

        List<Asukas> asukkaat2 = annaAsukkaat(asoy);
        for (Asukas asukas : asukkaat2) {
            tuloste += asukas.asukasTulosteString(tiedot);

        }
        return tuloste;
    }
    
    
    /**
     * @param args ei argumentteja
     */
    public static void main(String[] args) {
        Rekisteri rekisteri = new Rekisteri();
        
        Asoylista asoylista = new Asoylista();
        Asoy esim1 = new Asoy();
        esim1.vastaaPitsinNyplays(2);
        Asoy esim2 = new Asoy();
        esim2.vastaaPitsinNyplays(1);
        Asoy esim3 = new Asoy();
        esim3.vastaaPitsinNyplays(2);
        Asoy esim4 = new Asoy();
        esim4.vastaaPitsinNyplays(2);
        
        esim1.rekisteroi();
        esim2.rekisteroi();
        esim3.rekisteroi();
        esim4.rekisteroi();
        
        asoylista.lisaa(esim1);
        asoylista.lisaa(esim2);
        asoylista.lisaa(esim3);
        asoylista.lisaa(esim4);
        
        
        Asukas aku = new Asukas();
        Asukas aku2 = new Asukas();
        aku.rekisteroi();
        aku.taytaAkuAnkkaTiedoilla();
        aku2.rekisteroi();
        aku2.taytaAkuAnkkaTiedoilla();
        
        Asukas iines = new Asukas();
        Asukas iines2 = new Asukas();
        iines.rekisteroi();
        iines2.rekisteroi();
        iines2.taytaIinesAnkkaTiedoilla();
        
        rekisteri.lisaa(aku);
        rekisteri.lisaa(aku2);
        rekisteri.lisaa(iines);
        rekisteri.lisaa(iines2);
       

        
        

        for (int j = 0; j < asoylista.getAsoyLkm(); j++) {
            Asoy asoy = asoylista.annaAsoy(j);
            System.out.println("Asoy paikassa: " + j);
            asoy.tulosta(System.out);
            
            System.out.println("---Asukkaat---");
            List<Asukas> asukkaat = rekisteri.annaAsukkaat(asoy);
            for (Asukas asukas : asukkaat) {
                asukas.tulosta(System.out);
                System.out.println("-------");
            }
            System.out.println("=============================");
        }
    }
}
