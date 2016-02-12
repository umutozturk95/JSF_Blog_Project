package bolum11.beans;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.Pattern;
import bolum11.genel.Araclar;
import bolum11.model.Blog;
import bolum11.model.BlogDetay;
import bolum11.model.Girdi;
import bolum11.model.GirdiDetay;
import bolum11.model.Kullanici;
import bolum11.model.Yorum;
import bolum11.model.YorumDetay;
import bolum11.veritabani.VeritabaniIslemleri;

@ManagedBean(name = "KullaniciBean")
@SessionScoped
public class KullaniciBean implements Serializable {

    //Kullanıcı adının email şeklinde girilmesini sağlayalım
    //Eğer e-mail formatında yazılmamış ise hata versin.
    @Pattern(regexp = ".+@.+\\.[a-z]+",
    message = "Kullanıcı adı e-mail adresi formatında değil!!")
    private String kullaniciAdi;
    private String sifre;
    private Kullanici kullaniciBilgisi = null;
    private List<Blog> blogListesi = null;
    private BlogDetay seciliBlog = null;
    private GirdiDetay seciliGiridi = null;
    private Yorum seciliYorum = null;
    private List<Girdi> girdiListesi = null;
    private List<Girdi> enGuncelBesGirdi = null;

    // Eklenecek silinece Blog, Girdi ve Yorumlar icin
    // kullanılan yardımcı üye değişkenler.
    private String blogBaslik = null;
    private String blogAciklama = null;
    private String girdiBaslik = null;
    private String girdiIcerik = null;
    private String yorumBaslik = null;
    private String yorumIcerik = null;

