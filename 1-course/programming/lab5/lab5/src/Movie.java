import java.time.LocalDate;

public class Movie implements Validatable{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private int usaBoxOffice; //Значение поля должно быть больше 0, (кассовые сборы)
    private Integer length; //Поле не может быть null, Значение поля должно быть больше 0
    private MovieGenre genre; //Поле не может быть null
    private Person director; //Поле не может быть null

    public Movie(Long id, String name, Coordinates coordinates, LocalDate creationDate, int oscarCount, int usaBoxOffice, Integer length, MovieGenre genre, Person director){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarCount;
        this.usaBoxOffice = usaBoxOffice;
        this.length = length;
        this.genre = genre;
        this.director = director;

    }

    @Override
    public boolean isValid(){
        if (id == null || id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (oscarsCount < 0) return false;
        if (usaBoxOffice < 0) return false;
        if (length == null || length <= 0) return false;
        if (genre == null) return false;
        if (director == null) return false;
        return true;

    }

    @Override
    public String toString(){
        return "movie{id = " + id + ", name = " + name + ", coordinates = " + coordinates + ", creationDate = " + creationDate + ", oscarsCount = " + oscarsCount + ", usaBoxOffice = " + usaBoxOffice + ", length = " + length + ", genre = " + genre +", director = " + director + "}";

    }

}
