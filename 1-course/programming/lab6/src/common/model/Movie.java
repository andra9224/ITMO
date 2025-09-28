package common.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Класс, представляющий фильм с характеристиками: название, координаты, дата создания,
 * награды, жанр и режиссёр. Используется как основной элемент коллекции фильмов.
 */
public class Movie implements Comparable<Movie>, Serializable {

    /**
     * Уникальный идентификатор фильма. Устанавливается автоматически и не повторяется.
     */
    private long id;

    /**
     * Название фильма. Не может быть пустым.
     */
    private String name;

    /**
     * Координаты, связанные с фильмом. Не могут быть null.
     */
    private Coordinates coordinates;

    /**
     * Дата создания фильма. Устанавливается автоматически при создании объекта.
     */
    private final LocalDate creationDate;

    /**
     * Количество Оскаров. Значение должно быть больше 0.
     */
    private int oscarsCount;

    /**
     * Кассовые сборы в США. Значение должно быть больше 0.
     */
    private int usaBoxOffice;

    /**
     * Продолжительность фильма в минутах. Значение должно быть больше 0.
     */
    private Integer length;

    /**
     * Жанр фильма. Не может быть null.
     */
    private MovieGenre genre;

    /**
     * Режиссёр фильма. Не может быть null.
     */
    private Person director;

    /**
     * Конструктор. Устанавливает дату создания на текущую.
     */
    public Movie() {
        this.creationDate = LocalDate.now();
    }

    /**
     * Возвращает ID фильма.
     * @return уникальный идентификатор
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает уникальный ID фильма.
     * @param id идентификатор
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Возвращает название фильма.
     * @return имя фильма
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название фильма.
     * @param name имя фильма
     * @throws IllegalArgumentException если имя пустое или null
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название фильма не может быть пустым");
        }
        this.name = name;
    }

    /**
     * Возвращает координаты фильма.
     * @return координаты
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает координаты фильма.
     * @param coordinates координаты
     * @throws IllegalArgumentException если координаты null
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
        this.coordinates = coordinates;
    }

    /**
     * Возвращает дату создания фильма.
     * @return дата создания
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает количество Оскаров.
     * @return число наград
     */
    public int getOscarsCount() {
        return oscarsCount;
    }

    /**
     * Устанавливает количество Оскаров.
     * @param oscarsCount число наград
     * @throws IllegalArgumentException если значение не больше 0
     */
    public void setOscarsCount(int oscarsCount) {
        if (oscarsCount <= 0) {
            throw new IllegalArgumentException("Количество оскаров должно быть больше 0");
        }
        this.oscarsCount = oscarsCount;
    }

    /**
     * Возвращает кассовые сборы в США.
     * @return сумма сборов
     */
    public int getUsaBoxOffice() {
        return usaBoxOffice;
    }

    /**
     * Устанавливает кассовые сборы в США.
     * @param usaBoxOffice сумма сборов
     * @throws IllegalArgumentException если значение не больше 0
     */
    public void setUsaBoxOffice(int usaBoxOffice) {
        if (usaBoxOffice <= 0) {
            throw new IllegalArgumentException("Кассовые сборы в США должны быть больше 0");
        }
        this.usaBoxOffice = usaBoxOffice;
    }

    /**
     * Возвращает продолжительность фильма.
     * @return длина в минутах
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Устанавливает длину фильма.
     * @param length продолжительность в минутах
     * @throws IllegalArgumentException если значение null или не больше 0
     */
    public void setLength(Integer length) {
        if (length == null || length <= 0) {
            throw new IllegalArgumentException("Длина фильма должна быть больше 0");
        }
        this.length = length;
    }

    /**
     * Возвращает жанр фильма.
     * @return жанр
     */
    public MovieGenre getGenre() {
        return genre;
    }

    /**
     * Устанавливает жанр фильма.
     * @param genre жанр
     * @throws IllegalArgumentException если значение null
     */
    public void setGenre(MovieGenre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Жанр не может быть null");
        }
        this.genre = genre;
    }

    /**
     * Возвращает режиссёра фильма.
     * @return объект Person
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Устанавливает режиссёра фильма.
     * @param director объект Person
     * @throws IllegalArgumentException если значение null
     */
    public void setDirector(Person director) {
        if (director == null) {
            throw new IllegalArgumentException("Режиссёр не может быть null");
        }
        this.director = director;
    }

    /**
     * Сравнивает фильмы по названию (лексикографически).
     * @param other другой фильм
     * @return результат сравнения
     */
    @Override
    public int compareTo(Movie other) {
        return this.name.compareTo(other.name);
    }

    /**
     * Возвращает компактное строковое представление фильма.
     */
    @Override
    public String toString() {
        return "Фильм #" + id +
                " | Название: " + name +
                " | Координаты: " + coordinates +
                " | Дата создания: " + creationDate +
                " | Оскары: " + oscarsCount +
                " | Сборы в США: " + usaBoxOffice +
                " | Длина: " + length + " мин" +
                " | Жанр: " + genre +
                " | Режиссёр: " + director;
    }
}
