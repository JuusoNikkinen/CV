package tietorakenne;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.HetuTarkistus;

/**
 * 
 * @author juuso
 * @version 11.3.2024
 *
 */
public class Asukas implements Cloneable {
    private int asoyId;
    private int asukasId;
    private String nimi = "";
    private String hetu = "";
    private String rappu = "";
    private int asuntoNro;
    private String puhelin = "";
    private String sposti = "";
    private int liittymisvuosi = 0;
    private double asuntoM2 = 0;
    private double yhtiovastikeM2 = 0.0;
    private String varasto = "";
    private String hallitus = "";
    private String saunamaksu = "";
    private String pesutupa = "";

    private static int seuraavaNro = 1;

    /**
     * Henkilön tietojen tulostus
     * @param out Tietovirta johon tulostetaan
     * 
     */
    public void tulosta(PrintStream out) {
        out.println("Asoy ID " + asoyId);
        out.println("Osake " + rappu + " " + asuntoNro);
        out.println("Asukas ID " + String.format("%03d", asukasId));
        out.println("Nimi " + nimi);
        out.println("Hetu " + hetu);
        out.println("Puhelinnumero " + puhelin);
        out.println("Sähköposti " + sposti);
        out.println("Asunto-osakkeen koko m2 " + asuntoM2);
        double vastike = yhtiovastikeM2 * asuntoM2;
        out.println("Yhtiövastike " + vastike + " € / kk");
        out.println("Yhtiövastike per m2 " + yhtiovastikeM2 + " €");
        out.println("Häkkivarasto " + varasto);
        out.println("Saunaoikeus " + saunamaksu);
        out.println("Pyykkitupaoikeus " + pesutupa);
        out.println("Hallituksen jäsen " + hallitus);
        out.println("Liittymisvuosi " + liittymisvuosi);

    }


    /**
     * Tulostaa asukkaan tiedot
     * @return tuloste
     */
    public String tulostaString() {
        double vastike = yhtiovastikeM2 * asuntoM2;
        return "----------------" + "\n" + "Asoy ID " + asoyId + "\n" + "Osake "
                + rappu + " " + asuntoNro + "\n" + "Asukas ID "
                + String.format("%03d", asukasId) + "\n" + "Nimi " + nimi + "\n"
                + "Hetu " + hetu + "\n" + "Puhelinnumero " + puhelin + "\n"
                + "Sähköposti " + sposti + "\n" + "Asunto-osakkeen koko m2 "
                + asuntoM2 + "\n" +

                "Yhtiövastike " + vastike + " € / kk" + "\n"
                + "Yhtiövastike per m2 " + yhtiovastikeM2 + " €" + "\n"
                + "Häkkivarasto " + varasto + "\n" + "Saunaoikeus " + saunamaksu
                + "\n" + "Pyykkitupaoikeus " + pesutupa + "\n"
                + "Hallituksen jäsen " + hallitus + "\n" + "Liittymisvuosi "
                + liittymisvuosi + "\n" + "----------------" + "\n";

    }
    
    /**
     * Tulostaa määritellyt asukkaan tiedot
     * @param tulostettavat mitkä tiedot tulostetaan
     * @return tuloste
     */
    public String asukasTulosteString(int[] tulostettavat) {
        double vastike = yhtiovastikeM2 * asuntoM2;
        String tuloste = "---------------- \n";
        
        if (tulostettavat[0] == 1) tuloste += "Osake:               " + rappu + asuntoNro + "\n";
        if (tulostettavat[1] == 1) tuloste += "Nimi:                " + nimi + "\n";
        if (tulostettavat[2] == 1) tuloste += "Hetu:                " + hetu + "\n";
        if (tulostettavat[3] == 1) tuloste += "Puhelinnumero:       " + puhelin + "\n";
        if (tulostettavat[4] == 1) tuloste += "Sähköposti:          " + sposti + "\n";
        if (tulostettavat[5] == 1) tuloste += "Osake m2:            " + asuntoM2 + "\n";
        if (tulostettavat[6] == 1) tuloste += "Yhtiövastike:        " + vastike + "\n";
        if (tulostettavat[7] == 1) tuloste += "Yhtiövastike per m2: " + yhtiovastikeM2 + "\n";
        if (tulostettavat[8] == 1) tuloste += "Häkkivarasto:        " + varasto + "\n";
        if (tulostettavat[9] == 1) tuloste += "Saunaoikeus:         " + saunamaksu + "\n";
        if (tulostettavat[10] == 1) tuloste += "Pesutupaoikeus:     " + pesutupa + "\n";
        if (tulostettavat[11] == 1) tuloste += "Hallitus:           " + hallitus + "\n";
        if (tulostettavat[12] == 1) tuloste += "Liittymisvuosi:     " + liittymisvuosi + "\n";
        
        tuloste += "---------------- \n";
        
        return tuloste;
    }


