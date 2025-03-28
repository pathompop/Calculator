import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class calculator implements KeyListener, MouseListener{

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

    //number and operators part
    String number = "";
    String resultnumber = "";
    char operator = '\0';

    public calculator() {
        //create JFrame
        frame.setVisible(true);
        frame.setSize(420, 620);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.addKeyListener(this);

        //create resultJLabel
        resultlabel.setBackground(Color.black);
        resultlabel.setForeground(Color.white);
        resultlabel.setFont(new Font("Arial", Font.BOLD, 25));
        resultlabel.setHorizontalAlignment(JLabel.RIGHT);
        resultlabel.setOpaque(true);

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
                tile.addMouseListener(this);
                // tile.addKeyListener(this);

                if (r == 5 && c == 3) {
                    tile.setBackground(Color.orange);
                    tile.setForeground(Color.black);
                } else {
                    tile.setBackground(Color.DARK_GRAY);
                    tile.setForeground(Color.white);
                }

                tile.setFont(new Font("Arial", Font.BOLD, 20));
                tile.setFocusable(false);
            }
        }

    }

    //result method part
    String result(String number, String resultnumber, char operator) {
        BigDecimal num = new BigDecimal(number);
        BigDecimal renum = new BigDecimal(resultnumber);
        BigDecimal rs = BigDecimal.ZERO;

        switch (operator) {
            case '+':
                rs = renum.add(num);
                break;
            case '-':
                rs = renum.subtract(num);
                break;
            case 'x':
                rs = renum.multiply(num);
                break;
            case '÷':
                rs = renum.divide(num);
                break;
        }

        return String.valueOf(rs);
    }

    //operators method part
    char operators(char op) {
        switch (op) {
            case '+': return '+';
            case '-': return '-';
            case '*': return 'x';
            case '/': return '÷';
            default: return '+';
        }
    }

    //equals sysbol method part
    void equals() {
        resultlabel.setText(resultnumber + " " + operator + " " + number + " =");
        resultnumber = result(number, resultnumber, operator);
        showlabel.setText(resultnumber);
    }

    //clear method part
    void clear() {
        number = "";
        resultnumber = "";
        operator = '\0';
    }


    //KeyListener part
    //inputkey part
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        char keychar = e.getKeyChar();

        //numbers input part
        if (Character.isDigit(keychar)) {
            if (!(keychar == '0' && number.isEmpty())) {
                number += keychar;
                showlabel.setText(number);
            }
        }

        //delete part
        else if (key == KeyEvent.VK_BACK_SPACE) {
            if (!number.isEmpty()) {
                number = number.substring(0, number.length()-1);
                if (!number.isEmpty()) {
                    showlabel.setText(number);
                } else {
                    showlabel.setText("0");
                }
            }
        }

        //operators part
        else if ("+-*/".indexOf(keychar) != -1 && !(number.isEmpty() && resultnumber.isEmpty())) {
            if (number.isEmpty() && !resultnumber.isEmpty()) {
                operator = operators(keychar);
            } else if (resultnumber.isEmpty()) {
                resultnumber = number;
                operator = operators(keychar);
            } else if (!number.isEmpty()) {
                resultnumber = result(number, resultnumber, operator);
                operator = operators(keychar);
            }
            number = "";
            resultlabel.setText(resultnumber + operator);
            showlabel.setText(resultnumber);
        }

        //pont part
        else if (keychar == '.') {
            if (number.isEmpty()) {
                showlabel.setText("0");
                number += "0.";
            } else if (!number.contains(".")) {
                number += ".";
            }
            showlabel.setText(number);
        }

        //Enter part
        else if (key == KeyEvent.VK_ENTER && !(number.isEmpty() || resultnumber.isEmpty())) {
            equals();
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyReleased(KeyEvent e) {}


    //mouseListener part
    //clickinput
    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String buttonText = clickedButton.getText();
        
        //mouse number part
        if (Character.isDigit(buttonText.charAt(0))) {
            if (!(buttonText.equals("0") && number.isEmpty())) {
                number += buttonText;
                showlabel.setText(number);
            }
        }

        //Delete part
        else if (buttonText.equals("del")) {
            if (!number.isEmpty()) {
                number = number.substring(0, number.length()-1);
                if (!number.isEmpty()) {
                    showlabel.setText(number);
                } else {
                    showlabel.setText("0");
                }
            }
        }

        //operator part
        else if ("+-x÷".contains(buttonText) && !(number.isEmpty() && resultnumber.isEmpty())) {
            if (number.isEmpty() && !resultnumber.isEmpty()) {
                operator = buttonText.charAt(0);
            } else if (resultnumber.isEmpty()) {
                resultnumber = number;
                operator = buttonText.charAt(0);
            } else if (!number.isEmpty()) {
                resultnumber = result(number, resultnumber, operator);
                operator = buttonText.charAt(0);
            }
            number = "";
            resultlabel.setText(resultnumber + operator);
            showlabel.setText(resultnumber);
        }

        //full stop part
        else if (buttonText.equals(".")) {
            if (number.isEmpty()) {
                showlabel.setText("0");
                number += "0.";
            } else if (number.contains(".")) {
                number += ".";
            }
            showlabel.setText(number);
        }

        //equal part
        else if (buttonText.equals("=") && !(number.isEmpty() || resultnumber.isEmpty())) {
            equals();
        }

        //clear part
        else if (buttonText.equals("C")) {
            clear();
            showlabel.setText("0");
            resultlabel.setText("");
        }

        //clear entry part
        else if (buttonText.equals("CE")) {
            number = "";
            showlabel.setText("0");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