    /**
     * Bu metot kullanıcının giriş(login işlemini sağlar).
     * Kullanıcının girdiği kullanıcı adı ve şifre VeritabaniIslemleri
     * aracılığıyla veritabanından kontrol edilir.
     * <p>
     * Eğer bilgiler doğruysa yeni bir Kullanici nesnesi oluşturulur.
     * Daha sonra kullanıcıların sahip olduğu bloglar hakkındaki bilgi
     * veritabanından çekilir.
     *
     * @return Bilgiler dogruysa kullanici sayfasanı yonlendirilir.
     * Bilgilerde bir sorun varsa null döndürür. Bu işlem tekrar giriş
     * sayfasına yönlendirme yapılmasını sağlar.
     * 
     */
    public String girisYap() {

        try {
            VeritabaniIslemleri vi = new VeritabaniIslemleri();
            vi.baglan();
            kullaniciBilgisi = vi.kullaniciBilgisiniGetir(kullaniciAdi, sifre);
            kullanicininBloglariniListele();
            vi.baglantiyiKes();
            if (kullaniciBilgisi != null) {
                return "kullanici";
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

     /**
     * Bu metot kullandığımız önemli üye değişkenleri null
     * yaptıktan sonra tekrar giriş sayfasına yönledirir.
     *
     * @return Yönelndirme yapılacak giriş sayfasının adı.
     */
    public String cikis() {
        
        this.kullaniciBilgisi = null;
        this.kullaniciAdi = null;
        this.sifre = null;
        this.blogListesi = null;
        this.seciliBlog = null;
        this.seciliGiridi = null;
        this.seciliYorum = null;
        return "index";
    }

    /**
     * Bu metot aracılığıyla VeritabaniIslemleri.java
     * sınıf aracılığıyla kullanıcın bloglarının listesi çekilir
     * ve blogListesi isimli üye değişkene kaydedilir.
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     */
    public String kullanicininBloglariniListele() {

        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        try {
            vi.baglan();
            blogListesi = vi.birKullaniciyaAitTumBloglariGetir(this.kullaniciBilgisi);

            vi.baglantiyiKes();
        } catch (Exception ex) {
            return null;
        }
        return "kullanici";

    }

    /**
     * Bu metot aracılığıyla VeritabaniIslemleri.java
     * sınıfı aracılığıyla veitabanından tüm blogların listesi çekilir
     * ve blogListesi isimli üye değişkene kaydedilir.
     * Bu işlem sonrasında kullanici sayfasani yonlendirme
     * yapılabilmesi için "kullanici" isimli bir String döndürülür.
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see Blog
     * @see BlogDetay
     * @see VeritabaniIslemleri
     */
    public String tumBloglariListele() {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        try {
            vi.baglan();
            blogListesi = vi.tumBloglariGetir();
            vi.baglantiyiKes();
        } catch (Exception ex) {
            return null;
        }

        return "kullanici";
    }

    /**
     * Kullanıcın web arayüzü aracılığıyla seçtiği bloğun
     * bilgisini getirir. Blogla ilgili en güncel 5 girdinin
     * özet bilgisini çeker. Bunun yanında en güncel girdiyi
     * gösterilecek girdi olarak kabul eder ve bu girdi için
     * detaylı bilgiyi veritabanından çeker.
     *
     * Bu işlem sonrasında kullanici sayfasani yonlendirme
     * yapılabilmesi için "kullanici" isimli bir String döndürülür.
     *
     * @param blog Yonlendirilecek bloğun bilgisinin olduğu nesne
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Girdi
     * @see GirdiDetay
     */
    public String blogaGit(Blog blog) {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        try {
            vi.baglan();
            int id = vi.blogIDSiniBul(blog.getOlusturmaTarih());
            blog.setBlogID(id);
            this.seciliBlog = vi.blogDetayGetir(blog.getBlogID());
            this.seciliGiridi = vi.enGuncelGirdiyiGetir(blog.getBlogID());
            this.enGuncelBesGirdi = vi.enGuncelBesGirdiyiGetir(blog.getBlogID());
            vi.baglantiyiKes();
            return "blog";
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Kullanıcının seçtiği bloğu siler.
     *
     * @param blog Silinecek bloğun bilgisinin olduğu nesne
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Blog
     */
    public String bloguSil(Blog b) throws Exception {

        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        vi.bloguSil(b);
        vi.baglantiyiKes();
        kullanicininBloglariniListele();
        return "kullanici?faces-redirect=true";

    }

    /**
     * Kullanıcın web arayüzü aracılığıyla seçtiği girdinin
     * detaylı bilgisini getirir.
     *
     * @param girdi  Yonlendirilecek girdinin bilgisinin olduğu nesne
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Girdi
     * @see GirdiDetay
     */
    public String girdiyeGit(Girdi girdi) {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        try {
            vi.baglan();
            this.seciliGiridi = vi.seciliGirdininDetayiniGetir(girdi.getGirdiID());
            vi.baglantiyiKes();
            return "blog";
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Kullanıcı web arayüzü araçılığıyla tüm yorumları listelemek
     * isterse bu metot çağırılır.
     *
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Yorum
     * @see YorumDetay
     */
    public String tumYorumlariListele() {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        try {
            vi.baglan();
            this.seciliGiridi.setYorumlar(
                    vi.birGirdiyeAitTumYorumDetaylariniGetir(
                    this.seciliGiridi.getGirdiID()));
            vi.baglantiyiKes();
            return "blog";
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Kullanıcı web arayüzü araçılığıyla seçili boğa ait tüm
     * girdileri listelemek istediğinde bu metot çalışır.
     *
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Girdi
     */
    public String tumGirdileriGetir() {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        try {
            vi.baglan();
            this.girdiListesi = vi.tumGirdileriGetir(this.seciliBlog.getBlogID());
            vi.baglantiyiKes();
            return "tumgirdiler";
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Kullanıcı web arayüzü araçılığıyla
     * herhangi bir bloğa ait tüm
     * girdileri listelemek istediğinde bu metot çalışır.
     *
     *
     * @param blogId Girdilerinin listeleneceği bloğun idsi
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Girdi
     */
    public String tumGirdileriGetir(int blogId) {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        try {
            vi.baglan();
            this.seciliBlog = vi.blogDetayGetir(blogId);
            this.seciliGiridi = vi.enGuncelGirdiyiGetir(blogId);
            this.enGuncelBesGirdi = vi.enGuncelBesGirdiyiGetir(blogId);
            this.girdiListesi = vi.tumGirdileriGetir(blogId);
            vi.baglantiyiKes();
            return "tumgirdiler";
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Kullanıcı tarafından atanmış olan üye değişkenleri kullanarak
     * yeni bir blog nesnesi oluşturur ve bu nesne içindeki bilgileri
     * veritabanına insert eder.
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Blog
     * @see Araclar
     */
    public String blogEkle() throws Exception {
        Blog b = new Blog();
        b.setAciklama(this.blogAciklama);
        b.setBlogBaslik(this.blogBaslik);
        b.setKullaniciID(this.getKullaniciBilgisi().getKullaniciID());
        b.setOlusturmaTarih(Araclar.yeniTimeStampOlustur());
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        vi.blogOlustur(b);
        vi.baglantiyiKes();
        return blogaGit(b);
    }

     /**
     * Kullanıcı tarafından atanmış olan üye değişkenleri kullanarak
     * yeni bir girdi nesnesi oluşturur ve bu nesne içindeki bilgileri
     * veritabanına insert eder.
     *
     * @return Girdi yapılan bloğu güncelledikten sonra
     * ilgili blog sayfasına yeniden yönlendirme yapar.
     *
     * @see VeritabaniIslemleri
     * @see Girdi
     * @see Araclar
     */
    public String girdiEkle() throws Exception {
        Girdi g = new Girdi();
        g.setGirdiBaslik(this.girdiBaslik);
        g.setGirdiIcerik(this.girdiIcerik);
        g.setKullaniciID(this.getKullaniciBilgisi().getKullaniciID());
        g.setBlogID(this.seciliBlog.getBlogID());
        g.setGirdiTarih(Araclar.yeniTimeStampOlustur());
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        vi.girdiOlustur(g);
        vi.baglantiyiKes();
        return blogaGit(seciliBlog);
    }

    /**
     * Kullanıcı tarafından atanmış olan üye değişkenleri kullanarak
     * yeni bir girdi nesnesi oluşturur ve bu nesne içindeki bilgileri
     * veritabanına insert eder.
     *
     * @return Yorum yapılan girdiyi güncelledikten sonra
     * blog sayfasında ilgili girdiyi gösterilecek şekilde
     * yeniden yönlendirme yapar.
     *
     * @see VeritabaniIslemleri
     * @see Girdi
     * @see Araclar
     */
    public String yorumEkle() throws Exception {
        Yorum y = new Yorum();
        y.setKullaniciID(this.kullaniciBilgisi.getKullaniciID());
        y.setYorumBaslik(yorumBaslik);
        y.setYorumIcerik(yorumIcerik);
        y.setGirdiID(this.seciliGiridi.getGirdiID());
        y.setYorumTarih(Araclar.yeniTimeStampOlustur());
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        vi.yorumOlustur(y);
        vi.baglantiyiKes();
        return girdiyeGit(this.seciliGiridi);
    }

    /**
     * Yeni girilecek yorum için yorumduzenle sayfasında
     * gösterilen yorumBaslik ve yorumIcerik değişkenlerini
     * temizle ve yorumduzenle sayfasına bir yonlendirme yapar
     * bilgileri veritabanından getirir ve yorumduzenle sayfasına
     * bir yönlendirme yapar.
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see YorumDetay
     */
    public String yorumDuzenleSayfasi() throws Exception {
        this.yorumBaslik = "";
        this.yorumIcerik = "";
        return "yorumduzenle";
    }

    /**
     * Parametre olarak verilen yorumu düzenlemek için gerekli
     * bilgileri veritabanından getirir ve yorumduzenle sayfasına
     * bir yönlendirme yapar.
     *
     * @param yorumDetay düzenlenecek yorum bilgisinin tutulduğu nesne
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see YorumDetay
     */
    public String yorumDuzenleSayfasi(YorumDetay yorumDetay) throws Exception {

        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        this.seciliYorum = vi.yorumBilgisiniGetir(yorumDetay.getYorumID());
        vi.baglantiyiKes();
        this.yorumBaslik = this.seciliYorum.getYorumBaslik();
        this.yorumIcerik = this.seciliYorum.getYorumIcerik();
        return "yorumduzenle";
    }

    /**
     * Kullanıcının girdiği bilgileri kullanarak yeni bir Yorum
     * nesnesi oluşturur ve bu nesne aracılığıyla veritabanındaki
     * yorum kaydını update eder.
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see YorumDetay
     * @see Yorum
     * @see GirdiDetay
     */
    public String yorumGuncelle() throws Exception {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        Yorum yorum = (Yorum) this.seciliYorum;
        yorum.setYorumBaslik(yorumBaslik);
        yorum.setYorumTarih(Araclar.yeniTimeStampOlustur());
        yorum.setYorumIcerik(yorumIcerik);
        vi.baglan();
        vi.yorumBilgisiGuncelle(yorum);
        vi.baglantiyiKes();
        return girdiyeGit(seciliGiridi);
    }

    /**
     * Kullanıcının seçtiği yorumu veritabanından siler.
     *
     * @param yorumDetay Silinecek yorumun bilgisi
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see YorumDetay
     */
    public String yorumSil(YorumDetay yorumDetay) throws Exception {

        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        vi.yorumuSil(yorumDetay);
        vi.baglantiyiKes();
        return girdiyeGit(seciliGiridi);

    }

    /**
     * Kullanıcının seçtiği girdiyi veritabanından siler.
     * En güncel girdileri ve girdi listesini tekrardan günceller.
     *
     * @param girdi Silinecek girdinin bilgisi
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Girdi
     * @see GirdiDetay
     */
    public String girdiSil(Girdi girdi) throws Exception {

        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        vi.girdiyiSil(girdi);
        this.enGuncelBesGirdi = vi.enGuncelBesGirdiyiGetir(girdi.getBlogID());
        this.girdiListesi = vi.tumGirdileriGetir(girdi.getBlogID());
        if (this.seciliGiridi.getGirdiID() == girdi.getGirdiID()
                && this.enGuncelBesGirdi != null) {
            this.seciliGiridi = vi.enGuncelGirdiyiGetir(girdi.getBlogID());
        }
        vi.baglantiyiKes();
        return null;
    }

     /**
     * Bu metot parametre almaz ve direk olarak seçili
     * girdiyi siler. İşlem sonrasında
     * en güncel girdileri ve girdi listesini tekrardan günceller.
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Girdi
     * @see GirdiDetay
     */
    public String girdiSil() throws Exception {

        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        vi.girdiyiSil(this.seciliGiridi);
        this.enGuncelBesGirdi = vi.enGuncelBesGirdiyiGetir(this.seciliBlog.getBlogID());
        this.girdiListesi = vi.tumGirdileriGetir(this.seciliBlog.getBlogID());
        if (this.enGuncelBesGirdi != null) {
            this.seciliGiridi = vi.enGuncelGirdiyiGetir(this.seciliBlog.getBlogID());
        }
        vi.baglantiyiKes();
        return "blog";
    }

     /**
     * Bu metot parametre almaz ve direkt olarak
     * seçlili girdinin bilgisini günceller.
     *
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Girdi
     * @see GirdiDetay
     */
    public String girdiDuzenle() throws Exception {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        this.seciliGiridi.setGirdiBaslik(girdiBaslik);
        this.seciliGiridi.setGirdiIcerik(girdiIcerik);
        this.seciliGiridi.setGirdiTarih(Araclar.yeniTimeStampOlustur());
        vi.girdiBilgisiGuncelle(this.seciliGiridi);
        this.enGuncelBesGirdi = vi.enGuncelBesGirdiyiGetir(this.seciliBlog.getBlogID());
        this.girdiListesi = vi.tumGirdileriGetir(this.seciliBlog.getBlogID());
        if (this.enGuncelBesGirdi != null) {
            this.seciliGiridi = vi.enGuncelGirdiyiGetir(this.seciliBlog.getBlogID());
        }
        vi.baglantiyiKes();
        return "blog";
    }

    /**
     * Bu metot parametre olarak aldığı girdi için
     * gerekli hazırlığı yapar ve girdiduzenle sayfasına
     * yönlendirmeyi gerçekleştirir.
     *
     * @param girdi Düzenlenecek girdinin bilgisi
     * @return Yönlendirme yapılacak sayfanın adı.
     * @see VeritabaniIslemleri
     * @see Girdi
     * @see GirdiDetay
     */
    public String girdiDuzenleSayfasi(Girdi girdi) throws Exception {
        VeritabaniIslemleri vi = new VeritabaniIslemleri();
        vi.baglan();
        this.seciliGiridi = vi.seciliGirdininDetayiniGetir(girdi.getGirdiID());
        vi.baglantiyiKes();
        this.girdiBaslik = this.seciliGiridi.getGirdiBaslik();
        this.girdiIcerik = this.seciliGiridi.getGirdiIcerik();
        return "girdiduzenle";
    }

    /**
     * Bu metot kullaniciBilgisi uye değişkeninin
     * aldığı değeri inceleyerek kullanıcının yetkisini belirler.
     * Genelde blog sayfasında kullanıcının izninin olmadığı
     * işlemleri engellemek için kullanılır.
     *
     * @return Eğer true ise blog sahibi ya da admin
     * Eğer false ise okur
     * @see Kullanici
     * @see BlogDetay
     */
    public boolean isKullaniciBlogSahibiVeyaAdminMi() {
        if (this.kullaniciBilgisi.getKullaniciIzin().charAt(0) == 'A') {
            // Admin
            return true;
        }
        if ((this.kullaniciBilgisi.getKullaniciIzin().charAt(0) == 'Y')
                && (this.seciliBlog.getKullanici().getKullaniciID()
                == this.kullaniciBilgisi.getKullaniciID())) {
            // Blog Sahibi
            return true;
        }
        // Blog sahibi ya da admin değil.
        return false;
    }

     /**
     * Bu metot yeni bir girdi girişi için ilgii alanları temizler
     * ve girdiduzenle sayfasına yönlendirme yapar
     *
     * @return Yönlendirilecek sayfanın adı.
     */
    public String yeniGirdi() {
        this.girdiBaslik = "";
        this.girdiIcerik = "";
        return "girdiduzenle";
    }

    /**
     * Bu metot yeni bir blog girişi için ilgii alanları temizler
     * ve blogduzenle sayfasına yönlendirme yapar
     *
     * @return Yönlendirilecek sayfanın adı.
     */
    public String yeniBlog() {
        this.blogBaslik = "";
        this.blogAciklama = "";
        return "blogduzenle";
    }

     /**
     * Bu metot kullanıcın blog sayısını döndürür.
     *
     * @return Kullanıcının blog sayısı
     */
    public int getKullanicininBlogSayisi() {
        if (blogListesi != null) {
            return blogListesi.size();
        } else {
            return 0;
        }
    }

    /*
     * Bundan sonraki tüm metotlar private tanımlanmış
     * üye değişkenler için getter ve setter metotlardır.
     */

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public Kullanici getKullaniciBilgisi() {
        return kullaniciBilgisi;
    }

    public void setKullaniciBilgisi(Kullanici kullaniciBilgisi) {
        this.kullaniciBilgisi = kullaniciBilgisi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public List<Blog> getBlogListesi() {
        return blogListesi;
    }

    public void setBlogListesi(List<Blog> blogListesi) {
        this.blogListesi = blogListesi;
    }

    public BlogDetay getSeciliBlog() {
        return seciliBlog;
    }

    public void setSeciliBlog(BlogDetay seciliBlog) {
        this.seciliBlog = seciliBlog;
    }

    public List<Girdi> getEnGuncelBesGirdi() {
        return enGuncelBesGirdi;
    }

    public void setEnGuncelBesGirdi(List<Girdi> enGuncelBesGirdi) {
        this.enGuncelBesGirdi = enGuncelBesGirdi;
    }

    public List<Girdi> getGirdiListesi() {
        return girdiListesi;
    }

    public void setGirdiListesi(List<Girdi> girdiListesi) {
        this.girdiListesi = girdiListesi;
    }

    public GirdiDetay getSeciliGiridi() {
        return seciliGiridi;
    }

    public void setSeciliGiridi(GirdiDetay seciliGiridi) {
        this.seciliGiridi = seciliGiridi;
    }

    public String getGirdiBaslik() {
        return girdiBaslik;
    }

    public void setGirdiBaslik(String girdiBaslik) {
        this.girdiBaslik = girdiBaslik;
    }

    public String getGirdiIcerik() {
        return girdiIcerik;
    }

    public void setGirdiIcerik(String girdiIcerik) {
        this.girdiIcerik = girdiIcerik;
    }

    public String getYorumBaslik() {
        return yorumBaslik;
    }

    public void setYorumBaslik(String yorumBaslik) {
        this.yorumBaslik = yorumBaslik;
    }

    public String getYorumIcerik() {
        return yorumIcerik;
    }

    public void setYorumIcerik(String yorumIcerik) {
        this.yorumIcerik = yorumIcerik;
    }

    public String getBlogAciklama() {
        return blogAciklama;
    }

    public void setBlogAciklama(String blogAciklama) {
        this.blogAciklama = blogAciklama;
    }

    public String getBlogBaslik() {
        return blogBaslik;
    }

    public void setBlogBaslik(String blogBaslik) {
        this.blogBaslik = blogBaslik;
    }
}
