
package bolum11.model;

import bolum11.genel.Araclar;
import java.sql.Timestamp;

public class Girdi {

    private int girdiID;
    private int blogID;
    private int kullaniciID;
    private String girdiBaslik;
    private String girdiIcerik;
    private Timestamp girdiTarih;

    public void hosgeldinGirdisiHazirla(Kullanici k, Blog b){
        this.blogID=b.getBlogID();
        this.kullaniciID = k.getKullaniciID();
        this.girdiBaslik = "Bloğuma Hoş Geldiniz.";
        this.girdiIcerik = "Bu bir deneme girdisidir. Yeni girdi eklemek" +
                "ya da bu girdiyi degistirmek icin giris linkini kullaniniz";
        this.girdiTarih = Araclar.yeniTimeStampOlustur();
    }
    public int getBlogID() {
        return blogID;
    }
    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }
    public String getGirdiBaslik() {
        return girdiBaslik;
    }
    public void setGirdiBaslik(String girdiBaslik) {
        this.girdiBaslik = girdiBaslik;
    }
    public int getGirdiID() {
        return girdiID;
    }
    public void setGirdiID(int girdiID) {
        this.girdiID = girdiID;
    }
    public String getGirdiIcerik() {
        return girdiIcerik;
    }
    public void setGirdiIcerik(String girdiIcerik) {
        this.girdiIcerik = girdiIcerik;
    }
    public int getKullaniciID() {
        return kullaniciID;
    }
    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }
    public Timestamp getGirdiTarih() {
        return girdiTarih;
    }
    public void setGirdiTarih(Timestamp girdiTarih) {
        this.girdiTarih = girdiTarih;
    }

      public String getGirdiOzetIcerik(){
        if(this.girdiIcerik.length() > 100)
            return this.girdiIcerik.substring(0, 100) + " ......";
        else
            return this.girdiIcerik;
    }

    public String getTarih(){
        return Araclar.sadeceTarihGoster(this.girdiTarih);
    }

    public String getZaman(){
        return Araclar.sadeceZamanGoster(this.girdiTarih);
    }
}
