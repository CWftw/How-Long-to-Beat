package howlongtobeat.cwftw.me.howlongtobeat.models;

/**
 * Created by colin on 2015-12-02.
 */
public class Game {

    // Basic game info
    private String title;
    private int mainHours;
    private int mainExtraHours;
    private int completionistHours;
    private int combinedHours;
    private String imageUrl;
    private byte[] imageBytes;

    // Detailed game info
    private int polled;
    private int ratedPercent;
    private int backlogCount;
    private int playing;
    private int speedruns;
    private int retired;

    public Game(int mainHours, int mainExtraHours, int completionistHours, int combinedHours) {
        this.mainHours = mainHours;
        this.mainExtraHours = mainExtraHours;
        this.completionistHours = completionistHours;
        this.combinedHours = combinedHours;
    }

    public int getMainHours() {
        return mainHours;
    }

    public void setMainHours(int mainHours) {
        this.mainHours = mainHours;
    }

    public int getMainExtraHours() {
        return mainExtraHours;
    }

    public void setMainExtraHours(int mainExtraHours) {
        this.mainExtraHours = mainExtraHours;
    }

    public int getCompletionistHours() {
        return completionistHours;
    }

    public void setCompletionistHours(int completionistHours) {
        this.completionistHours = completionistHours;
    }

    public int getCombinedHours() {
        return combinedHours;
    }

    public void setCombinedHours(int combinedHours) {
        this.combinedHours = combinedHours;
    }

    public int getPolled() {
        return polled;
    }

    public void setPolled(int polled) {
        this.polled = polled;
    }

    public int getRatedPercent() {
        return ratedPercent;
    }

    public void setRatedPercent(int ratedPercent) {
        this.ratedPercent = ratedPercent;
    }

    public int getBacklogCount() {
        return backlogCount;
    }

    public void setBacklogCount(int backlogCount) {
        this.backlogCount = backlogCount;
    }

    public int getPlaying() {
        return playing;
    }

    public void setPlaying(int playing) {
        this.playing = playing;
    }

    public int getSpeedruns() {
        return speedruns;
    }

    public void setSpeedruns(int speedruns) {
        this.speedruns = speedruns;
    }

    public int getRetired() {
        return retired;
    }

    public void setRetired(int retired) {
        this.retired = retired;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
