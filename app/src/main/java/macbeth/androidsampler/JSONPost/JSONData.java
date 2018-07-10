package macbeth.androidsampler.JSONPost;

import java.util.List;

public class JSONData {
    private String query = null;
    private List<String> age_certifications = null;
    private List<String> content_types = null;
    private List<String> presentation_types = null;
    private List<String> providers = null;
    private List<String> genres = null;
    private Integer release_year_from = null;
    private Integer release_year_until = null;
    private List<String> monetization_types = null;
    private Integer min_price = null;
    private Integer max_price = null;
    private Boolean nationwide_cinema_releases_only = null;
    private String languages = null;
    private List<String> scoring_filter_types = null;

    public void setQuery(String query) {
        this.query = query;
    }

    public void setAge_certifications(List<String> age_certifications) {
        this.age_certifications = age_certifications;
    }

    public void setContent_types(List<String> content_types) {
        this.content_types = content_types;
    }

    public void setPresentation_types(List<String> presentation_types) {
        this.presentation_types = presentation_types;
    }

    public void setProviders(List<String> providers) {
        this.providers = providers;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setRelease_year_from(Integer release_year_from) {
        this.release_year_from = release_year_from;
    }

    public void setRelease_year_until(Integer release_year_until) {
        this.release_year_until = release_year_until;
    }

    public void setMonetization_types(List<String> monetization_types) {
        this.monetization_types = monetization_types;
    }

    public void setMin_price(Integer min_price) {
        this.min_price = min_price;
    }

    public void setMax_price(Integer max_price) {
        this.max_price = max_price;
    }

    public void setNationwide_cinema_releases_only(Boolean nationwide_cinema_releases_only) {
        this.nationwide_cinema_releases_only = nationwide_cinema_releases_only;
    }
}
