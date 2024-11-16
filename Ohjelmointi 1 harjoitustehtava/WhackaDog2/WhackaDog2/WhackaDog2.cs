using System;
using Jypeli;
using Jypeli.Assets;

namespace WhackaDog2;

/// @author Juuso Nikkinen
/// @version 27.04.2024
/// <summary>
/// Whac-A-Dog niminen peli, jossa pelaajan on tarkoitus läimäyttää koiraa hiirellä klikkaamalla.
/// Mikäli pelaaja osuu koiraan, saa pelaaja pisteen.
/// </summary>
public class WhackaDog2 : PhysicsGame
{
    
    
    //Pelin vakiot
    private const double PeliAika = 30.0;
    private const double KoiraSijaintiY = -410.0;
    private const int LisaysEtaisyysX = 300;
    private const int LisaysEtaisyysY = 114;
    private const double Maastokerroin = 1.3;
    private const double KoiranElinIka = 1;
    private const int KoiraNopeus = 2000;
    private const double KoiraKoko = 150;
    
    //Pelin aikana muuttuvat attribuutit
    private double _sijaintiX = -200.0; //Sijainti x-koordinaatissa johon koira lisätään
    private double _sijaintiY; //Sijainti y-koordinaatissa johon koira lisätään
    private int _koiraLayer; //Taso jolle koira lisätään
    
    //Peli- ja fysiikkaobjektit
    private PhysicsObject _koira;
    private PhysicsObject _vasara;
    private GameObject _maasto;
    private GameObject _etulevy;
    private GameObject _takalevy;

    //Pelissä käytettävät kuvat, äänet ja muodot
    private readonly Image _vasaraKuva = LoadImage("vasara.png");
    private readonly Image _koiraKuva = LoadImage("koira.png");
    private readonly Image _maastoKuva = LoadImage("maasto.png");
    private readonly Image _maastoKuva2 = LoadImage("maasto2.png");
    private Shape _maastoMuoto;
    private Shape _maastoMuoto2;
    private SoundEffect _haukku = LoadSoundEffect("haukku.wav");
    
