package StreamsAndFunctionalInterfaces.FunctionalInterfacesExamples;

import java.util.List;

class Player {
    private int id;
    private String name;
    private int score;
    private boolean active;
    private List<String> games;

    public Player(int id, String name, int score, boolean active, List<String> games) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", active=" + active +
                ", games=" + games +
                '}';
    }
}