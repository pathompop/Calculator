import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class calculator {

    //JFrame part
    JFrame frame = new JFrame("Calculator");

    //JPanel part
    JPanel resultpanel = new JPanel();
    JPanel showpanel = new JPanel();
    JPanel parentPanel = new JPanel();
    JPanel buttonpanel = new JPanel();

    //jLabel part
    JLabel showlabel = new JLabel("0");
    JLabel resultlabel = new JLabel();

    //JButton part
    JButton[][] button = new JButton[6][4];
    String[][] buttonlabels = {
        {"%", "CE", "C", "del"},
        {"1/x", "x^2", "√", "÷"},
        {"7", "8", "9", "x"},
        {"4", "5", "6", "-"},
        {"1", "2", "3", "+"},
        {"+/-", "0", ".", "="}
    };

    public calculator() {
        //create JFrame
    frame.setVisible(true);
    frame.setSize(420, 620);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //create resultJLabel
    resultlabel.setBackground(Color.black);
    resultlabel.setForeground(Color.white);
    resultlabel.setFont(new Font("Arial", Font.BOLD, 25));
    resultlabel.setHorizontalAlignment(JLabel.RIGHT);
    resultlabel.setOpaque(true);
    // resultlabel.setText("123");

    //create resultJPanel
    resultpanel.setLayout(new BorderLayout());
    resultpanel.add(resultlabel);
    frame.add(resultpanel, BorderLayout.NORTH);

    // Show Label
    showlabel.setBackground(Color.black);
    showlabel.setForeground(Color.WHITE);
    showlabel.setFont(new Font("Arial", Font.BOLD, 60));
    showlabel.setHorizontalAlignment(JLabel.RIGHT);
    showlabel.setOpaque(true);

    showpanel.setLayout(new BorderLayout());
    showpanel.add(showlabel);

    // Parent Panel to Hold Both
    parentPanel.setLayout(new GridLayout(2, 1)); // Two rows, one column
    parentPanel.add(resultpanel);
    parentPanel.add(showpanel);

    frame.add(parentPanel, BorderLayout.NORTH);

    //button label
    buttonpanel.setLayout(new GridLayout(6,4));
    buttonpanel.setBackground(Color.DARK_GRAY);
    frame.add(buttonpanel, BorderLayout.CENTER);

    //create button
    for (int r = 0; r < 6; r++) {
        for (int c = 0; c < 4; c++) {
            JButton tile = new JButton(buttonlabels[r][c]);
            button[r][c] = tile;
            buttonpanel.add(tile);

            if (r == 5 && c == 3) {
                tile.setBackground(Color.orange);
                tile.setForeground(Color.black);
                tile.setFont(new Font("Arial", Font.BOLD, 20));
                tile.setFocusable(false);
                return;
            }

            tile.setBackground(Color.DARK_GRAY);
            tile.setForeground(Color.white);
            tile.setFont(new Font("Arial", Font.BOLD, 20));
            tile.setFocusable(false);
        }
    }
    }
    
}
