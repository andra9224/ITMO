package server.repository;

import common.model.Movie;
import server.db.MovieDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Репозиторий фильмов: потокобезопасная коллекция в памяти + синхронизация с БД.
 * Правило: сначала успешная операция в БД, затем (и только тогда) изменение памяти.
 */
public class MovieRepository {

    private final MovieDAO dao;
    /** Храним фильмы по id для быстрых операций. */
    private final ConcurrentHashMap<Long, Movie> byId = new ConcurrentHashMap<>();

    public MovieRepository(MovieDAO dao) {
        this.dao = dao;
    }

    /** Загружает все фильмы из БД в память. Вызывать при старте сервера. */
    public void loadAll() throws SQLException {
        byId.clear();
        dao.getAllMovies().forEach(m -> byId.put(m.getId(), m));
    }

    /** Возвращает все фильмы из памяти, отсортированные по имени (Comparable<Movie>). */
    public List<Movie> all() {
        return byId.values().stream()
                .sorted()
                .toList();
    }

    /** Возвращает все фильмы указанного владельца (из памяти), отсортированные по имени. */
    public List<Movie> byOwner(String ownerLogin) {
        return byId.values().stream()
                .filter(m -> ownerLogin != null && ownerLogin.equals(m.getOwner()))
                .sorted()
                .toList();
    }

    /** Находит фильм по id (из памяти). */
    public Optional<Movie> byId(long id) {
        return Optional.ofNullable(byId.get(id));
    }

    /**
     * Добавляет фильм: сначала БД, затем — память.
     * Возвращает сгенерированный id.
     */
    public synchronized long add(Movie movie, String ownerLogin) throws SQLException {
        long id = dao.addMovie(movie, ownerLogin);
        byId.put(id, movie);
        return id;
    }

    /**
     * Обновляет фильм по id (только владелец): сначала БД, затем — память.
     * ВАЖНО: сохраняем неизменяемые серверные поля (creationDate, owner) из старого объекта.
     */
    public synchronized boolean update(long id, Movie newState, String ownerLogin) throws SQLException {
        boolean ok = dao.updateMovie(id, newState, ownerLogin);
        if (ok) {
            Movie old = byId.get(id);
            if (old != null) {

                newState.setId(id);
                newState.setCreationDate(old.getCreationDate());
                newState.setOwner(old.getOwner());
            } else {

                newState.setId(id);
                newState.setOwner(ownerLogin);

            }
            byId.put(id, newState);
        }
        return ok;
    }

    /**
     * Удаляет фильм по id (только владелец): сначала БД, затем — память.
     */
    public synchronized boolean remove(long id, String ownerLogin) throws SQLException {
        boolean ok = dao.removeById(id, ownerLogin);
        if (ok) {
            byId.remove(id);
        }
        return ok;
    }

    /**
     * Удаляет все фильмы владельца: для каждого id — сначала БД, затем — память.
     * Возвращает количество удалённых.
     */
    public synchronized int clearOwned(String ownerLogin) throws SQLException {

        List<Long> ids = byId.values().stream()
                .filter(m -> ownerLogin != null && ownerLogin.equals(m.getOwner()))
                .map(Movie::getId)
                .toList();

        int removed = 0;
        for (Long id : ids) {
            if (dao.removeById(id, ownerLogin)) {
                if (byId.remove(id) != null) removed++;
            }
        }
        return removed;
    }

    /**
     * Возвращает любой фильм с минимальным usaBoxOffice (из памяти), если есть.
     */
    public Optional<Movie> anyWithMinUsaBoxOffice() {
        return byId.values().stream()
                .min(Comparator.comparingInt(Movie::getUsaBoxOffice));
    }

    /**
     * Считает количество фильмов с length < target (из памяти).
     */
    public long countWithLengthLessThan(int target) {
        return byId.values().stream()
                .filter(m -> m.getLength() < target)
                .count();
    }

    /**
     * Фильтрует по префиксу имени (из памяти).
     */
    public List<Movie> filterStartsWithName(String prefix) {
        return byId.values().stream()
                .filter(m -> m.getName().startsWith(prefix))
                .sorted()
                .toList();
    }

    /**
     * Возвращает id фильмов текущего владельца, которые меньше sample (по compareTo).
     * Удобно для remove_lower.
     */
    public List<Long> idsLowerThan(Movie sample, String ownerLogin) {
        List<Long> ids = new ArrayList<>();
        byId.values().forEach(m -> {
            if (ownerLogin != null && ownerLogin.equals(m.getOwner()) && m.compareTo(sample) < 0) {
                ids.add(m.getId());
            }
        });
        return ids;
    }
}
