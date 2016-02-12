package bolum11.model;

import bolum11.genel.Araclar;
import java.sql.Timestamp;

public class Blog {

    private int blogID;
    private int kullaniciID;
    private String blogBaslik;
    private String aciklama;
    private Timestamp olusturmaTarih;

    public Blog(){

    }

    public Blog(Blog b){
        this.blogID = b.blogID;
        this.kullaniciID = b.kullaniciID;
        this.aciklama = b.aciklama;
        this.blogBaslik = b.blogBaslik;
        this.olusturmaTarih = b.olusturmaTarih;
    }

    public void yeniKullaniciIcinBlogBilgisiHazirla(Kullanici k){
        this.kullaniciID = k.getKullaniciID();
        this.blogBaslik = "Merhaba. Ben " + k.getKullaniciAdSoyad() +
                ". Bloğuma hoş geldiniz.";
        this.aciklama = "Bu otomatik oluşturulmuş bir blogdur. " +
                "Blog içeriğini değiştirmek istiyorsanız kullanıcı " +
                "adı ve şifreniz ile sisteme giriş yapınız. ";
        this.olusturmaTarih=Araclar.yeniTimeStampOlustur();
    }

    public String getAciklama() {
        return aciklama;
    }
    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
    public String getBlogBaslik() {
        return blogBaslik;
    }
    public void setBlogBaslik(String blogBaslik) {
        this.blogBaslik = blogBaslik;
    }
    public int getBlogID() {
        return blogID;
    }
    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }
    public int getKullaniciID() {
        return kullaniciID;
    }
    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }
    public Timestamp getOlusturmaTarih() {
        return olusturmaTarih;
    }
    public void setOlusturmaTarih(Timestamp olusturmaTarih) {
        this.olusturmaTarih = olusturmaTarih;
    }

      public String getBlogOzetAciklama(){
        if(this.aciklama.length() > 100)
            return this.aciklama.substring(0, 100) + " ......";
        else
            return this.aciklama;
    }
}
