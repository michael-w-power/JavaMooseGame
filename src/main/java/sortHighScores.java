import java.util.Comparator;

class sortHighScores implements Comparator<HighScore>
{
    // Used for sorting in descending order
    public int compare(HighScore a, HighScore b)
    {
        return  b.getScore() - a.getScore();
    }
}