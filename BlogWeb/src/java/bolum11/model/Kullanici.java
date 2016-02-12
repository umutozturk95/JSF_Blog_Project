package bolum11.model;

import bolum11.genel.Araclar;
import java.sql.Timestamp;

public class Kullanici {

    private int kullaniciID;
    private String kullaniciEmail;
    private String kullaniciSifre;
    private String kullaniciAdSoyad;
    private String kullaniciIzin;
    private Timestamp kayitTarih;

    public void bosKullaniciOlustur(){
        this.kullaniciAdSoyad = "Yeni Kullanici ";
        this.kullaniciEmail = "deneme@deneme.com";
        this.kullaniciSifre = "deneme";
        this.kullaniciIzin = "A";
        this.kayitTarih = Araclar.yeniTimeStampOlustur();
    }
    public String getKullaniciAdSoyad() {
        return kullaniciAdSoyad;
    }
    public void setKullaniciAdSoyad(String kullaniciAdSoyad) {
        this.kullaniciAdSoyad = kullaniciAdSoyad;
    }
    public String getKullaniciEmail() {
        return kullaniciEmail;
    }
    public void setKullaniciEmail(String kullaniciEmail) {
        this.kullaniciEmail = kullaniciEmail;
    }
    public int getKullaniciID() {
        return kullaniciID;
    }
    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }
    public String getKullaniciIzin() {
        return kullaniciIzin;
    }
    public void setKullaniciIzin(String kullaniciIzin) {
        this.kullaniciIzin = kullaniciIzin;
    }
    public String getKullaniciSifre() {
        return kullaniciSifre;
    }
    public void setKullaniciSifre(String kullaniciSifre) {
        this.kullaniciSifre = kullaniciSifre;
    }
    public Timestamp getKayitTarih() {
        return kayitTarih;
    }
    public void setKayitTarih(Timestamp kayitTarih) {
        this.kayitTarih = kayitTarih;
    }
}
