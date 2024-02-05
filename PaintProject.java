package PaintProject;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
public class PaintProject extends JFrame {

//    PaintProject() {
//        setTitle("Paint");
//        setSize(1500, 1000);
//        setBackground(Color.CYAN);
//        setVisible(true);
//    }
    public static void main(String[] args) {
        //PaintProject frame = new PaintProject();
        JFrame frame = new JFrame();
        ButtonPanel buttonPanel = new ButtonPanel();
        DrawPanel drawPanel = new DrawPanel();
        frame.setSize(1500, 1000);
        
        //frame.setContentPane(drawPanel);
        //frame.setContentPane(buttonPanel);
        //panel.setLayout(new FlowLayout());
        frame.add(buttonPanel, BorderLayout.PAGE_START);
        frame.add(drawPanel, BorderLayout.CENTER);
        drawPanel.setBackground(Color.WHITE);
        
        frame.setTitle("Paint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

class ButtonPanel extends JPanel {

    JCheckBox checkFilled;
    JButton btnRed, btnGreen, btnBlue, btnRectangle,
            btnOval, btnLine, btnPencil, btnEraser,
            btnClear, btnUndo, btnSave, btnOpen;

    public ButtonPanel() {
        checkFilled = new JCheckBox("Fill");
        checkFilled.setPreferredSize(new Dimension(50, 40));
        checkFilled.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
        
        btnRed = createIconButton(Color.red, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnGreen = createIconButton(Color.GREEN, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnBlue = createIconButton(Color.BLUE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnRectangle = createIconButton(Color.WHITE, "Rectangle.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnOval = createIconButton(Color.WHITE, "Oval.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnLine = createIconButton(Color.WHITE, "Line.jpeg", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnPencil = createIconButton(Color.WHITE, "Pencil.jpg", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnEraser = createIconButton(Color.WHITE, "Eraser.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnClear = createIconButton(Color.WHITE, "Clear.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnUndo = createIconButton(Color.WHITE, "Undo.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnSave = createIconButton(Color.WHITE, "Save.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnOpen = createIconButton(Color.WHITE, "Open.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.setFocusable(true);
        add(checkFilled);
        add(btnRed);
        add(btnGreen);
        add(btnBlue);
        add(btnRectangle);
        add(btnOval);
        add(btnLine);
        add(btnPencil);
        add(btnEraser);
        add(btnClear);
        add(btnUndo);
        add(btnSave);
        add(btnOpen);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }

    private JButton createIconButton(Color color, String fileLocation, ActionListener listener) {
        // Get the URL of the image file
        URL imageURL = getClass().getResource(fileLocation);
        ImageIcon icon = new ImageIcon(imageURL);
        JButton button = new JButton(icon);
        // set the background Color
        button.setBackground(color);
        button.addActionListener(listener);
        // Set a specific size for the button
        button.setPreferredSize(new Dimension(50, 40));
        Image scaledImage = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        return button;
    }

    private JButton createIconButton(Color color, ActionListener listener) {

        JButton button = new JButton();
        // set the background Color
        button.setBackground(color);
        button.addActionListener(listener);
        // Set a specific size for the button
        button.setPreferredSize(new Dimension(50, 40));
        return button;
    }

}


class DrawPanel extends JPanel {

    DrawPanel() {

    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }
}
