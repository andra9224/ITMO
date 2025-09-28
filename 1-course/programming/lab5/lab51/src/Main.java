import core.MovieCollectionManager;

/**
 * Главная точка входа в программу. Запускает менеджер коллекции фильмов.
 */
public class Main {
    /**
     * Метод main — запускает приложение.
     * @param args аргумент командной строки, имя файла с коллекцией
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Укажите имя файла.");
            return;
        }
        new MovieCollectionManager(args[0]).run();
    }
}