    /**
     * Antaa asukkaalle (seuraavan vapaana olevan) rekisterinumeron (id).
     * @example
     * <pre name="test">
     * Asukas aku1 = new Asukas();
     * aku1.getAsukasId () === 0;
     * aku1.rekisteroi();
     * Asukas aku2 = new Asukas();
     * aku2.rekisteroi();
     * int n1 = aku1.getAsukasId();
     * int n2 = aku2.getAsukasId();
     * n1 === n2-1;
     * </pre>
     */
    public void rekisteroi() {
        this.asukasId = seuraavaNro;
        seuraavaNro++;
    }

    /**
     * Asukkaan kloonaaminen
     * @return Object kloonattu asukas
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Asukas asukas = new Asukas();
     *   asukas.parse("   3  |  Ankka Aku   | 123");
     *   
     *   Asukas kopio = asukas.clone();
     *   kopio.toString() === asukas.toString();
     *   
     *   asukas.parse("   4  |  Ankka Tupu   | 123");
     *   kopio.toString().equals(asukas.toString()) === false;
     * </pre>
     * 
     */
    @Override
    public Asukas clone() throws CloneNotSupportedException {
        Asukas uusi;
        uusi = (Asukas) super.clone();
        return uusi;
    }


    /**
     * @return palauttaa asukasid:n
     */
    public int getAsukasId() {
        return asukasId;
    }


    /**
     * Apumetodi
     */
    public void taytaAkuAnkkaTiedoilla() {
        asoyId = 2;
        nimi = "Aku Ankka" + HetuTarkistus.rand(1, 300);
        hetu = HetuTarkistus.arvoHetu();
        rappu = "A";
        asuntoNro = HetuTarkistus.rand(1, 50);
        puhelin = "04013131313";
        sposti = "akuankka@gmail.com";
        liittymisvuosi = HetuTarkistus.rand(1950, 2024);
        asuntoM2 = HetuTarkistus.rand(15, 90);
        yhtiovastikeM2 = 10.0;
        varasto = Integer.toString(HetuTarkistus.rand(1, 50));
        hallitus = "puheenjohtaja";
        saunamaksu = "";
        pesutupa = "";
    }


    /**
     * Apumetodi, lisätään asukkaalle tiedot tietyllä asoy id:llä
     * @param haettuAsoyId asoyId jota vastaava tietoa haetaan
     */
    public void taytaAkuAnkkaTiedoilla(int haettuAsoyId) {
        asoyId = haettuAsoyId;
        nimi = "Aku Ankka" + HetuTarkistus.rand(1, 300);
        hetu = HetuTarkistus.arvoHetu();
        rappu = "A";
        asuntoNro = HetuTarkistus.rand(1, 50);
        puhelin = "04013131313";
        sposti = "akuankka@gmail.com";
        liittymisvuosi = HetuTarkistus.rand(1950, 2024);
        asuntoM2 = HetuTarkistus.rand(15, 90);
        yhtiovastikeM2 = 10.0;
        varasto = Integer.toString(HetuTarkistus.rand(1, 50));
        hallitus = "puheenjohtaja";
        saunamaksu = "";
        pesutupa = "";
    }


    /**
     * Apumetodi
     */
    public void taytaIinesAnkkaTiedoilla() {
        asoyId = 3;
        nimi = "Iines Ankka" + HetuTarkistus.rand(1, 300);
        hetu = HetuTarkistus.arvoHetu();
        rappu = "B";
        asuntoNro = HetuTarkistus.rand(1, 50);
        puhelin = "04013131313";
        sposti = "akuankka@gmail.com";
        liittymisvuosi = HetuTarkistus.rand(1950, 2024);
        asuntoM2 = HetuTarkistus.rand(15, 90);
        yhtiovastikeM2 = 10.0;
        varasto = Integer.toString(HetuTarkistus.rand(1, 50));
        hallitus = "puheenjohtaja";
        saunamaksu = "";
        pesutupa = "";
    }


    /**
     * @return Asukkaan nimi
     * @example
     * <pre name="test">
     *   Asukas aku = new Asukas();
     *   aku.taytaAkuAnkkaTiedoilla();
     *   aku.getNimi() =R= "Aku Ankka.*";
     * </pre>
     */
    public String getNimi() {
        return this.nimi;
    }


    /**
     * Asettaa nimen ja palauttaa virheen
     * @param s uusi nimi
     * @return null jos ok, muuten virhe
     */
    public String setNimi(String s) {
        this.nimi = s;
        return null;
    }


    /**
     * @return Asunnon rappukäytävä ja asunnon/huoneiston numero
     * 
     */
    public String getAsunto() {
        return this.rappu + " " + asuntoNro;
    }


