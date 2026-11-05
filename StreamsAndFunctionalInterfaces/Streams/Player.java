package StreamsAndFunctionalInterfaces.Streams;

import java.util.*;

public class Player {
    private int id;
    private String name;
    private int score;
    private boolean active;
    private List<String> games;

    public Player(int id, String name, int score,
                  boolean active, List<String> games) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.active = active;
        this.games = games;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public boolean isActive() {
        return active;
    }

    public List<String> getGames() {
        return games;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", active=" + active +
                '}';
    }
}
