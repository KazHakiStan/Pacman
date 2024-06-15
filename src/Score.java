public class Score {
    private final String map;
    private final String nickname;
    private final int score;
    private final int time;

    public Score(int score, int time, String nickname, String map) {
        this.score = score;
        this.time = time;
        this.nickname = nickname;
        this.map = map;
    }

    public String getMap() {
        return map;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Score [map=" + map + ", nickname=" + nickname + ", score=" + score + ", time=" + time + "]";
    }

    
}
