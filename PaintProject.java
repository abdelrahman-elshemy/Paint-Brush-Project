package PaintProject;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
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
        DrawPanel drawPanel = new DrawPanel();
        ButtonPanel buttonPanel = new ButtonPanel(drawPanel);
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

    public ButtonPanel(final DrawPanel drawPanel) {

        /* Just default State*/
        drawPanel.setCurrentShape("Pencil");
        drawPanel.setCurrentColor(Color.BLACK);

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

        btnRectangle = createIconButton(Color.WHITE, "Rectangle.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentShape("Rectangle");
                if (drawPanel.getCurrentColor() == Color.WHITE) {
                    drawPanel.setCurrentColor(Color.BLACK);
                }
            }
        });

        btnOval = createIconButton(Color.WHITE, "Oval.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentShape("Oval");
                if (drawPanel.getCurrentColor() == Color.WHITE) {
                    drawPanel.setCurrentColor(Color.BLACK);
                }
            }
        });

        btnLine = createIconButton(Color.WHITE, "Line.jpeg", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentShape("Line");
                if (drawPanel.getCurrentColor() == Color.WHITE) {
                    drawPanel.setCurrentColor(Color.BLACK);
                }

            }
        });

        btnPencil = createIconButton(Color.WHITE, "Pencil.jpg", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentShape("Pencil");
                if (drawPanel.getCurrentColor() == Color.WHITE) {
                    drawPanel.setCurrentColor(Color.BLACK);
                }

            }
        });

        btnEraser = createIconButton(Color.WHITE, "Eraser.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentShape("Eraser");
                drawPanel.setCurrentColor(Color.WHITE);

            }
        });

        btnClear = createIconButton(Color.WHITE, "Clear.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.clearAll();
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
                JFileChooser fileChooser = new JFileChooser();
                // Set default directory (optional)
                //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

                // Show save dialog
                int result = fileChooser.showSaveDialog(ButtonPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // Create an image of the drawing panel
                        BufferedImage image = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = image.createGraphics();
                        drawPanel.paint(g2d);
                        // Write the image to the selected file
                        ImageIO.write(image, "PNG", selectedFile);
                        // Dispose of graphics context
                        g2d.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnOpen = createIconButton(Color.WHITE, "Open.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                // Show open dialog
                int result = fileChooser.showOpenDialog(ButtonPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // Read the image from the selected file
                        BufferedImage image = ImageIO.read(selectedFile);
                        // Set the size of the drawing panel to match the size of the loaded image
                        drawPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
                        // Repaint the drawing panel with the loaded image
                        Graphics g = drawPanel.getGraphics();
                        g.drawImage(image, 0, 0, null);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
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

    private Color currentColor;
    private boolean isFilled;
    private int startX, startY, endX, endY;
    private String currentShape;

    DrawPanel() {
        MyMouseListner ML = new MyMouseListner();
        this.setFocusable(true);
        this.addMouseListener(ML);
        this.addMouseMotionListener(ML);
    }

    public class MyMouseListner implements MouseListener, MouseMotionListener {
        @Override
        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
            if (currentShape.equals("Pencil") || currentShape.equals("Eraser")) {
                Graphics g = getGraphics();
                g.setColor(currentColor);
                g.fillRect(startX, startY, 2, 2); // Draw a tiny square at the starting point
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            endX = e.getX();
            endY = e.getY();
            Graphics2D g2d = (Graphics2D) getGraphics();
            g2d.setColor(currentColor);
            switch (currentShape) {
                case "Rectangle":
                    if (isFilled) {
                        g2d.fillRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    } else {
                        g2d.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    }
                    break;
                case "Oval":
                    if (isFilled) {
                        g2d.fillOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    } else {
                        g2d.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    }
                    break;
                case "Line":
                    g2d.drawLine(startX, startY, endX, endY);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            endX = e.getX();
            endY = e.getY();
            Graphics g = getGraphics();
            g.setColor(currentColor);
            switch (currentShape) {
                case "Pencil": {
                    g.drawLine(startX, startY, endX, endY);
                    startX = endX;
                    startY = endY;
                    break;
                }
                case "Eraser": {
                    g.setColor(getBackground());
                    g.fillRect(endX - 5, endY - 5, 10, 10); // Erase a small area around the cursor
                    startX = endX;
                    startY = endY;
                    break;
                }
                case "Rectangle": {
                    repaint();
                    break;
                }
                case "Oval": {
                    repaint();
                    break;
                }
                case "Line": {
                    repaint();
                    break;
                }
                default:
                    break;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentShape.equals("Rectangle") || currentShape.equals("Oval") || currentShape.equals("Line")) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(currentColor);
            switch (currentShape) {
                case "Rectangle":
                    if (isFilled) {
                        g2d.fillRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    } else {
                        g2d.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    }
                    break;
                case "Oval":
                    if (isFilled) {
                        g2d.fillOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    } else {
                        g2d.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    }
                    break;
                case "Line":
                    g2d.drawLine(startX, startY, endX, endY);
                    break;
                default:
                    break;
            }
        }
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentShape(String shape) {
        currentShape = shape;
    }

    public String getCurrentShape() {
        return currentShape;
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
