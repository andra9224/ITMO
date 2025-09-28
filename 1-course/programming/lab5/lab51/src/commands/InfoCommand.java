package commands;

import collection.MovieCollection;
import java.time.format.DateTimeFormatter;

/**
 * Команда вывода информации о коллекции фильмов.
 */
public class InfoCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public InfoCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Выполняет команду: отображает тип коллекции, дату инициализации и количество элементов.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Информация о коллекции:");
        System.out.println("Тип коллекции: HashSet");
        System.out.println("Дата инициализации: " +
                collection.getInitDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.println("Количество элементов: " + collection.size());
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}
