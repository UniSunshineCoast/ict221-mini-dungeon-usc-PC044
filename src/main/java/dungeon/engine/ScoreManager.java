package dungeon.engine;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ScoreManager {

    private static final String FILE_NAME = "highScores.txt";
    private List<ScoreRecord> scores = new ArrayList<>();

    public ScoreManager() {
        loadScores();
    }

    private void loadScores() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int score = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                scores.add(new ScoreRecord(score, date));
            }
        } catch (IOException e) {
            System.out.println("Error reading scores: " + e.getMessage());
        }
    }

    public void addScore(int score) {
        ScoreRecord newRecord = new ScoreRecord(score, LocalDate.now());
        scores.add(newRecord);
        Collections.sort(scores); // sort by score descending
        saveScores();
        printTopScores();
        printRank(newRecord);
    }

    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ScoreRecord score : scores) {
                writer.write(score.getScore() + "," + score.getDate() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving scores: " + e.getMessage());
        }
    }

    private void printTopScores() {
        System.out.println("\n--- Top Scores ---");
        for (int i = 0; i < Math.min(5, scores.size()); i++) {
            ScoreRecord s = scores.get(i);
            System.out.printf("%d. %s%n", i + 1, s);
        }
    }

    private void printRank(ScoreRecord record) {
        int rank = scores.indexOf(record) + 1;
        System.out.println("\nYour final score: " + record.getScore() + " (Rank #" + rank + ")");
    }
}
