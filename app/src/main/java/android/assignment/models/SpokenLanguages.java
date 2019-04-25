package android.assignment.models;

import com.google.gson.annotations.SerializedName;

public class SpokenLanguages {
    private String name;
    @SerializedName("iso_639_1")
    private String isoLang;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoLang() {
        return isoLang;
    }

    public void setIsoLang(String isoLang) {
        this.isoLang = isoLang;
    }

    @Override
    public String toString() {
        return "ClassPojo [name = " + name + ", isoLang = " + isoLang + "]";
    }
}
