package nguyenpham.com.model;

import java.io.Serializable;

/**
 * Created by Quang Nguyen on 8/5/2017.
 */

public class Music implements Serializable {

    private String song;
    private String singer;
    private String filePath;

    public Music() {
    }

    public Music(String song, String singer, String filePath) {
        this.song = song;
        this.singer = singer;
        this.filePath = filePath;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
