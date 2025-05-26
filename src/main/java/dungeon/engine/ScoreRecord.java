package dungeon.engine;

import java.time.LocalDate;

public class ScoreRecord implements Comparable<ScoreRecord> {
    private int score;
    private LocalDate date;

    public ScoreRecord(int score, LocalDate date) {
        this.score = score;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(ScoreRecord other) {
        return Integer.compare(other.score, this.score); // Descending order
    }

    @Override
    public String toString() {
        return "Score: " + score + ", Date: " + date;
    }
}
