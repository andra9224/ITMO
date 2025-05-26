package collection;

/**
 * Генератор уникальных идентификаторов для фильмов.
 */
public class IdGenerator {
    private long currentId = 1;

    /**
     * Возвращает следующий уникальный идентификатор.
     * @return новый ID
     */
    public synchronized long getNextId() {

        return currentId++;
    }

    /**
     * Устанавливает текущий ID, если он больше текущего.
     * Используется при загрузке коллекции из файла.
     * @param id максимальный ID из коллекции
     */
    public void setCurrentId(long id) {
        if (id >= currentId) {
            this.currentId = id + 1;
        }
    }
}
