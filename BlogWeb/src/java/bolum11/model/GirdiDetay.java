package bolum11.model;

import java.util.List;

public class GirdiDetay extends Girdi {

    private Blog blog;
    private Kullanici kullanici;
    private List<YorumDetay> yorumlar;
    private int yorumSayisi = 0;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public List<YorumDetay> getYorumlar() {
        return yorumlar;
    }

    public void setYorumlar(List<YorumDetay> yorumlar) {
        this.yorumlar = yorumlar;
    }

    public int getYorumSayisi() {
        return yorumSayisi;
    }

    public void setYorumSayisi(int yorumSayisi) {
        this.yorumSayisi = yorumSayisi;
    }

}
