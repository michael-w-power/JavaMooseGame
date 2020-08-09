import com.almasb.fxgl.core.collection.Array;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList highScoreList = new ArrayList();

    public SaveData(ArrayList<HighScore> highScoreList) {
        this.highScoreList = highScoreList;
    }

    public ArrayList<HighScore> getHighScoreList() {
        return highScoreList;
    }

    public void sortHighScores(){
        Collections.sort(highScoreList,new sortHighScores());
    }

}
