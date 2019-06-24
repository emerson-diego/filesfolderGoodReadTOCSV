public class Book {

    private String description;
    private String keys;
    private String rating;

    public Book(String description, String keys, String rating) {
        this.description = description;
        this.keys = keys;
        this.rating = rating;
    }

    public Book(String description, String keys) {
        this.description = description;
        this.keys = keys;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}