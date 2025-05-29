package dungeon.engine;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ScoreManager {

    private static final String FILE_NAME = "highScores.txt"; //File name will always be 'highScores.txt'
    private List<ScoreRecord> scores = new ArrayList<>(); //Keep a list of scores

    public ScoreManager() {
        loadScores();
    }

    private void loadScores() {
        //Create File if it doesn't exist
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        //File must be in correct Format = [data1,data2]
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            //Each line that is NOT null, split data with delimiter ',' = [1][2]
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int score = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                scores.add(new ScoreRecord(score, date));
            }
        //Catch invalid File Format
        } catch (IOException e) {
            System.out.println("Error reading scores: " + e.getMessage());
        }
    }

    //Store every record saved in txt file. One record per line.
    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ScoreRecord score : scores) {
                writer.write(score.getScore() + "," + score.getDate() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving scores: " + e.getMessage());
        }
    }

    //Add score and local time to List. Save Files, then print top scores.
    public void addScore(int score) {
        ScoreRecord newRecord = new ScoreRecord(score, LocalDate.now());
        scores.add(newRecord);
        Collections.sort(scores); // sort by score descending
        saveScores();
        printTopScores();
        printRank(newRecord);
    }

    //Print ONLY top 5 scores. (The rest will be in txt.file)
    private void printTopScores() {
        System.out.println("\n--- Top Scores ---");
        for (int i = 0; i < Math.min(5, scores.size()); i++) {
            ScoreRecord s = scores.get(i);
            System.out.printf("%d. %s%n", i + 1, s);
        }
    }

    //Print what rank you came on scoreboard (1st, 2nd, 3rd etc.)
    private void printRank(ScoreRecord record) {
        int rank = scores.indexOf(record) + 1;
        System.out.println("\nYour final score: " + record.getScore() + " (Rank #" + rank + ")");
    }
}
