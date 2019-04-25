package android.assignment.models;

import com.google.gson.annotations.SerializedName;

public class MovieListing {

    @SerializedName("item_count")

    private String itemCount;

    @SerializedName("list_type")
    private String listType;

    private String name;

    private String description;

    @SerializedName("favorite_count")
    private String favorite_count;

    private String id;

    @SerializedName("iso_639_1")
    private String iso6391;

    @SerializedName("poster_path")
    private String posterPath;

    public String getItemCount()
    {
        return itemCount;
    }

    public void setItemCount(String itemCount)
    {
        this.itemCount = itemCount;
    }

    public String getListType()
    {
        return listType;
    }

    public void setListType(String listType)
    {
        this.listType = listType;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getFavorite_count ()
    {
        return favorite_count;
    }

    public void setFavorite_count (String favorite_count)
    {
        this.favorite_count = favorite_count;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIso6391()
    {
        return iso6391;
    }

    public void setIso6391(String iso6391)
    {
        this.iso6391 = iso6391;
    }

    public String getPosterPath ()
    {
        return posterPath;
    }

    public void setPosterPath (String posterPath)
    {
        this.posterPath = posterPath;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [itemCount = "+ itemCount +", listType = "+ listType +", name = "+name+", description = "+description+", favorite_count = "+favorite_count+", id = "+id+", iso6391 = "+ iso6391 +", posterPath = "+ posterPath +"]";
    }
}
