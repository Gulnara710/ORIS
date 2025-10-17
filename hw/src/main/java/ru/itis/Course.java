package ru.itis;

public class Course {
    private Long id;
    private String title;
    private String description;
    private String details;
    private String image;
    private double price;

    public Course(Long id, String title, String description,String details, String image, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.details = details;
        this.image = image;
        this.price = price;
    }

    public Course(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + price;
    }
}