    //Laskurit ja tarkistimet + pistetaulukko
    private Timer _koiralaskuri;
    private DoubleMeter _alaspainlaskuri;
    private Timer _aikalaskuri;
    private Timer _poistolaskuri;
    private bool _peliKaynnissa;
    private IntMeter _pistelaskuri;
    private int _pisteetMaxLkm = 10;
    private int _pisteetLkm;
    private int[] _pisteTaulukko;
    private bool _voikoLyoda;
    private bool _koiralaskuriOlemassa;
    
    
    /// <summary>
    /// Ohjelma joka käydään läpi käynnistyessä.
    /// Luo alkuvalikon ja kutsuu tarvittavia aliohjelmia.
    /// </summary>
    public override void Begin()
    {
        MultiSelectWindow alkuvalikko = new MultiSelectWindow("Whac-A-Dog", "Aloita peli", "Lopeta");
        Add(alkuvalikko);
        _pisteTaulukko = new int[_pisteetMaxLkm];
        alkuvalikko.AddItemHandler(0, AloitaPeli);
        alkuvalikko.AddItemHandler(1, Exit);
    }

    
    /// <summary>
    /// Aloittaa pelin ja kutsuu kentän luontiin ja pelin valmisteluun tarvittavia aliohjelmia.
    /// </summary>
    private void AloitaPeli()
    {
        _maastoMuoto = Shape.FromImage(_maastoKuva);
        _maastoMuoto2 = Shape.FromImage(_maastoKuva2);
        
        LuoKentta();
        LuoAikalaskuri();
        LaskeAlaspain();
        LuoPistelaskuri();
        KoiraAjastin();

        Mouse.Listen(MouseButton.Left, ButtonState.Pressed, HiirenKlikkaus, "Klikkaus");
        
        LisaaKoira();
    }

    
    /// <summary>
    /// Aloittaa pelin alusta.
    /// </summary>
    private void AloitaAlusta()
    {
        ClearAll();
        _koiralaskuriOlemassa = false;
        AloitaPeli();
    }
    
    
    /// <summary>
    /// Mitä tehdään kun hiiren vasenta nappia klikataan.
    /// Ohjaa vasaran liikettä
    /// </summary>
    private void HiirenKlikkaus()
    {
        Vector hiiriSijainti = Mouse.PositionOnWorld;
        double vasaraX = hiiriSijainti.X + 300;
        double vasaraY = hiiriSijainti.Y;

        _vasara = new PhysicsObject(275, 858);
        _vasara.Image = _vasaraKuva;
        _vasara.Position = new Vector(vasaraX, vasaraY);
        _vasara.IgnoresCollisionResponse = true;
        
        Add(_vasara,4);
        
        _vasara.AngularVelocity = 10;
        Timer.CreateAndStart(0.15, _vasara.Destroy);
    }
    
    
    /// <summary>
    /// Lisätään koira-olio peliin, arvottuun sijaintiin.
    /// </summary>
    private void LisaaKoira()
    {
        KoiraAjastin();
        if (_peliKaynnissa)
        {
            _koira = new PhysicsObject(KoiraKoko, KoiraKoko * 1.23);
            _voikoLyoda = true;
            _koira.Image = _koiraKuva;
            _koira.Shape = Shape.Rectangle;
            ArvoSijainti();
            _koira.X = _sijaintiX;
            _koira.Y = _sijaintiY;
            Add(_koira, _koiraLayer);
            
            _koira.MoveTo(new Vector(_sijaintiX, _sijaintiY + 150), KoiraNopeus);
            Mouse.ListenOn(_koira, MouseButton.Left, ButtonState.Pressed, KoiraanOsui, null);
        }    
    }

    
    /// <summary>
    /// Ohjelma joka käydään läpi kun koiraan osutaan. Aktivoi tehosteet ja koiran poiston.
    /// </summary>
    private void KoiraanOsui()
    {
        if (_voikoLyoda == false) return;
        _voikoLyoda = false;
        _pistelaskuri.Value += 1;
        
        _koiralaskuri.Stop();
        
        Explosion rajahdys = new Explosion(100);
        rajahdys.Position = _koira.Position;
        rajahdys.Speed = 500;
        rajahdys.Force = 0;
        rajahdys.Sound = _haukku;
        Add(rajahdys,4);
        KoiraAlas();
    }
    
    
    /// <summary>
    /// Liikuttaa koiran alas ja poistaa olion.
    /// </summary>
    private void KoiraAlas()
    {
        _koiralaskuri.Reset();
        _koira.MoveTo(new Vector(_sijaintiX, _sijaintiY - 250), KoiraNopeus);
        _poistolaskuri = new Timer();
        _poistolaskuri.Interval = 0.2;
        _poistolaskuri.Timeout += KoiraPoistajaLisaa;
        _poistolaskuri.Start(1);
    }


