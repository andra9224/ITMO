package model;

/**
 * Представляет координаты X и Y.
 */
public class Coordinates {
    /**
     * Координата X. Не может быть null.
     */
    private Double x;

    /**
     * Координата Y. Должна быть больше -822.
     */
    private Integer y;

    /**
     * Возвращает координату X.
     * @return значение X
     */
    public Double getX() {
        return x;
    }

    /**
     * Устанавливает координату X.
     * @param x значение X
     * @throws IllegalArgumentException если x равен null
     */
    public void setX(Double x) {
        if (x == null) {
            throw new IllegalArgumentException("Координата X не может быть null");
        }
        this.x = x;
    }

    /**
     * Возвращает координату Y.
     * @return значение Y
     */
    public Integer getY() {
        return y;
    }

    /**
     * Устанавливает координату Y.
     * @param y значение Y (должно быть больше -822)
     * @throws IllegalArgumentException если y равен null или меньше/равно -822
     */
    public void setY(Integer y) {
        if (y == null || y <= -822) {
            throw new IllegalArgumentException("Координата Y должна быть больше -822");
        }
        this.y = y;
    }

    /**
     * Возвращает строковое представление координат.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
