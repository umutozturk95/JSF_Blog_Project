package bolum11.genel;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class YetkisizGirisPhaseListener implements PhaseListener {

    @Override
    public PhaseId getPhaseId() {
        //Managed beanlerin herhangi birine erişim
        //sağlandığı Restore fazında devreye gir.
        return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    public void afterPhase(PhaseEvent event) {
        FacesContext fc = event.getFacesContext();

        // Giriş sayfasında mı diye kontrol et
        String temp =  fc.getViewRoot().getViewId();
        boolean loginSayfasindaMi = fc.getViewRoot().getViewId().contains("index.xhtml");

        // Eğer kullanıcı giriş sayfası dışındaki bir sayfayı çağırıyorsa
        // ve login olmamış ise logout kuralını çağır ve
        // kullanıcıyı giriş sayfasına geri gönder
        if (!loginSayfasindaMi && !girisYapmisMi(fc)) {
            NavigationHandler nh = fc.getApplication().getNavigationHandler();
            nh.handleNavigation(fc, null, "logout");
            //Kullanıcıya mesaj gönder
            fc.addMessage(null,
                    new FacesMessage("Yetkisiz erişim. Lütfen giriş Yaptınız"));
        }
    }
    private boolean girisYapmisMi(FacesContext fc){
        //FacesContest aracılığıyla #{KullaniciBean.kullaniciBilgisi}
        //değişenine ulaş
        ValueBinding binding = fc.getApplication()
                .createValueBinding("#{KullaniciBean.kullaniciBilgisi}");
        Object o = binding.getValue(fc);

        // Değer nuldan farklı ise true döndür
        // Değer null ise kullaniciBilgisi henüz oluşturulmamış
        // ve kullanıcı giriş yapmamış demektir. O zaman false döndür.
        if(o != null)
            return true;
        else
            return false;
    }

   
}
