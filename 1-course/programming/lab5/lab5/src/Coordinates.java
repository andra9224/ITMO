public class Coordinates implements Validatable{
    private Double x; //Поле не может быть null
    private int y; //Значение поля должно быть больше -822

    public Coordinates( Double x, int y){
        this.x = x;
        this.y = y;

    }

    @Override
    public boolean isValid(){
        if (x == null) return false;
        if (y <= -822) return false;
        return true;

    }

    @Override
    public String toString(){
        return x + "; " + y;
    }
}
