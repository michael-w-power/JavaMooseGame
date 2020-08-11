import java.io.Serializable;

/**
 * Highscore class, used to save score and name to the highscore list.
 */
public class HighScore implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int score;

    /**
     * Constructor responsible for creating a HighScore object.
     * @param name
     * @param score
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * getters for name in HighScore object.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getters for score in HighScore object.
     * @return
     */
    public int getScore() {
        return score;
    }
}



