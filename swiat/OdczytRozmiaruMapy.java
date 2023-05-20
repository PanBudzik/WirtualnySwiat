package swiat;

import javax.swing.*;

public class OdczytRozmiaruMapy {
    private JFrame frame;
    private JTextField xTextField;
    private JTextField yTextField;
    private int rozmiarX;
    private int rozmiarY;
    private boolean zebranoDane = false;

    public void wczytajRozmiar() {
        // Create a dialog box
        frame = new JFrame("Rozmiar mapy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel xLabel = new JLabel("Szerokosc:");
        xTextField = new JTextField(10);

        JLabel yLabel = new JLabel("Wysokosc:");
        yTextField = new JTextField(10);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            if (sprawdzPoprawnoscInputa()) {
                rozmiarX = Integer.parseInt(xTextField.getText());
                rozmiarY = Integer.parseInt(yTextField.getText());
                frame.dispose();
                zebranoDane=true;
            }
        });

        JPanel panel = new JPanel();
        panel.add(xLabel);
        panel.add(xTextField);
        panel.add(yLabel);
        panel.add(yTextField);
        panel.add(okButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private boolean sprawdzPoprawnoscInputa() {
        try {
            int rozmiarX = Integer.parseInt(xTextField.getText());
            int rozmiarY = Integer.parseInt(yTextField.getText());

            if (rozmiarX <= 0 || rozmiarY <= 0) {
                JOptionPane.showMessageDialog(frame, "Wprowadz wartosc wieksza niz 0", "Bledny input", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Wpisz poprawne numery", "Bledny input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

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

    public boolean isZebranoDane() {
        return zebranoDane;
    }

    public void setZebranoDane(boolean zebranoDane) {
        this.zebranoDane = zebranoDane;
    }
}
