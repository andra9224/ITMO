import java.util.Date;

public class Person implements Validatable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Date birthday; //Поле может быть null
    private float height; //Значение поля должно быть больше 0
    private Integer weight; //Поле может быть null, Значение поля должно быть больше 0

    public Person(String name, Date birthday, float height, Integer weight){
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this. weight = weight;

    }

    @Override
    public boolean isValid(){
        if (name == null || name.isEmpty()) return false;
        if (birthday == null) return false;
        if (height <= 0) return false;
        if (weight == null || weight <= 0) return false;
        return true;
    }

    @Override
    public String toString(){
        return "person{name = " + name + ", birthday = " + birthday + ", height = " + ", weight = " + weight + "}";
    }
}
