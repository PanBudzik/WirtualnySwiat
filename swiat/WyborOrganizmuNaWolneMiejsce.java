package swiat;

import javax.swing.*;

enum TypyOrganizmow {
    ORGANISM_1,
    ORGANISM_2,
    ORGANISM_3
}

public class WyborOrganizmuNaWolneMiejsce {

    public void wybierzOrganizm(){
        JFrame frame = new JFrame("Wybierz organizm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Wybierz");
        button.addActionListener(e -> {
            TypyOrganizmow[] wszystkieOrganizmy = TypyOrganizmow.values();
            TypyOrganizmow selectedOrganizm = (TypyOrganizmow) JOptionPane.showInputDialog(
                    frame, "Wybierz organizm:", "Wybor organizmu",
                    JOptionPane.QUESTION_MESSAGE, null, wszystkieOrganizmy, wszystkieOrganizmy[0]);

            if (selectedOrganizm != null) {
                System.out.println("Wybierz organizm: " + selectedOrganizm);
            }
        });

        frame.getContentPane().add(button);

        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}