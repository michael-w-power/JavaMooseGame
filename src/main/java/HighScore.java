import java.io.Serializable;
import java.util.Comparator;

public class HighScore implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

}



