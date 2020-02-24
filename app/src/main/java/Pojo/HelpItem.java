package Pojo;

import java.util.Objects;

public class HelpItem {

    private String title;
    private String mainText;

    public HelpItem(String title, String mainText) {
        this.title = title;
        this.mainText = mainText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelpItem helpItem = (HelpItem) o;
        return title.equals(helpItem.title) &&
                mainText.equals(helpItem.mainText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, mainText);
    }
}
