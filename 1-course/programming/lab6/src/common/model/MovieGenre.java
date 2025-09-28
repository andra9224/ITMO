package common.model;

import java.io.Serializable;

/**
 * Перечисление возможных жанров фильмов.
 */
public enum MovieGenre implements Serializable {
    ACTION,
    THRILLER,
    FANTASY;

    /**
     * Выводит список доступных жанров в консоль.
     */
    public static void printGenres() {
        System.out.println("Доступные жанры:");
        for (MovieGenre genre : values()) {
            System.out.println("- " + genre);
        }
    }
}
