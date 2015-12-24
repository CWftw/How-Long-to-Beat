/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.gamelength.models;

public class Game {

    // Basic game info
    private int id;
    private String title;
    private double mainHours;
    private double mainExtraHours;
    private double completionistHours;
    private double combinedHours;
    private String imageUrl;
    private byte[] imageBytes;

    // Detailed game info
    private double polled;
    private double ratedPercent;
    private double backlogCount;
    private double playing;
    private double speedruns;
    private double retired;

    public Game() {
        // Initialize fields with 'empty' data
        this.title = "";
        this.mainHours = -1;
        this.mainExtraHours = -1;
        this.completionistHours = -1;
        this.combinedHours = -1;
        this.imageUrl = "";
        this.imageBytes = null;

        this.polled = -1;
        this.ratedPercent = -1;
        this.backlogCount = -1;
        this.playing = -1;
        this.speedruns = -1;
        this.retired = -1;
    }

    public double getMainHours() {
        return mainHours;
    }

    public void setMainHours(double mainHours) {
        this.mainHours = mainHours;
    }

    public double getMainExtraHours() {
        return mainExtraHours;
    }

    public void setMainExtraHours(double mainExtraHours) {
        this.mainExtraHours = mainExtraHours;
    }

    public double getCompletionistHours() {
        return completionistHours;
    }

    public void setCompletionistHours(double completionistHours) {
        this.completionistHours = completionistHours;
    }

    public double getCombinedHours() {
        return combinedHours;
    }

    public void setCombinedHours(double combinedHours) {
        this.combinedHours = combinedHours;
    }

    public double getPolled() {
        return polled;
    }

    public void setPolled(double polled) {
        this.polled = polled;
    }

    public double getRatedPercent() {
        return ratedPercent;
    }

    public void setRatedPercent(double ratedPercent) {
        this.ratedPercent = ratedPercent;
    }

    public double getBacklogCount() {
        return backlogCount;
    }

    public void setBacklogCount(double backlogCount) {
        this.backlogCount = backlogCount;
    }

    public double getPlaying() {
        return playing;
    }

    public void setPlaying(double playing) {
        this.playing = playing;
    }

    public double getSpeedruns() {
        return speedruns;
    }

    public void setSpeedruns(double speedruns) {
        this.speedruns = speedruns;
    }

    public double getRetired() {
        return retired;
    }

    public void setRetired(double retired) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
