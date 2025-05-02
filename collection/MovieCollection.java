package collection;

import model.Movie;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Хранит коллекцию фильмов и предоставляет методы для управления ею:
 * добавление, обновление, удаление, поиск и получение статистики.
 */
public class MovieCollection {
    /**
     * Множество фильмов.
     */
    private final Set<Movie> movies;

    /**
     * Дата инициализации коллекции.
     */
    private final LocalDate initDate;

    /**
     * Генератор уникальных ID для фильмов.
     */
    private final IdGenerator idGenerator;

    /**
     * Создаёт новую коллекцию с текущей датой и пустым набором фильмов.
     */
    public MovieCollection() {
        this.movies = new HashSet<>();
        this.initDate = LocalDate.now();
        this.idGenerator = new IdGenerator();
    }

    /**
     * Добавляет фильм в коллекцию и автоматически присваивает ему ID.
     * @param movie добавляемый фильм
     */
    public void add(Movie movie) {
        movie.setId(idGenerator.getNextId());
        movies.add(movie);
    }

    /**
     * Обновляет фильм по ID.
     * @param id идентификатор обновляемого фильма
     * @param updatedMovie новый объект фильма
     * @return true, если обновление прошло успешно, иначе false
     */
    public boolean update(long id, Movie updatedMovie) {
        Movie existing = getById(id);
        if (existing != null) {
            movies.remove(existing);
            updatedMovie.setId(id);
            movies.add(updatedMovie);
            return true;
        }
        return false;
    }

    /**
     * Удаляет фильм по его ID.
     * @param id идентификатор фильма
     * @return true, если фильм был найден и удалён, иначе false
     */
    public boolean removeById(long id) {

        return movies.removeIf(movie -> movie.getId() == id);
    }

    /**
     * Очищает коллекцию от всех элементов.
     */
    public void clear() {
        movies.clear();
    }

    /**
     * Возвращает фильм по его ID.
     * @param id идентификатор фильма
     * @return объект Movie или null, если не найден
     */
    public Movie getById(long id) {
        return movies.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Возвращает текущее количество фильмов в коллекции.
     * @return размер коллекции
     */
    public int size() {
        return movies.size();
    }

    /**
     * Возвращает дату инициализации коллекции.
     * @return дата создания коллекции
     */
    public LocalDate getInitDate() {
        return initDate;
    }

    /**
     * Возвращает копию коллекции фильмов.
     * @return множество фильмов
     */
    public Set<Movie> getMovies() {
        return new HashSet<>(movies);
    }

    /**
     * Возвращает фильм с максимальным значением (по имени).
     * @return фильм с наибольшим значением или null, если коллекция пуста
     */
    public Movie getMax() {
        return movies.stream().max(Movie::compareTo).orElse(null);
    }

    /**
     * Возвращает фильм с минимальным значением (по имени).
     * @return фильм с наименьшим значением или null, если коллекция пуста
     */
    public Movie getMin() {
        return movies.stream().min(Movie::compareTo).orElse(null);
    }

    /**
     * Удаляет все фильмы, которые меньше заданного по сравнению (compareTo).
     * @param movie фильм для сравнения
     * @return количество удалённых элементов
     */
    public int removeLowerThan(Movie movie) {
        int before = movies.size();
        movies.removeIf(m -> m.compareTo(movie) < 0);
        return before - movies.size();
    }
}
