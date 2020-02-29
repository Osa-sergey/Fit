package Pojo;

import java.util.Objects;

import Utils.SupportUtils;

public class TargetItem {
    private boolean expand;


    private String title;
    private int starsCount;
    private int progress;
    private long spentTime;
    private int maxTrainingsQt;
    private int curTrainingsQt;

    public TargetItem(boolean expand, String title, int starsCount, int progress, long spentTime, int maxTrainingsQt, int curTrainingsQt) {
        this.expand = expand;
        this.title = title;
        this.starsCount = starsCount;
        this.progress = progress;
        this.spentTime = spentTime;
        this.maxTrainingsQt = maxTrainingsQt;
        this.curTrainingsQt = curTrainingsQt;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getProgressStr() {
        return progress+"%";
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public String getTrainings() {
        return curTrainingsQt + " из " + maxTrainingsQt;
    }

    public String getSpentTime() {
        return SupportUtils.getSpentTime(spentTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetItem that = (TargetItem) o;
        return expand == that.expand &&
                starsCount == that.starsCount &&
                progress == that.progress &&
                spentTime == that.spentTime &&
                maxTrainingsQt == that.maxTrainingsQt &&
                curTrainingsQt == that.curTrainingsQt &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expand, title, starsCount, progress, spentTime, maxTrainingsQt, curTrainingsQt);
    }
}