    private void setAsukasId(int nro) {
        asukasId = nro;
        if (asukasId >= seuraavaNro)
            seuraavaNro = asukasId + 1;
    }


    /**
     * @return asukkaan id-numero
     * 
     */
    public int getAsoyId() {
        return asoyId;
    }
    
    /**
     * Palauttaa asukkaan tiedot merkkijonona |-merkeillä eroteltuina
     * @return asukastiedot
     * @example
     * <pre name="test">
     * Asukas asukas = new Asukas();
     * asukas.parse("3| 2   |Ankka Aku    |030201-111C   |A   | 2 |04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja||");
     *  asukas.toString().startsWith("3|2|Ankka Aku|030201-111C|A|2|04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja||") === true;
     </pre>
     */
    @Override
    public String toString() {
        return "" + getAsoyId() + "|" + asukasId + "|" + nimi + "|" + hetu + "|"
                + rappu + "|" + asuntoNro + "|" + puhelin + "|" + sposti + "|"
                + liittymisvuosi + "|" + asuntoM2 + "|" + yhtiovastikeM2 + "|"
                + varasto + "|" + hallitus + "|" + saunamaksu + "|" + pesutupa;
    }


    /**
     * @param rivi asukkaat.dat tiedoston rivi
     *  @example
     * <pre name="test">
     *   Asukas asukas = new Asukas();
     *   asukas.parse("3| 2   |Ankka Aku    |030201-111C   |A   | 2 |04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja||");
     *   asukas.toString().startsWith("3|2|Ankka Aku|030201-111C|A|2|04013131313|akuankka@gmail.com|0|0.0|0.0|0|puheenjohtaja||") === true;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        asoyId = Mjonot.erota(sb, '|', asoyId);
        setAsukasId(Mjonot.erota(sb, '|', getAsukasId()));
        nimi = Mjonot.erota(sb, '|', nimi);
        hetu = Mjonot.erota(sb, '|', hetu);
        rappu = Mjonot.erota(sb, '|', rappu);
        asuntoNro = Mjonot.erota(sb, '|', asuntoNro);
        puhelin = Mjonot.erota(sb, '|', puhelin);
        sposti = Mjonot.erota(sb, '|', sposti);
        liittymisvuosi = Mjonot.erota(sb, '|', liittymisvuosi);
        asuntoM2 = Mjonot.erota(sb, '|', asuntoM2);
        yhtiovastikeM2 = Mjonot.erota(sb, '|', yhtiovastikeM2);
        varasto = Mjonot.erota(sb, '|', varasto);
        hallitus = Mjonot.erota(sb, '|', hallitus);
        saunamaksu = Mjonot.erota(sb, '|', saunamaksu);
        pesutupa = Mjonot.erota(sb, '|', pesutupa);
    }


    private static HetuTarkistus hetut = new HetuTarkistus();

    /**
     * @return hetu
     */
    public String getHetu() {
        return hetu;
    }


    /**
     * @param s mitä hetuun asetetaan
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setHetu(String s) {
        String virhe = hetut.tarkista(s);
        if (virhe != null)
            return virhe;
        this.hetu = s;
        return null;
    }


    /**
     * @return palauttaa puhelinnumeron
     * @example
     * <pre name="test">
     *   Asukas aku = new Asukas();
     *   aku.taytaAkuAnkkaTiedoilla();
     *   aku.getPuhelinnumero() =R= "04013131313";
     * </pre>
     */
    public String getPuhelinnumero() {
        return puhelin;
    }


    /**
     * @param s asetettava puhelinnumero
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setPuhelin(String s) {
        this.puhelin = s;
        return null;
    }


    /**
     * @return haettu rappu
     * @example
     * <pre name="test">
     *   Asukas aku = new Asukas();
     *   aku.taytaAkuAnkkaTiedoilla();
     *   aku.getRappu() =R= "A";
     * </pre>
     */
    public String getRappu() {
        return rappu;
    }


    /**
     * @param s asetettava rappu
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setRappu(String s) {
        this.rappu = s;
        return null;
    }


    /**
     * @return haettava asuntonro
     */
    public String getAsuntoNro() {
        return String.valueOf(asuntoNro);
    }


    /**
     * @param s asetettava asuntonro
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setAsuntoNro(String s) {
        try {
            int numero = Integer.valueOf(s);
            this.asuntoNro = numero;
            return null;
        } catch (Exception e) {
            return "Asunnon numero: Syötä kokonaisluku";
        }

    }


    /**
     * @return haettava sähköposti
     * @example
     * <pre name="test">
     *   Asukas aku = new Asukas();
     *   aku.taytaAkuAnkkaTiedoilla();
     *   aku.getEmail() =R= "akuankka@gmail.com";
     * </pre>
     */
    public String getEmail() {
        return sposti;
    }


