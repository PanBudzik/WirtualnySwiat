package swiat;

import swiat.organizmy.FabrykaOrganizmow;
import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.zwierzeta.Czlowiek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Swiat {

    private JFrame frame = null;
    private JPanel panelGlowny;
    private JPanel gridPanel;
    JTextArea poleNarratora = new JTextArea();
    private int rozmiarX;
    private int rozmiarY;
    private char[][] plansza;
    private double wypelnieniePlanszy = 0.3;
    private int czasMiedzySprawdzeniemCzyZaczacNowaTure = 100;
    private int tura;
    private ArrayList<Organizm> organizmy = new ArrayList<Organizm>();
    private boolean czyCzlowiekZyje;
    private boolean nastepnaTuraKliknieta;
    private boolean czyJestKoniecGry;
    private Narrator narrator = new Narrator();

    private FabrykaOrganizmow fabrykaOrganizmow = new FabrykaOrganizmow();

    private TypyOrganizmow typyOrganizmow;
    private boolean wyjdz=false;

    public enum TypyOrganizmow {
        OWCA(0),
        WILK(1),
        LIS(2),
        ZOLW(3),
        ANTYLOPA(4),
        TRAWA(5),
        MLECZ(6),
        GUARANA(7),
        WILCZE_JAGODY(8),
        BARSZCZ_SOSNOWSKIEGO(9),
        CZLOWIEK(10);

        private int ID;

        TypyOrganizmow(int ID) {
            this.ID = ID;
        }

        public int getID() {
            return ID;
        }
        public static TypyOrganizmow fromID(int ID) {
            for (TypyOrganizmow typ : TypyOrganizmow.values()) {
                if (typ.getID() == ID) {
                    return typ;
                }
            }
            throw new IllegalArgumentException("Nie ma organizmu z id: " + ID);
        }
    }

    private ZapisIOdczytDanych zapisIOdczytDanych = new ZapisIOdczytDanych();

    public Swiat() {
        this(5, 5);
    }

    public Swiat(int rozmiarX, int rozmiarY) {
        if(rozmiarX<=0||rozmiarY<=0)
        {
            System.exit(0);
        }
        this.rozmiarX = rozmiarX;
        this.rozmiarY = rozmiarY;
        plansza = new char[rozmiarY][rozmiarX];
        for (int i = 0; i < rozmiarY; i++) {
            for (int j = 0; j < rozmiarX; j++) {
                plansza[i][j] = ' ';
            }
        }
    }

    public void aktualizujSwiat(){
        for (int i = 0; i < rozmiarY; i++) {
            for (int j = 0; j < rozmiarX; j++) {
                plansza[i][j] = ' ';
            }
        }

        for (int i = 0; i < organizmy.size(); i++) {
            Organizm organizm = organizmy.get(i);
            int x = organizm.getPozycja().getX();
            int y = organizm.getPozycja().getY();
            plansza[y][x] = organizm.getSymbol();
            organizm.setCzyRozmozylSieWTejTurze(false);
        }
    }
    public void odswiezPlansze() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(rozmiarY, rozmiarX));
        int cellSize = 50;

        for (int i = 0; i < rozmiarY; i++) {
            for (int j = 0; j < rozmiarX; j++) {
                JButton komorka = new JButton(String.valueOf(plansza[i][j]));
                komorka.setFont(new Font("Arial", Font.PLAIN, 15));
                komorka.setMinimumSize(new Dimension(cellSize,cellSize));
                if(zwrocOrganizmNaPozycja(new Pozycja(j,i)) instanceof Czlowiek)
                {
                    komorka.setBackground(Color.RED);
                }
                else {
                    if (zwrocOrganizmNaPozycja(new Pozycja(j, i)) != null) {
                        if (zwrocOrganizmNaPozycja(new Pozycja(j, i)).isCzyJestZwierzeciem()) {
                            komorka.setBackground(Color.ORANGE);
                        } else {
                            komorka.setBackground(Color.GREEN);
                        }
                    } else {
                        komorka.setBackground(Color.BLUE);
                    }
                }
                JPanel panelKomorek = new JPanel();
                panelKomorek.setPreferredSize(new Dimension(cellSize, cellSize));
                panelKomorek.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(komorka);

                if (plansza[i][j]==' ') {
                    final int x = j;
                    final int y = i;
                    komorka.setEnabled(true);
                    komorka.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            TypyOrganizmow[] wszystkieOrganizmy = TypyOrganizmow.values();
                            int length = wszystkieOrganizmy.length;
                            TypyOrganizmow[] newArray = Arrays.copyOf(wszystkieOrganizmy, length - 1);
                            System.arraycopy(wszystkieOrganizmy, 0, newArray, 0, length - 1);
                            wszystkieOrganizmy = newArray;

                            TypyOrganizmow wybranyOrganizm = (TypyOrganizmow) JOptionPane.showInputDialog(
                                    frame, "Jaki organizm dodac:", "Wybor organizmu",
                                    JOptionPane.QUESTION_MESSAGE, null, wszystkieOrganizmy, wszystkieOrganizmy[0]);

                            if (wybranyOrganizm != null) {
                                fabrykaOrganizmow.stworzOrganizm(wybranyOrganizm, new Pozycja(x,y), Swiat.this);
                            }

                            Czlowiek czlowiek = (Czlowiek)zworcCzlowieka(organizmy);
                            czlowiek.ustawFocusKlawiatury();

                            narrator.DopiszFragmentHistorii("Na mape dodano nowy organizm: "+zwrocOrganizmNaPozycja(new Pozycja(x,y)).toString());
                            setZawartoscPoleNarratora(narrator.getHistoria());

                            aktualizujSwiat();
                            odswiezPlansze();
                        }
                    });
                } else {
                    komorka.setEnabled(false);
                }
            }
        }

        int gridPanelWidth = cellSize * rozmiarX;
        int gridPanelHeight = cellSize * rozmiarY;
        gridPanel.setPreferredSize(new Dimension(gridPanelWidth, gridPanelHeight));

        frame.revalidate();
        frame.repaint();
    }
    public void rysujSwiat() {
        frame = new JFrame("Wirtualny Swiat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelGlowny = new JPanel(new GridBagLayout());
        gridPanel = new JPanel(new GridLayout(rozmiarY, rozmiarX));
        JScrollPane scrollGridPane = new JScrollPane(gridPanel);
        scrollGridPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollGridPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        String message = "<html>" +
                "<p>Sterowanie: </p>" +
                "<p>Czlowiek oznaczony litera 'H' i kolorem czerwonym" +
                "<p>Strzalki - Ruch czlowieka</p>" +
                "<p>U - Umiejetnosc specjalna" +
                "</html>";
        JOptionPane.showMessageDialog(frame, message, "Wirtualny Swiat", JOptionPane.INFORMATION_MESSAGE);

        JPanel panelGuziki = new JPanel();
        JButton przyciskZapisz = new JButton("Zapisz");
        JButton przyciskWczytaj = new JButton("Wczytaj");
        JButton przyciskNastepnaTura = new JButton("Nastepna Tura");
        przyciskZapisz.setBackground(Color.WHITE);
        przyciskWczytaj.setBackground(Color.WHITE);
        przyciskNastepnaTura.setBackground(Color.CYAN);
        panelGuziki.add(przyciskZapisz);
        panelGuziki.add(przyciskWczytaj);
        panelGuziki.add(przyciskNastepnaTura);

        JPanel panelNarrator = new JPanel(new BorderLayout());
        panelNarrator.setBorder(BorderFactory.createTitledBorder("Narrator"));
        poleNarratora = new JTextArea();
        poleNarratora.setText("Przygotowanie do rozgrywki...");
        poleNarratora.setWrapStyleWord(true);
        poleNarratora.setLineWrap(true);
        poleNarratora.setEditable(false);
        poleNarratora.setRows(5);
        JScrollPane scrollPane1 = new JScrollPane(poleNarratora);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelNarrator.add(scrollPane1, BorderLayout.CENTER);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BorderLayout());
        layeredPane.add(scrollGridPane, BorderLayout.CENTER);
        layeredPane.add(panelGuziki, BorderLayout.PAGE_START);
        layeredPane.add(panelNarrator, BorderLayout.PAGE_END);

        frame.getContentPane().add(layeredPane);

        frame.setPreferredSize(new Dimension(1200, 800));
        frame.pack();
        frame.setVisible(true);

        przyciskNastepnaTura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nastepnaTuraKliknieta = true;
                Czlowiek czlowiek = (Czlowiek)zworcCzlowieka(organizmy);
                czlowiek.ustawFocusKlawiatury();
            }
        });

        przyciskZapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    zapisIOdczytDanych.zapiszDaneDoPliku(filePath, frame, Swiat.this);
                    Czlowiek czlowiek = (Czlowiek)zworcCzlowieka(organizmy);
                    czlowiek.ustawFocusKlawiatury();
                }
            }
        });

        przyciskWczytaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    List<String> lines = zapisIOdczytDanych.odczytajDaneZPliku(filePath, frame, Swiat.this);
                    StringBuilder data = new StringBuilder();
                    for (String line : lines) {
                        data.append(line);
                        data.append("\n");
                    }
                    Czlowiek czlowiek = (Czlowiek)zworcCzlowieka(organizmy);
                    czlowiek.ustawFocusKlawiatury();
                }
            }
        });
    }

    public Pozycja zwrocLosowaPozycjeNaPlanszy(){
        Random losowaliczba = new Random();
        Pozycja losowaPozycja = new Pozycja(0,0);
        losowaPozycja.setX(losowaliczba.nextInt(rozmiarX));
        losowaPozycja.setY(losowaliczba.nextInt(rozmiarY));
        while(this.czyPoleZajetePrzezOrganizm(losowaPozycja)) {
            losowaPozycja.setX(losowaliczba.nextInt(rozmiarX));
            losowaPozycja.setY(losowaliczba.nextInt(rozmiarY));
        }
        return losowaPozycja;
    }
    public void generujSwiat(){
        narrator.ZacznijPisacHistorieOdNowa();
        fabrykaOrganizmow.stworzOrganizm(TypyOrganizmow.CZLOWIEK, this.zwrocLosowaPozycjeNaPlanszy(), this);
        czyCzlowiekZyje=true;
        int ileDodacZwierzat = (int)Math.floor(rozmiarX*rozmiarY*wypelnieniePlanszy);
        TypyOrganizmow[] wszystkieOrganizmy = TypyOrganizmow.values();
        Random losowaLiczba = new Random();
        for (int i = 0; i < ileDodacZwierzat; i++) {
            TypyOrganizmow losowyOrganizm = wszystkieOrganizmy[losowaLiczba.nextInt(wszystkieOrganizmy.length-1)];
            fabrykaOrganizmow.stworzOrganizm(losowyOrganizm, this.zwrocLosowaPozycjeNaPlanszy(), this);
        }
        aktualizujSwiat();
        odswiezPlansze();
    }

    public void wykonajTure() throws InterruptedException {

        if (czyJestKoniecGry) return;
        tura++;

        sortujOrganizmy();
        for (int i = 0; i < organizmy.size();i++) {
            Organizm organizm = organizmy.get(i);
            if (organizm!=null)
            {
                if (organizm.getCzyZyje())
                {
                    organizm.akcja();
                }
            }
        }
        usunMartweOrganizmy();
        aktualizujSwiat();
        odswiezPlansze();

        this.setZawartoscPoleNarratora(narrator.getHistoria());
        narrator.ZacznijPisacHistorieOdNowa();
        narrator.DopiszFragmentHistorii("Zaczyna sie tura: "+tura);

        if(!this.czyCzlowiekZyje){
            while(!nastepnaTuraKliknieta) {
                Thread.sleep(czasMiedzySprawdzeniemCzyZaczacNowaTure);
            }
        }

        nastepnaTuraKliknieta = false;
    }

    public void sortujOrganizmy()
    {
        Collections.sort(organizmy, new Comparator<Organizm>() {
            @Override
            public int compare(Organizm o1, Organizm o2) {
                if (o1.getInicjatywa() != o2.getInicjatywa()) {
                    return Integer.compare(o2.getInicjatywa(), o1.getInicjatywa());
                } else {
                    return Integer.compare(o1.getTuraUrodzenia(), o2.getTuraUrodzenia());
                }
            }
        });
    }
    public void dodajOrganizm(Organizm organizm){
        organizmy.add(organizm);
    }

    public void usunMartweOrganizmy(){
        for (int i = 0; i < organizmy.size(); i++) {
            Organizm organizm = organizmy.get(i);
            if (!(organizm.getCzyZyje()))
            {
                if(organizm instanceof Czlowiek)
                {
                    this.setCzyCzlowiekZyje(false);
                }
                narrator.DopiszFragmentHistorii("Smierc: " + organizm.toString());
                organizmy.remove(i);
                i--;
            }
        }
    }
    public boolean czyPoleZajetePrzezOrganizm(Pozycja sprawdzanaPozycja){
        for (Organizm organizm : organizmy) {
            if (organizm.getPozycja().equals(sprawdzanaPozycja) && organizm != null) {
                return true;
            }
        }
        return false;
    }

    public Pozycja zwrocDostepneMiejsceObok(Pozycja mojaPozycja){
        Pozycja nowaPozycja = new Pozycja(0,0);
        ArrayList<Pozycja> kierunki = new ArrayList<Pozycja>();
        kierunki.add(new Pozycja(0,-1));
        kierunki.add(new Pozycja(0,1));
        kierunki.add(new Pozycja(1,0));
        kierunki.add(new Pozycja(-1,0));

        Collections.shuffle(kierunki);

        for (int i = 0; i < 4; i++) {

            nowaPozycja.setX(mojaPozycja.getX()+kierunki.get(i).getX());
            nowaPozycja.setY(mojaPozycja.getY()+kierunki.get(i).getY());

            if (nowaPozycja.getX() >= 0 && nowaPozycja.getY() >= 0 && nowaPozycja.getX()< rozmiarX && nowaPozycja.getY() < rozmiarY) {
                    return nowaPozycja;
            }
        }
        return mojaPozycja;
    }

    public Pozycja zwrocWolneMiejsceObok(Pozycja mojaPozycja){
        Pozycja nowaPozycja = new Pozycja(0,0);
        ArrayList<Pozycja> kierunki = new ArrayList<Pozycja>();
        kierunki.add(new Pozycja(0,-1));
        kierunki.add(new Pozycja(0,1));
        kierunki.add(new Pozycja(1,0));
        kierunki.add(new Pozycja(-1,0));

        Collections.shuffle(kierunki);

        for (int i = 0; i < 4; i++) {

            nowaPozycja.setX(mojaPozycja.getX()+kierunki.get(i).getX());
            nowaPozycja.setY(mojaPozycja.getY()+kierunki.get(i).getY());

            if (nowaPozycja.getX() >= 0 && nowaPozycja.getY() >= 0 && nowaPozycja.getX()< rozmiarX && nowaPozycja.getY() < rozmiarY && !this.czyPoleZajetePrzezOrganizm(nowaPozycja)) {
                return nowaPozycja;
            }
        }
        return mojaPozycja;
    }

    public Organizm zwrocOrganizmNaPozycja(Pozycja danaPozycja){
        for (Organizm organizm : organizmy) {
            if (organizm != null && ((Organizm) organizm).getPozycja().equals(danaPozycja)) {
                return organizm;
            }
        }
        return null;
    }

    public Organizm zworcCzlowieka(ArrayList<Organizm> organizmy){
        for (int i = 0; i < organizmy.size(); i++) {
            Organizm organizm = organizmy.get(i);
            if (organizm instanceof Czlowiek) {
                return organizm;
            }
        }
        Organizm czlowiek = new Czlowiek(new Pozycja(0,0),this);
        czlowiek.setCzyZyje(false);
        return czlowiek;
    }

    public int getRozmiarX() {
        return rozmiarX;
    }

    public void setRozmiarX(int rozmiarX) {
        this.rozmiarX = rozmiarX;
    }

    public int getRozmiarY() {
        return rozmiarY;
    }

    public void setRozmiarY(int rozmiarY) {
        this.rozmiarY = rozmiarY;
    }

    public char[][] getPlansza() {
        return plansza;
    }

    public void setPlansza(char[][] plansza) {
        this.plansza = plansza;
    }

    public int getTura() {
        return tura;
    }

    public void setTura(int tura) {
        this.tura = tura;
    }

    public ArrayList<Organizm> getOrganizmy() {
        return organizmy;
    }

    public void setOrganizmy(ArrayList<Organizm> organizmy) {
        this.organizmy = organizmy;
    }

    public boolean isCzyCzlowiekZyje() {
        return czyCzlowiekZyje;
    }

    public void setCzyCzlowiekZyje(boolean czyCzlowiekZyje) {
        this.czyCzlowiekZyje = czyCzlowiekZyje;
    }

    public boolean getCzyJestKoniecGry() {
        return czyJestKoniecGry;
    }

    public void setCzyJestKoniecGry(boolean czyJestKoniecGry) {
        this.czyJestKoniecGry = czyJestKoniecGry;
    }

    public Narrator getNarrator() {
        return narrator;
    }

    public void setNarrator(Narrator narrator) {
        this.narrator = narrator;
    }

    public boolean isCzyJestKoniecGry() {
        return czyJestKoniecGry;
    }

    public FabrykaOrganizmow getFabrykaOrganizmow() {
        return fabrykaOrganizmow;
    }

    public void setFabrykaOrganizmow(FabrykaOrganizmow fabrykaOrganizmow) {
        this.fabrykaOrganizmow = fabrykaOrganizmow;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanelGlowny() {
        return panelGlowny;
    }

    public void setPanelGlowny(JPanel panelGlowny) {
        this.panelGlowny = panelGlowny;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public void setGridPanel(JPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public double getWypelnieniePlanszy() {
        return wypelnieniePlanszy;
    }

    public void setWypelnieniePlanszy(double wypelnieniePlanszy) {
        this.wypelnieniePlanszy = wypelnieniePlanszy;
    }

    public TypyOrganizmow getTypyOrganizmow() {
        return typyOrganizmow;
    }

    public void setTypyOrganizmow(TypyOrganizmow typyOrganizmow) {
        this.typyOrganizmow = typyOrganizmow;
    }

    public boolean isNastepnaTuraKliknieta() {
        return nastepnaTuraKliknieta;
    }

    public void setNastepnaTuraKliknieta(boolean nastepnaTuraKliknieta) {
        this.nastepnaTuraKliknieta = nastepnaTuraKliknieta;
    }

    public JTextArea getPoleNarratora() {
        return poleNarratora;
    }

    public void setZawartoscPoleNarratora(String tekst) {
        this.poleNarratora.setText(tekst);
    }
}
