import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class calculator implements KeyListener, ActionListener{

    //JFrame part
    JFrame frame = new JFrame("Calculator");

    //JPanel part
    JPanel toppanel = new JPanel(new GridLayout(2,1));
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
    String number = "0";
    String resultnumber = "";
    char operator = '\0';

    public calculator() {
        //create JFrame
        frame.setVisible(true);
        frame.setSize(420, 620);
        frame.setMinimumSize(new Dimension(420, 620));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.addKeyListener(this);

        //create resultJLabel
        resultlabel.setBackground(Color.black);
        resultlabel.setForeground(Color.gray);
        resultlabel.setFont(new Font("Arial", Font.PLAIN, 25));
        resultlabel.setHorizontalAlignment(JLabel.RIGHT);
        resultlabel.setOpaque(true);

        // Show Label
        showlabel.setBackground(Color.black);
        showlabel.setForeground(Color.WHITE);
        showlabel.setFont(new Font("Arial", Font.PLAIN, 60));
        showlabel.setHorizontalAlignment(JLabel.RIGHT);
        showlabel.setOpaque(true);

        toppanel.add(resultlabel);
        toppanel.add(showlabel);
        frame.add(toppanel, BorderLayout.NORTH);

        //button label
        buttonpanel.setLayout(new GridLayout(6,4));
        buttonpanel.setBackground(Color.DARK_GRAY);
        frame.add(buttonpanel, BorderLayout.CENTER);

        //create button
        for (byte r = 0; r < 6; r++) {
            for (byte c = 0; c < 4; c++) {
                JButton tile = new JButton(buttonlabels[r][c]);
                button[r][c] = tile;
                buttonpanel.add(tile);
                tile.addActionListener(this);
                // tile.addKeyListener(this);

                if (r == 5 && c == 3) {
                    tile.setBackground(Color.orange);
                    tile.setForeground(Color.black);
                } else if (r < 2 || c > 2) {
                    tile.setBackground(new Color(50, 50, 50));
                    tile.setForeground(Color.white);
                } else {
                    tile.setBackground(new Color(75,75,75));
                    tile.setForeground(Color.white);
                }

                tile.setFont(new Font("Arial", Font.PLAIN, 20));
                tile.setFocusable(false);
            }
        }

    }

//----------------------------------------------------------------------------Method--------------------------------------------------------------------

    //format number method part
    void format_number(String num) {
        try {
            DecimalFormat df = new DecimalFormat("#,###.###############");
            String format = df.format(Double.parseDouble(num));
            showlabel.setText(format);
        } catch (Exception e) {
            showlabel.setText("Cannot divide by zero");
            clear();
        }
    }
    
    //result method part
    String result(String number, String resultnumber, char operator) {
        try {
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
                try {
                    rs = renum.divide(num);
                } catch (Exception e) {
                    rs = renum.divide(num, 15, RoundingMode.HALF_UP);
                }
                    break;
            }
    
            return String.valueOf(rs);
        } catch (Exception e) {
            return "Error";
        }
    }

    //convert operators method part
    char convert_operators(char op) {
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
        point_trim();
        resultlabel.setText(resultnumber + " " + operator + " " + number + " =");
        resultnumber = result(number, resultnumber, operator);
        format_number(resultnumber);
        clear();
    }

    //clear method part
    void clear() {
        number = "0";
        resultnumber = "";
        operator = '\0';
    }

    //number input method part
    void numbers_input(String num) {
        if (!number.contains(".") && !number.equals("0") && number.length() < 16) {
            number += num;
            format_number(number);
        } else if (!number.contains(".") && number.length() < 16) {
            number = "";
            number += num;
            format_number(number);
        } else if (number.length() < 16) {
            number += num;
            String[] sn = number.split("\\.");
            format_number(number);
            showlabel.setText(showlabel.getText() + "." + sn[1]);
        }
    }

    //Delete input method part
    void delete_input() {
        if (!number.isEmpty()) {
            number = number.substring(0, number.length()-1);
            if (!number.isEmpty()) {
                format_number(number);
                if (Double.parseDouble(number) % 1 == 0 && number.contains(".")) {
                    showlabel.setText(showlabel.getText() + ".");
                }
            } else {
                number = "0";
            showlabel.setText(number);
            }
        }
    }

    //Operator input method part
    void Operator_input (char op) {
        point_trim();
        if (resultnumber.isEmpty()) {
            operator = op;
            resultnumber = number;
        } else if (number.equals("0")) {
            operator = op;
        } else {
            resultnumber = result(number, resultnumber, operator);
            operator = op;
        }
        number = "0";
        resultlabel.setText(resultnumber + " " + operator);
        format_number(resultnumber);
    }

    //full stop input part
    void point_input() {
        if (!number.contains(".")) {
            number += ".";
            format_number(number);
            if (Double.parseDouble(number) % 1 == 0) {
                showlabel.setText(showlabel.getText() + ".");
            }
        }
    }

    //plus or minus input method
    void Plus_or_Minus() {
        if (number.contains("-")) {
            number = number.substring(1);
        } else {
            char minus = '-';
            number = minus + number;
        }
        format_number(number);
    }

    //square root method
    void square_root(String num) {
        try {
            BigDecimal n = new BigDecimal(Math.sqrt(Double.parseDouble(num)));
            number = String.valueOf(n);
            format_number(number);
        } catch (Exception e) {
            showlabel.setText("Invalid input");
                clear();
        }
    }

    //power method
    void power(String num) {
        BigDecimal n = new BigDecimal(Math.pow(Double.parseDouble(num), 2));
        number = String.valueOf(n);
        format_number(number);
    }

    //check integer method
    boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //percents method part
    void percent() {
        if (resultnumber.isEmpty()) {
            resultnumber = "0";
            number = "0";
            resultlabel.setText(resultnumber);
        } else {
            number = result(number, resultnumber, 'x');
            System.out.println(number);
            number = result("100", number, '÷');
            System.out.println(number);
            resultlabel.setText(resultnumber + operator + number);
            format_number(number);
        }
    }

    //trim point method
    void point_trim() {
        if (Double.parseDouble(number) % 1 == 0 && number.contains(".") && !number.isEmpty()) {
            String[] sn = number.split("\\.");
            number = sn[0];
        }
    }

