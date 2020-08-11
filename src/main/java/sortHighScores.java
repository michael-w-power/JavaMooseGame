import java.util.Comparator;

/**
 * Comparator class used only to sort the highscores in descending order.
 */
class sortHighScores implements Comparator<HighScore>
{
    public int compare(HighScore a, HighScore b)
    {
        return  b.getScore() - a.getScore();
    }
}