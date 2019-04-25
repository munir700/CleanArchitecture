package android.assignment.models;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("imdb_id")
    private String imdbId;

    private String video;

    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    private String revenue;

    private Genres[] genres;

    private String popularity;

    @SerializedName("production_countries")
    private ProductionCountries[] productionCountries;

    private String id;

    @SerializedName("vote_count")
    private String voteCount;

    private String budget;

    private String overview;

    @SerializedName("original_title")
    private String originalTitle;

    private String runtime;

    private String poster_path;

    @SerializedName("spoken_languages")
    private SpokenLanguages[] spokenLanguages;

    @SerializedName("production_companies")
    private ProductionCompanies[] productionCompanies;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("belongs_to_collection")
    private String belongsToCollection;

    private String tagline;

    private String adult;

    private String homepage;

    private String status;

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public Genres[] getGenres() {
        return genres;
    }

    public void setGenres(Genres[] genres) {
        this.genres = genres;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public ProductionCountries[] getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(ProductionCountries[] productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public SpokenLanguages[] getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(SpokenLanguages[] spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public ProductionCompanies[] getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(ProductionCompanies[] productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongs_to_collection(String belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [originalLanguage = " + originalLanguage + ", imdbId = " + imdbId + ", video = " + video + ", title = " + title + ", backdropPath = " + backdropPath + ", revenue = " + revenue + ", genres = " + genres + ", popularity = " + popularity + ", productionCountries = " + productionCountries + ", id = " + id + ", voteCount = " + voteCount + ", budget = " + budget + ", overview = " + overview + ", originalTitle = " + originalTitle + ", runtime = " + runtime + ", poster_path = " + poster_path + ", spokenLanguages = " + spokenLanguages + ", productionCompanies = " + productionCompanies + ", releaseDate = " + releaseDate + ", voteAverage = " + voteAverage + ", belongsToCollection = " + belongsToCollection + ", tagline = " + tagline + ", adult = " + adult + ", homepage = " + homepage + ", status = " + status + "]";
    }
}