//----------------------------------------------------------------------------KeyListener--------------------------------------------------------------------

    //KeyListener part
    //inputkey part
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        char keychar = e.getKeyChar();

        //numbers input part
        if (Character.isDigit(keychar)) {
            numbers_input(Character.toString(keychar));
        }

        //delete part
        else if (key == KeyEvent.VK_BACK_SPACE) {
            delete_input();
        }

        //operators part
        else if ("+-*/".indexOf(keychar) != -1) {
            Operator_input(convert_operators(keychar));
        }

        //full stop part
        else if (keychar == '.') {
            point_input();
        }

        //Enter part
        else if (key == KeyEvent.VK_ENTER && !resultnumber.isEmpty()) {
            equals();
        }

        //clear part
        else if (key == KeyEvent.VK_ESCAPE) {
            clear();
            showlabel.setText(number);
            resultlabel.setText("");
        }

        //clear enter part
        else if (key == KeyEvent.VK_DELETE) {
            number = "0";
            showlabel.setText("0");
        }

        //plus or minus sysbol part
        else if (key == KeyEvent.VK_F9) {
            Plus_or_Minus();
        }

        //percent part
        else if (key == KeyEvent.VK_5 && e.isShiftDown()) {
            percent();
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyReleased(KeyEvent e) {}

//----------------------------------------------------------------------------ActionListener--------------------------------------------------------------------

    //mouseListener part
    //clickinput

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String buttonText = clickedButton.getText();
        
        //mouse number part
        if (isInteger(buttonText)) {
            numbers_input(buttonText);
        }
        
        //Delete part
        else if (buttonText.equals("del")) {
            delete_input();
        }
        
        //operator part
        else if ("+-x÷".contains(buttonText)) {
            Operator_input(buttonText.charAt(0));
        }
        
        //full stop part
        else if (buttonText.equals(".")) {
            point_input();
        }
        
        //equal part
        else if (buttonText.equals("=") && !resultnumber.isEmpty()) {
            equals();
        }
        
        //clear part
        else if (buttonText.equals("C")) {
            clear();
            showlabel.setText(number);
            resultlabel.setText("");
        }
        
        //clear entry part
        else if (buttonText.equals("CE")) {
            number = "0";
            showlabel.setText("0");
        }
        
        //plus or minus sysbol part
        else if (buttonText.equals("+/-")) {
            Plus_or_Minus();
        }
        
        //square root part
        else if (buttonText.equals("√")) {
            resultlabel.setText("√" + "(" + number + ")");
            square_root(number);
        }
        
        //power part
        else if (buttonText.equals("x^2")) {
            resultlabel.setText("sqr" + "(" + number + ")");
            power(number);
        }
        
        //reciprocal part
        else if (buttonText.equals("1/x")) {
            number = result(number, "1", '÷');
            format_number(number);
        }

        //percent part
        else if (buttonText.equals("%")) {
            percent();
        }
    }
    
}
