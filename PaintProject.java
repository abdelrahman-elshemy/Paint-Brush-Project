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
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;

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
        DrawPanel drawPanel = null;
        ButtonPanel buttonPanel = new ButtonPanel(drawPanel); // Pass DrawPanel object to ButtonPanel constructor

        drawPanel = new DrawPanel();
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

    private final DrawPanel drawPanel; // Declare drawPanel as an instance variable

    JCheckBox checkFilled;
    JButton btnRed, btnGreen, btnBlue, btnRectangle,
            btnOval, btnLine, btnPencil, btnEraser,
            btnClear, btnUndo, btnSave, btnOpen;

    public ButtonPanel(final DrawPanel drawPanel) {
        this.drawPanel = drawPanel; // Assign drawPanel to the instance variable

        checkFilled = new JCheckBox("Fill");
        checkFilled.setPreferredSize(new Dimension(50, 40));
        checkFilled.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (checkFilled.isSelected()) {
                    drawPanel.setFilled(true);
                } else {
                    drawPanel.setFilled(false);
                }
            }
        });

        btnRed = createIconButton(Color.red, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentColor(Color.RED);
            }
        });

        btnGreen = createIconButton(Color.GREEN, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentColor(Color.GREEN);
            }
        });

        btnBlue = createIconButton(Color.BLUE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentColor(Color.BLUE);
            }
        });

        btnRectangle = createShapeButton("Rectangle");
        btnOval = createShapeButton("Oval");
        btnLine = createShapeButton("Line");
        btnPencil = createShapeButton("Free Hand");
        btnEraser = createShapeButton("Eraser");

        btnClear = createIconButton(Color.WHITE, "Clear.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.clearAll();
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
    }

    private JButton createIconButton(Color color, ActionListener listener) {
        JButton button = new JButton();
        button.setBackground(color);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(50, 40));
        return button;
    }

    private JButton createIconButton(Color color, String fileLocation, ActionListener listener) {
        URL imageURL = getClass().getResource(fileLocation);
        ImageIcon icon = new ImageIcon(imageURL);
        JButton button = new JButton(icon);
        button.setBackground(color);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(50, 40));
        Image scaledImage = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        return button;
    }

    private JButton createShapeButton(final String shape) {
        JButton button = new JButton(shape);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentShape(shape);
            }
        });
        return button;
    }
}





class DrawPanel extends JPanel {

    private Color currentColor;
    private boolean isFilled;
    private int startX, startY, endX, endY;
    private String currentShape;

    DrawPanel() {
        this.setBackground(Color.WHITE);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                Graphics2D g2d = (Graphics2D) getGraphics();
                g2d.setColor(currentColor);
                if (currentShape.equals("Rectangle")) {
                    if (isFilled) {
                        g2d.fillRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    } else {
                        g2d.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    }
                } else if (currentShape.equals("Oval")) {
                    if (isFilled) {
                        g2d.fillOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    } else {
                        g2d.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    }
                } else if (currentShape.equals("Line")) {
                    g2d.drawLine(startX, startY, endX, endY);
                }
                g2d.dispose();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentShape.equals("Free Hand")) {
                    Graphics g = getGraphics();
                    g.setColor(currentColor);
                    g.drawLine(startX, startY, e.getX(), e.getY());
                    startX = e.getX();
                    startY = e.getY();
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    public void setCurrentShape(String shape) {
        currentShape = shape;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public void clearAll() {
        Graphics g = getGraphics();
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}