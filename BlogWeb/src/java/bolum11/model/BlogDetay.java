package bolum11.model;

import java.util.List;

public class BlogDetay extends Blog{
    
    private Kullanici kullanici;
    private List<GirdiDetay> girdiler;
    private int girdiSayisi;
    private int yorumSayisi;

    public BlogDetay(Blog b) {
        super(b);
    }
    
    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public int getGirdiSayisi() {
        return girdiSayisi;
    }

    public void setGirdiSayisi(int girdiSayisi) {
        this.girdiSayisi = girdiSayisi;
    }

    public int getYorumSayisi() {
        return yorumSayisi;
    }

    public void setYorumSayisi(int yorumSayisi) {
        this.yorumSayisi = yorumSayisi;
    }

}
