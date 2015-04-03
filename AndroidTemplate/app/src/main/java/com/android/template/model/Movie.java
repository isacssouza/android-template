package com.android.template.model;

/**
 * Model for movies.
 *
 * Created by isacssouza on 3/10/15.
 */
public class Movie {
    private String Title;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String Type;
    private String Response;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String metascore) {
        Metascore = metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!imdbID.equals(movie.imdbID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return imdbID.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("Title='").append(Title).append('\'');
        sb.append(", Year='").append(Year).append('\'');
        sb.append(", Rated='").append(Rated).append('\'');
        sb.append(", Released='").append(Released).append('\'');
        sb.append(", Runtime='").append(Runtime).append('\'');
        sb.append(", Genre='").append(Genre).append('\'');
        sb.append(", Director='").append(Director).append('\'');
        sb.append(", Writer='").append(Writer).append('\'');
        sb.append(", Actors='").append(Actors).append('\'');
        sb.append(", Plot='").append(Plot).append('\'');
        sb.append(", Language='").append(Language).append('\'');
        sb.append(", Country='").append(Country).append('\'');
        sb.append(", Awards='").append(Awards).append('\'');
        sb.append(", Poster='").append(Poster).append('\'');
        sb.append(", Metascore='").append(Metascore).append('\'');
        sb.append(", imdbRating='").append(imdbRating).append('\'');
        sb.append(", imdbVotes='").append(imdbVotes).append('\'');
        sb.append(", imdbID='").append(imdbID).append('\'');
        sb.append(", Type='").append(Type).append('\'');
        sb.append(", Response='").append(Response).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
