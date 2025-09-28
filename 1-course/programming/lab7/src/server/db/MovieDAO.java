package server.db;

import common.model.Coordinates;
import common.model.Movie;
import common.model.MovieGenre;
import common.model.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private final Connection connection;

    public MovieDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Добавление фильма в БД. Возвращает сгенерированный ID.
     * Владелец сохраняется в колонку owner_username.
     */
    public long addMovie(Movie movie, String ownerUsername) throws SQLException {
        String sql = """
            INSERT INTO movies (name, x, y, creation_date, oscars_count, usa_box_office,
                                length, genre, director_name, director_birthday,
                                director_height, director_weight, owner_username)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getCoordinates().getX());
            stmt.setInt(3, movie.getCoordinates().getY());

            stmt.setDate(4, Date.valueOf(movie.getCreationDate()));

            stmt.setInt(5, movie.getOscarsCount());
            stmt.setInt(6, movie.getUsaBoxOffice());
            stmt.setInt(7, movie.getLength());
            stmt.setString(8, movie.getGenre().name());
            stmt.setString(9, movie.getDirector().getName());

            if (movie.getDirector().getBirthday() != null) {
                stmt.setDate(10, new Date(movie.getDirector().getBirthday().getTime()));
            } else {
                stmt.setNull(10, Types.DATE);
            }

            stmt.setFloat(11, movie.getDirector().getHeight());

            if (movie.getDirector().getWeight() != null) {
                stmt.setInt(12, movie.getDirector().getWeight());
            } else {
                stmt.setNull(12, Types.INTEGER);
            }

            stmt.setString(13, ownerUsername);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                movie.setId(id);
                movie.setOwner(ownerUsername);
                return id;
            } else {
                throw new SQLException("Не удалось получить ID вставленного фильма.");
            }
        }
    }

    /** Загрузка всех фильмов из БД. */
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT * FROM movies";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(parseMovie(rs));
            }
        }

        return movies;
    }

    /** Удалить фильм по ID (только если владелец совпадает). */
    public boolean removeById(long id, String owner) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ? AND owner_username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.setString(2, owner);
            return stmt.executeUpdate() > 0;
        }
    }

    /** Обновить фильм по ID (только если владелец совпадает). */
    public boolean updateMovie(long id, Movie movie, String owner) throws SQLException {
        String sql = """
            UPDATE movies SET name=?, x=?, y=?, oscars_count=?, usa_box_office=?,
                              length=?, genre=?, director_name=?, director_birthday=?,
                              director_height=?, director_weight=?
            WHERE id=? AND owner_username=?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getCoordinates().getX());
            stmt.setInt(3, movie.getCoordinates().getY());
            stmt.setInt(4, movie.getOscarsCount());
            stmt.setInt(5, movie.getUsaBoxOffice());
            stmt.setInt(6, movie.getLength());
            stmt.setString(7, movie.getGenre().name());
            stmt.setString(8, movie.getDirector().getName());

            if (movie.getDirector().getBirthday() != null) {
                stmt.setDate(9, new Date(movie.getDirector().getBirthday().getTime()));
            } else {
                stmt.setNull(9, Types.DATE);
            }

            stmt.setFloat(10, movie.getDirector().getHeight());

            if (movie.getDirector().getWeight() != null) {
                stmt.setInt(11, movie.getDirector().getWeight());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            stmt.setLong(12, id);
            stmt.setString(13, owner);

            boolean ok = stmt.executeUpdate() > 0;
            if (ok) {
                movie.setId(id);
                movie.setOwner(owner);
            }
            return ok;
        }
    }

    /** Удалить все фильмы пользователя. */
    public int clearUserMovies(String owner) throws SQLException {
        String sql = "DELETE FROM movies WHERE owner_username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, owner);
            return stmt.executeUpdate();
        }
    }

    /** Получить все фильмы конкретного пользователя. */
    public List<Movie> getMoviesByOwner(String owner) throws SQLException {
        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT * FROM movies WHERE owner_username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, owner);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(parseMovie(rs));
                }
            }
        }

        return movies;
    }

    /**
     * Маппинг строки из ResultSet в доменный объект Movie.
     */
    private Movie parseMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie();

        movie.setId(rs.getLong("id"));
        movie.setName(rs.getString("name"));

        Coordinates coords = new Coordinates();
        coords.setX(rs.getDouble("x"));
        coords.setY(rs.getInt("y"));
        movie.setCoordinates(coords);

        Date sqlCreation = rs.getDate("creation_date");
        if (sqlCreation != null) {
            LocalDate created = sqlCreation.toLocalDate();
            movie.setCreationDate(created);
        }

        movie.setOscarsCount(rs.getInt("oscars_count"));
        movie.setUsaBoxOffice(rs.getInt("usa_box_office"));
        movie.setLength(rs.getInt("length"));
        movie.setGenre(MovieGenre.valueOf(rs.getString("genre")));

        Person director = new Person();
        director.setName(rs.getString("director_name"));

        Date sqlBirthday = rs.getDate("director_birthday");
        if (sqlBirthday != null) {
            director.setBirthday(new java.util.Date(sqlBirthday.getTime()));
        }

        director.setHeight(rs.getFloat("director_height"));
        int weight = rs.getInt("director_weight");
        if (!rs.wasNull()) {
            director.setWeight(weight);
        }
        movie.setDirector(director);

        movie.setOwner(rs.getString("owner_username"));

        return movie;
    }
}

