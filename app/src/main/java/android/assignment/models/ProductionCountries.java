package android.assignment.models;

import com.google.gson.annotations.SerializedName;

public class ProductionCountries {
    @SerializedName("iso_3166_1")
    private String isoCountry;

    private String name;

    public String getIsoCountry()
    {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry)
    {
        this.isoCountry = isoCountry;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [isoCountry = "+ isoCountry +", name = "+name+"]";
    }
}