    /**
     * @param s asetettava sähköposti
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setEmail(String s) {
        this.sposti = s;
        return null;
    }


    /**
     * @return haettava osakkeen pinta-ala
     */
    public String getOsakeM2() {
        return String.valueOf(asuntoM2);
    }


    /**
     * @param s asetettava osakkeen pinta-ala
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setOsakeM2(String s) {

        try {
            double osake = Integer.valueOf(s);
            this.asuntoM2 = osake;
            return null;
        } catch (Exception e) {
            return "Osake m2: Syötä desimaaliluku";
        }

    }


    /**
     * @return haettava yhtiövastike
     */
    public String getYhtiovastike() {
        double yhtiovastike = yhtiovastikeM2 * asuntoM2;
        return String.valueOf(yhtiovastike);
    }



    /**
     * @return haettava yhtiövastike per neliömetri
     * @example
     * <pre name="test">
     *   Asukas aku = new Asukas();
     *   aku.taytaAkuAnkkaTiedoilla();
     *   aku.getYhtiovastikeM2() =R= "10.0";
     * </pre>
     */
    public String getYhtiovastikeM2() {
        return String.valueOf(yhtiovastikeM2);
    }


    /**
     * @param s asetettava yhtiövastike per neliömetri
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setYhtiovastikeM2(String s) {
        try {
            int vastikeM2 = Integer.valueOf(s);
            this.yhtiovastikeM2 = vastikeM2;
            return null;
        } catch (Exception e) {
            return "Yhtiövastike per m2: Syötä desimaaliluku";
        }
    }


    /**
     * @return haettava varasto
     */
    public String getHakkivarasto() {
        return varasto;
    }


    /**
     * @param s asetettava varasto
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setVarasto(String s) {
        this.varasto = s;
        return null;
    }


    /**
     * @return saunamaksu maksettu mihin pvm asti
     */
    public String getSaunaoikeus() {
        return saunamaksu;
    }


    /**
     * @param s asettaa mihin asti saunamaksu maksettu
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setSaunaoikeus(String s) {
        String[] formats = { "d.M.yyyy", "dd.MM.yyyy", "d.MM.yyyy",
        "dd.M.yyyy" };

        for (String format : formats) {
            try {
                LocalDate date = LocalDate.parse(s,
                        DateTimeFormatter.ofPattern(format));
                this.saunamaksu = date
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                return null;
            } catch (Exception e) {
                //
            }
        }
        return "Saunaoikeus: Syötä päivämäärä muodossa pp.kk.vvvv";
    }


    /**
     * @return pyykkitupa maksettu mihin asti
     */
    public String getPyykkitupa() {
        return pesutupa;
    }


    /**
     * @param s asettaa mihin asti pyykkitupa käytössä
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setPyykkitupa(String s) {
        String[] formats = { "d.M.yyyy", "dd.MM.yyyy", "d.MM.yyyy",
                "dd.M.yyyy" };

        for (String format : formats) {
            try {
                LocalDate date = LocalDate.parse(s,
                        DateTimeFormatter.ofPattern(format));
                this.pesutupa = date
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                return null;
            } catch (Exception e) {
                //
            }
        }
        return "Pyykkitupa: Syötä päivämäärä muodossa pp.kk.vvvv";


    }


    /**
     * @return paikka hallituksessa
     * @example
     * <pre name="test">
     *   Asukas aku = new Asukas();
     *   aku.taytaAkuAnkkaTiedoilla();
     *   aku.getHallitus() =R= "puheenjohtaja";
     * </pre>
     */
    public String getHallitus() {
        return hallitus;
    }


    /**
     * @param s asetettava paikka hallituksessa
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */
    public String setHallitus(String s) {
        this.hallitus = s;
        return null;
    }


    /**
     * @return milloin liittynyt
     */
    public String getLiittymisvuosi() {
        return String.valueOf(liittymisvuosi);
    }


    /**
     * @param s asetettava liittymisvuosi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus
     */ 
    public String setLiittymisvuosi(String s) {
        try {
            int vuosi = Integer.valueOf(s);
            this.liittymisvuosi = vuosi;
            return null;
        } catch (Exception e) {
            return "Liittymisvuosi: Syötä kokonaisluku";
        }

    }
    

    /**
     * @param args ei argumentteja
     */
    public static void main(String[] args) {
        Asukas aku = new Asukas();
        Asukas aku2 = new Asukas();

        aku.rekisteroi();
        aku2.rekisteroi();

        aku.tulosta(System.out);
        aku2.tulosta(System.out);

        aku.taytaAkuAnkkaTiedoilla();
        aku2.taytaAkuAnkkaTiedoilla();

        aku.tulosta(System.out);
        aku2.tulosta(System.out);

    }

}
