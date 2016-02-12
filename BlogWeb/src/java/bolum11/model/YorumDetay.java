package bolum11.model;

import bolum11.genel.Araclar;

public class YorumDetay extends Yorum {
    private Girdi girdi;
    private Kullanici kullanici;
    public Girdi getGirdi() {
        return girdi;
    }

    public void setGirdi(Girdi girdi) {
        this.girdi = girdi;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public String getTarihZaman(){
        return Araclar.tarihZamanGoster(super.getYorumTarih());
    }
}
