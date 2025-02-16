package tietorakenne.test;
// Generated by ComTest BEGIN
import java.io.File;
import tietorakenne.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.04.26 12:24:48 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class AsoylistaTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa63 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa63() throws SailoException {    // Asoylista: 63
    Asoylista asoylista = new Asoylista(); 
    Asoy aku1 = new Asoy(), aku2 = new Asoy(); 
    assertEquals("From: Asoylista line: 67", 0, asoylista.getLkm()); 
    asoylista.lisaa(aku1); 
    assertEquals("From: Asoylista line: 69", 1, asoylista.getLkm()); 
    asoylista.lisaa(aku2); 
    assertEquals("From: Asoylista line: 71", 2, asoylista.getLkm()); 
    asoylista.poista(aku1); 
    assertEquals("From: Asoylista line: 74", 1, asoylista.getLkm()); 
    asoylista.poista(aku2); 
    assertEquals("From: Asoylista line: 77", 0, asoylista.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista89 
   * @throws SailoException when error
   */
  @Test
  public void testPoista89() throws SailoException {    // Asoylista: 89
    Asoylista asoylista = new Asoylista(); 
    Asoy aku1 = new Asoy(), aku2 = new Asoy(); 
    assertEquals("From: Asoylista line: 93", 0, asoylista.getLkm()); 
    asoylista.lisaa(aku1); 
    assertEquals("From: Asoylista line: 95", 1, asoylista.getLkm()); 
    asoylista.lisaa(aku2); 
    assertEquals("From: Asoylista line: 97", 2, asoylista.getLkm()); 
    asoylista.poista(aku1); 
    assertEquals("From: Asoylista line: 100", 1, asoylista.getLkm()); 
    asoylista.poista(aku2); 
    assertEquals("From: Asoylista line: 103", 0, asoylista.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta201 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta201() throws SailoException {    // Asoylista: 201
    Asoylista asoylista = new Asoylista(); 
    Asoy aku1 = new Asoy(), aku2 = new Asoy(); 
    aku1.taytaTestiTiedoilla(); 
    aku2.taytaTestiTiedoilla(); 
    String hakemisto = "testikelmit"; 
    String tiedNimi = hakemisto + "/asoylista"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    asoylista.lueTiedostosta(hakemisto); 
    fail("Asoylista: 215 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    asoylista.lisaa(aku1); 
    asoylista.lisaa(aku2); 
    asoylista.tallenna(hakemisto); 
    asoylista = new Asoylista();  // Poistetaan vanhat luomalla uusi
    asoylista.lueTiedostosta(hakemisto);  // johon ladataan tiedot tiedostosta.
    assertEquals("From: Asoylista line: 221", aku1.toString(), asoylista.anna(0).toString()); 
    assertEquals("From: Asoylista line: 222", aku2.toString(), asoylista.anna(1).toString()); 
    try {
    asoylista.anna(2); 
    fail("Asoylista: 223 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    asoylista.lisaa(aku2); 
    asoylista.tallenna(hakemisto); 
    assertEquals("From: Asoylista line: 226", true, ftied.delete()); 
    assertEquals("From: Asoylista line: 227", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa254 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa254() throws SailoException,CloneNotSupportedException {    // Asoylista: 254
    Asukkaat asukkaat = new Asukkaat(); 
    Asukas aku1 = new Asukas(), aku2 = new Asukas(); 
    aku1.rekisteroi(); aku2.rekisteroi(); 
    assertEquals("From: Asoylista line: 260", 0, asukkaat.getLkm()); 
    asukkaat.korvaaTaiLisaa(aku1); assertEquals("From: Asoylista line: 261", 1, asukkaat.getLkm()); 
    asukkaat.korvaaTaiLisaa(aku2); assertEquals("From: Asoylista line: 262", 2, asukkaat.getLkm()); 
    Asukas aku3 = aku1.clone(); 
    aku3.setNimi("testiankka"); 
    asukkaat.korvaaTaiLisaa(aku3); 
    assertEquals("From: Asoylista line: 266", 2, asukkaat.getLkm()); 
  } // Generated by ComTest END
}