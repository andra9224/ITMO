package model;

import java.util.Date;

/**
 * Представляет информацию о режиссёре фильма.
 */
public class Person {
    /**
     * Имя режиссёра. Не может быть пустым.
     */
    private String name;

    /**
     * Дата рождения. Может быть null.
     */
    private Date birthday;

    /**
     * Рост в метрах. Должен быть больше 0.
     */
    private float height;

    /**
     * Вес в килограммах. Может быть null, но если указан — должен быть больше 0.
     */
    private Integer weight;

    /**
     * Возвращает имя.
     * @return имя режиссёра
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя.
     * @param name имя режиссёра
     * @throws IllegalArgumentException если имя null или пустое
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        this.name = name;
    }

    /**
     * Возвращает дату рождения.
     * @return дата рождения
     */
    public Date getBirthday() {

        return birthday;
    }

    /**
     * Устанавливает дату рождения.
     * @param birthday объект Date (может быть null)
     */
    public void setBirthday(Date birthday) {

        this.birthday = birthday;
    }

    /**
     * Возвращает рост.
     * @return рост в метрах
     */
    public float getHeight() {

        return height;
    }

    /**
     * Устанавливает рост.
     * @param height рост в метрах
     * @throws IllegalArgumentException если значение меньше или равно 0
     */
    public void setHeight(float height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Рост должен быть больше 0");
        }
        this.height = height;
    }

    /**
     * Возвращает вес.
     * @return вес в килограммах (может быть null)
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Устанавливает вес.
     * @param weight вес в килограммах (может быть null)
     * @throws IllegalArgumentException если указанное значение не больше 0
     */
    public void setWeight(Integer weight) {
        if (weight != null && weight <= 0) {
            throw new IllegalArgumentException("Вес должен быть больше 0");
        }
        this.weight = weight;
    }

    /**
     * Возвращает строковое представление режиссёра.
     */
    @Override
    public String toString() {
        String formattedDate = (birthday != null)
                ? new java.text.SimpleDateFormat("dd-MM-yyyy").format(birthday)
                : "не указана";

        return name + " (дата рождения: " + formattedDate +
                ", рост: " + String.format("%.2f", height) + " м" +
                (weight != null ? ", вес: " + weight + " кг" : "") + ")";
    }
}
