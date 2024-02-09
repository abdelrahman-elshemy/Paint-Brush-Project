package PaintProject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PaintProject extends JFrame {
    PaintProject() {
        JFrame frame = new JFrame();
        DrawPanel drawPanel = new DrawPanel();
        ButtonPanel buttonPanel = new ButtonPanel(drawPanel);
        frame.setSize(1500, 1000);
        frame.add(buttonPanel, BorderLayout.PAGE_START);
        frame.add(drawPanel, BorderLayout.CENTER);
        drawPanel.setBackground(Color.WHITE);

        frame.setTitle("Paint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        PaintProject frame = new PaintProject();

    }
}

class ButtonPanel extends JPanel {

    JCheckBox checkFilled;
    JButton btnRed, btnGreen, btnBlue, btnRectangle,
            btnOval, btnLine, btnPencil, btnEraser,
            btnClear, btnSave, btnOpen;

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

        btnSave = createIconButton(Color.WHITE, "Save.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int result = fileChooser.showSaveDialog(ButtonPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedImage image = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = image.createGraphics();
                        drawPanel.paint(g2d);
                        ImageIO.write(image, "PNG", selectedFile);
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
                        BufferedImage image = ImageIO.read(selectedFile);
                        drawPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
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
        add(btnSave);
        add(btnOpen);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

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

    private JButton createIconButton(Color color, ActionListener listener) {

        JButton button = new JButton();
        button.setBackground(color);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(50, 40));
        return button;
    }

}

class DrawPanel extends JPanel {

    private Color currentColor = Color.BLACK;
    private boolean isFilled;
    private int startX, startY, endX, endY;
    private String currentShape = "Pencil";

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
            Graphics2D g2d = (Graphics2D) getGraphics();
            g2d.setColor(currentColor);

            switch (currentShape) {
                case "Pencil": {
                    g2d.setColor(currentColor);
                    g2d.drawLine(startX, startY, e.getX(), e.getY());
                    startX = e.getX();
                    startY = e.getY();
                    break;
                }
                case "Eraser": {
                    g2d.setColor(getBackground());
                    g2d.fillRect(startX, startY, 40, 40);
                    startX = e.getX();
                    startY = e.getY();
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
    public void paint(Graphics g2d) {
        super.paint(g2d);
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
