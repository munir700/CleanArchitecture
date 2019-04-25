package android.assignment.models;

import com.google.gson.annotations.SerializedName;

public class ProductionCompanies {
    @SerializedName("logo_path")
    private String logoPath;

    private String name;

    private String id;
    @SerializedName("origin_country")
    private String originCountry;

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    @Override
    public String toString() {
        return "ClassPojo [logoPath = " + logoPath + ", name = " + name + ", id = " + id + ", originCountry = " + originCountry + "]";
    }

}
