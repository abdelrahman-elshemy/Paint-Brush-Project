import java.awt.*;

public interface projectInterface {
    // Method to set the current drawing color
    void setColor(Color color);

    // Method to set the current drawing shape (Rectangle, Oval, Line)
    void setShape(ShapeType shapeType);

    // Method to set the drawing mode (Free Hand or Eraser)
    void setDrawingMode(DrawingMode drawingMode);

    // Method to set whether the shape should be filled or not
    void setFilled(boolean filled);

    // Method to clear all drawings
    void clearAll();

    // Method to perform undo operation
    void undo();

    // Method to handle mouse press event
    void handleMousePress(int x, int y);

    // Method to handle mouse drag event
    void handleMouseDrag(int x, int y);

    // Method to handle mouse release event
    void handleMouseRelease(int x, int y);

    // Enum to represent different drawing modes (Free Hand or Eraser)
    enum DrawingMode {
        FREE_HAND,
        ERASER
    }

    // Enum to represent different shapes
    enum ShapeType {
        RECTANGLE,
        OVAL,
        LINE
    }
}
