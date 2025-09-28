package server.commands;

import server.collection.MovieCollection;
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
     * Возвращает информацию о коллекции.
     *
     * @param args не используются
     * @param data не используется
     * @return строка с информацией о типе, дате и размере коллекции
     */
    @Override
    public Object execute(String[] args, Object data) {
        return "Тип коллекции: HashSet\n"
                + "Дата инициализации: " + collection.getInitDate() + "\n"
                + "Количество элементов: " + collection.size();
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}