    /// <summary>
    /// Poistaa koiran ja lisää uuden koiran.
    /// </summary>
    private void KoiraPoistajaLisaa()
    {
        _koira.Destroy();
        _voikoLyoda = true;
        _koiralaskuri.Start(1);
        LisaaKoira();
    }

    
    /// <summary>
    /// Luo pelikentän
    /// </summary>
    private void LuoKentta()
    {
        Label nimike = new Label(600.0,20.0,"Whac-A-Dog");
        nimike.TextColor = Color.White;
        nimike.BorderColor = Color.Black;
        Font nimikeFontti = new Font(70, true);
        nimike.Font = nimikeFontti;
        nimike.Y = 230;
        Add(nimike, 1);

        Label nimikePisteet = new Label(300, 50, "Pisteet");
        Label nimikeAika = new Label(300, 50,"Aika");
        nimikePisteet.TextColor = Color.White;
        nimikeAika.TextColor = Color.White;
        nimikePisteet.BorderColor = Color.Black;
        nimikeAika.BorderColor = Color.Black;
        Font nimikePieniFontti = new Font(30, true);
        nimikePisteet.Font = nimikePieniFontti;
        nimikeAika.Font = nimikePieniFontti;
        nimikeAika.Y = 170;
        nimikePisteet.Y = 170;
        nimikeAika.X = -200;
        nimikePisteet.X = 200;
        Add(nimikeAika, 1);
        Add(nimikePisteet, 1);
        
        Camera.ZoomToLevel();

        LisaaMaastoEtu(0.0, -342.0, 3);
        LisaaMaastoTaka(0.0, -342.0, 1);
        LisaaMaastoEtu(0.0, -228.0, 1);
        LisaaMaastoTaka(0.0, -228.0, -1);
        LisaaMaastoEtu(0.0, -114.0, -1);
        LisaaMaastoTaka(0.0, -114.0, -3);
        

        _etulevy = new GameObject(726.0 * Maastokerroin, 300.0);
        _etulevy.Shape = Shape.Rectangle;
        _etulevy.Color = new Color(68, 83, 46);
        _etulevy.X = 0.0;
        _etulevy.Y = -500.0;
        Add(_etulevy, 4);

        _takalevy = new GameObject(756.0 * Maastokerroin, 600.0);
        _takalevy.Shape = Shape.Rectangle;
        _takalevy.Color = new Color(122, 155, 75);
        _takalevy.X = 0.0;
        _takalevy.Y = 0.0;
        Add(_takalevy, -3);
    }

    
    /// <summary>
    /// Lisää rivissä olevan edemmän maasto-objektin GameObjectina.
    /// </summary>
    /// <param name="xkoord"></param>X-koordinaatti johon maasto lisätään
    /// <param name="ykoord"></param>Y-koordinaatti johon maasto lisätään
    /// <param name="taso"></param>Taso jolle maasto lisätään
    /// <returns>Palauttaa maasto-objektin</returns>
    private void LisaaMaastoEtu(double xkoord, double ykoord, int taso)
    {
        _maasto = new GameObject(757.0 * Maastokerroin, 42.0 * Maastokerroin);
        _maasto.Shape = _maastoMuoto;
        _maasto.Image = _maastoKuva;
        _maasto.X = xkoord;
        _maasto.Y = ykoord;
        Add(_maasto, taso);
    }
    
    
    /// <summary>
    /// Lisää rivissä olevan taaemman maasto-objektin GameObjectina.
    /// </summary>
    /// <param name="xkoord"></param>X-koordinaatti johon maasto lisätään
    /// <param name="ykoord"></param>Y-koordinaatti johon maasto lisätään
    /// <param name="taso"></param>Taso jolle maasto lisätään
    /// <returns>Palauttaa maasto-objektin</returns>
    private void LisaaMaastoTaka(double xkoord, double ykoord, int taso)
    {
        _maasto = new GameObject(757.0 * Maastokerroin, 134.0 * Maastokerroin);
        _maasto.Shape = _maastoMuoto2;
        _maasto.Image = _maastoKuva2;
        _maasto.X = xkoord;
        _maasto.Y = ykoord;
        Add(_maasto, taso);
    }
    
    
    /// <summary>
    /// Luo peliaikalaskurin
    /// </summary>
    private void LuoAikalaskuri()
    {
        _alaspainlaskuri = new DoubleMeter(PeliAika);
    
        _aikalaskuri = new Timer();
        _aikalaskuri.Interval = 0.1;
        _aikalaskuri.Timeout += LaskeAlaspain;
        _aikalaskuri.Start();

        Label aikanaytto = new Label(300, 100);
        aikanaytto.X = -200;
        aikanaytto.Y = 100.0;
        
        aikanaytto.TextColor = Color.White;
        aikanaytto.Color = Color.Black;
        aikanaytto.DecimalPlaces = 1;
        aikanaytto.BindTo(_alaspainlaskuri);
        Add(aikanaytto, 1);
    }

    
    /// <summary>
    /// Käynnistää sekuntilaskurin ja aktivoi loppuvalikon kun aika on päättynyt (eli peli on loppunut).
    /// </summary>
    private void LaskeAlaspain()
    {
        _peliKaynnissa = true;
        _alaspainlaskuri.Value -= 0.1;
        
        if (_alaspainlaskuri.Value <= 0)
        {
            _peliKaynnissa = false;
            _aikalaskuri.Stop();
            MultiSelectWindow loppuvalikko = new MultiSelectWindow("Peli päättyi", "Pelaa uudelleen", "Lopeta");
            
            LisaaPisteetTaulukkoon();
            TulostaPisteet();
            KoiraAlas();

            loppuvalikko.Y = -15.0;
            
            Add(loppuvalikko);
            loppuvalikko.AddItemHandler(0, AloitaAlusta);
            loppuvalikko.AddItemHandler(1, Exit);
        }
    }


