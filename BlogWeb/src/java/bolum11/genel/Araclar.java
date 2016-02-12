package bolum11.genel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Araclar {
    public static Timestamp yeniTimeStampOlustur(){
         return new Timestamp((new java.util.Date()).getTime());
    }
    public static String tarihZamanGoster(Timestamp ts){
        Date date = new Date(ts.getTime());
        String gosterim = "dd/MM/yyyy - [hh:mm]";
        SimpleDateFormat sdf = new SimpleDateFormat(gosterim);
        return sdf.format(date);
    }
    public static String sadeceTarihGoster(Timestamp ts){
        Date date = new Date(ts.getTime());
        String gosterim = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(gosterim);
        return sdf.format(date);
    }
    public static String sadeceZamanGoster(Timestamp ts){
        Date date = new Date(ts.getTime());
        String gosterim = "hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(gosterim);
        return sdf.format(date);
    }
    public static TableModel tabloIcinModelOlustur(Object[] baslik, Object[][] veri) {
        TableModel result = new DefaultTableModel(veri, baslik) {
            // Tablodaki hücreler değiştirilmesin.
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return result;

    }
    public static void mesajGoster(String mesaj, String mesajTipi){
          if(mesajTipi.equals("HATA"))
          {
              JOptionPane.showMessageDialog(new JFrame(), mesaj,
                      "HATA", JOptionPane.ERROR_MESSAGE);
          }
          else if(mesajTipi.equals("BİLGİ"))
          {
              JOptionPane.showMessageDialog(new JFrame(), mesaj,
                      "BİLGİ", JOptionPane.INFORMATION_MESSAGE);
          }
          else if(mesajTipi.equals("UYARI"))
          {
              JOptionPane.showMessageDialog(new JFrame(),
                      mesaj, "UYARI", JOptionPane.WARNING_MESSAGE);
          }
    }
    public static int soruSor(String soruIcerik){
        Object[] secenekler = {"EVET","HAYIR"};
        int n = JOptionPane.showOptionDialog(new JFrame(),
            soruIcerik,
            "?",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            secenekler,
            secenekler[1]);
        return n;
    }
}
