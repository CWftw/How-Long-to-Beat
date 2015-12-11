package howlongtobeat.cwftw.me.howlongtobeat.models;

import java.sql.Time;

/**
 * Created by colin on 2015-12-02.
 */
public class Game {

    // Basic game info
    private String title;
    private Time mainHours;
    private Time mainExtraHours;
    private Time completionistHours;
    private Time combinedHours;
    private String imageUrl;
    private byte[] imageBytes;

    // Detailed game info
    private int polled;
    private int ratedPercent;
    private int backlogCount;
    private int playing;
    private int speedruns;
    private int retired;

    public Game(String title, Time mainHours, Time mainExtraHours, Time completionistHours,
                Time combinedHours, byte[] imageBytes)
    {
        this.title = title;
        this.mainHours = mainHours;
        this.mainExtraHours = mainExtraHours;
        this.completionistHours = completionistHours;
        this.combinedHours = combinedHours;
        this.imageBytes = imageBytes;
    }

    public Time getMainHours() {
        return mainHours;
    }

    public void setMainHours(Time mainHours) {
        this.mainHours = mainHours;
    }

    public Time getMainExtraHours() {
        return mainExtraHours;
    }

    public void setMainExtraHours(Time mainExtraHours) {
        this.mainExtraHours = mainExtraHours;
    }

    public Time getCompletionistHours() {
        return completionistHours;
    }

    public void setCompletionistHours(Time completionistHours) {
        this.completionistHours = completionistHours;
    }

    public Time getCombinedHours() {
        return combinedHours;
    }

    public void setCombinedHours(Time combinedHours) {
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