    /// <summary>
    /// Luodaan ajastin joka käsittelee kuinka pitkään koira on näkyvissä
    /// </summary>
    private void KoiraAjastin()
    {
        if (_koiralaskuriOlemassa) return;
        _koiralaskuriOlemassa = true;
        _koiralaskuri = new Timer();
        _koiralaskuri.Interval = KoiranElinIka;
        _koiralaskuri.Timeout += KoiraAlas;
        _koiralaskuri.Start(1);

        //Label koirajastinnaytto = new Label(300, 200);
        //koirajastinnaytto.Y = 300;
        //koirajastinnaytto.BindTo(koiralaskuri.SecondCounter);
        //Add(koirajastinnaytto);
    }
    

    /// <summary>
    /// Luo ja sijoittaa pistelaskurin.
    /// </summary>
    private void LuoPistelaskuri()
    {
        _pistelaskuri = new IntMeter(0);               
      
        Label pistenaytto = new Label(300,100); 
        pistenaytto.X = 200;
        pistenaytto.Y = 100;
        pistenaytto.TextColor = Color.White;
        pistenaytto.Color = Color.Black;

        pistenaytto.BindTo(_pistelaskuri);
        Add(pistenaytto, 1);
    }

    
    /// <summary>
    /// Arpoo sijainnin mihin koira sijoitetaan (X- ja Y-koordinaatit ja taso)
    /// </summary>
    private void ArvoSijainti()
    {
        int luku = RandomGen.NextInt(1, 9);
        
        switch (luku)
        {
            case 1:
                _sijaintiX = -1 * LisaysEtaisyysX;
                _sijaintiY = KoiraSijaintiY;
                _koiraLayer = 2;
                break;
            case 2:
                _sijaintiX = 0.0;
                _sijaintiY = KoiraSijaintiY;
                _koiraLayer = 2;
                break;
            case 3:
                _sijaintiX = LisaysEtaisyysX;
                _sijaintiY = KoiraSijaintiY;
                _koiraLayer = 2;
                break;
            case 4:
                _sijaintiX = -1 * LisaysEtaisyysX;
                _sijaintiY = KoiraSijaintiY + LisaysEtaisyysY;
                _koiraLayer = 0;
                break;
            case 5:
                _sijaintiX = 0.0;
                _sijaintiY = KoiraSijaintiY + LisaysEtaisyysY;
                _koiraLayer = 0;
                break;
            case 6:
                _sijaintiX = 0.0;
                _sijaintiY = KoiraSijaintiY + LisaysEtaisyysY;
                _koiraLayer = 0;
                break;
            case 7:
                _sijaintiX = LisaysEtaisyysX;
                _sijaintiY = KoiraSijaintiY + 2*LisaysEtaisyysY;
                _koiraLayer = -2;
                break;
            case 8:
                _sijaintiX = LisaysEtaisyysX;
                _sijaintiY = KoiraSijaintiY + 2*LisaysEtaisyysY;
                _koiraLayer = -2;
                break;
            case 9:
                _sijaintiX = LisaysEtaisyysX;
                _sijaintiY = KoiraSijaintiY + 2*LisaysEtaisyysY;
                _koiraLayer = -2;
                break;
        }
    }

    
    /// <summary>
    /// Lisää pisteet pistetaulukkoon
    /// </summary>
    private void LisaaPisteetTaulukkoon()
    {
        if (_pisteetLkm == _pisteetMaxLkm)
        {
            int[] uusiTaulukko = new int[_pisteetMaxLkm];
            for (int i = 0; i < _pisteetMaxLkm - 1; i++)
            {
                uusiTaulukko[i] = _pisteTaulukko[i + 1];
            }

            uusiTaulukko[_pisteetMaxLkm - 1] = _pistelaskuri;
            _pisteTaulukko = uusiTaulukko;
        }
        else
        {
            _pisteTaulukko[_pisteetLkm] = _pistelaskuri;
            _pisteetLkm++;
        }
    }


    /// <summary>
    /// Tulostaa pisteet pistetaulukosta
    /// </summary>
    private void TulostaPisteet()
    {
        String pisteetListana = "Viimeisimmät pisteet \n";
        for (int i = 0; i < _pisteetLkm; i++)
        {
            pisteetListana += _pisteTaulukko[i] + " pistettä \n";
        }
        
        Label pisteListaus = new Label(250, 400, pisteetListana);
        pisteListaus.X = 330;
        pisteListaus.Y = Screen.Top - 510;
        pisteListaus.Color = new Color(255, 255, 255, 200);
        pisteListaus.TextColor = Color.Black;
        Add(pisteListaus);
    }
    
    
}