package dungeon.engine;

import java.time.LocalDate;

public class ScoreRecord implements Comparable<ScoreRecord> {
    private int score;
    private LocalDate date; //Date format = YYYY-MM-DD

    public ScoreRecord(int score, LocalDate date) {
        this.score = score;
        this.date = date;
    }

    //Sorts the order of previous records compared to the current record
    @Override
    public int compareTo(ScoreRecord other) {
        return Integer.compare(other.score, this.score); // This is in descending order
    }

    @Override
    public String toString() {
        return "Score: " + score + ", Date: " + date;
    }

    //Getter & Setter
    public int getScore() {
        return score;
    }

    public LocalDate getDate() {
        return date;
    }
}
