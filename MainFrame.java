import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame implements Runnable {
    private JButton oblicz;
    private JPanel contentPane;
    private AbstractAction obliczAction;
    private DefaultComboBoxModel<Algorytm> model;
    private JComboBox<Algorytm> dzialanie;
    private MainFrame parentFrame;
    private Algorytm[] metoda= {
            new Algorytm() {

                @Override
                public String toString() {
                    return " + ";
                }
                @Override
                Double przelicz(Double val, Double val2) {
                    return val+val2;
                }
            },
            new Algorytm() {
                @Override
                public String toString() {
                    return " - ";
                }
                @Override
                Double przelicz(Double val, Double val2) {
                    return val-val2;
                }
            },
            new Algorytm() {
                @Override
                public String toString() {
                    return " * ";
                }
                @Override
                Double przelicz(Double val, Double val2) {
                    return val*val2;
                }
            },
            new Algorytm() {
                @Override
                public String toString() {
                    return " / ";
                }
                @Override
                Double przelicz(Double val, Double val2) {
                    return val/val2;
                }
            }
    };
    private JTextField dane, dane2;
    private JLabel wynik;
    public MainFrame(String title) {
        super(title);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension dim=tk.getScreenSize();
        setSize(dim.width/2,dim.height/2);
        contentPane=new JPanel();
        setContentPane(contentPane);
        parentFrame=this;
        obliczAction=new ObliczAction();
        oblicz=new JButton(obliczAction);
        model=new DefaultComboBoxModel<>(metoda);
        dzialanie=new JComboBox<>(model);
        dane=new JTextField(6);
        dane2=new JTextField(6);
        wynik=new JLabel();
        add(dane);
        add(dzialanie);
        add(dane2);
        add(oblicz);
        add(wynik);
        FrameCloseAdapter adapter=new FrameCloseAdapter();
        addWindowListener(adapter);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new MainFrame("Kalkulator"));
    }

    @Override
    public void run() {
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    class ObliczAction extends AbstractAction{
        public ObliczAction() {
            putValue(NAME, "Oblicz");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String txt=dane.getText();
            String txt2=dane2.getText();
            if(txt.length()>0) {
                    Double val=Double.parseDouble(txt);
                    Double val2=Double.parseDouble(txt2);
                    Double result=((Algorytm)dzialanie.getSelectedItem()).przelicz(val,val2);
                    wynik.setText(result.toString());
            }
        }

    }

    abstract class Algorytm{
        abstract Double przelicz(Double val, Double val2);
    }

    class FrameCloseAdapter extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(parentFrame, "Czy na pewno chcesz zamknąć kalkulator?"))
                System.exit(0);
        }
    }
}
