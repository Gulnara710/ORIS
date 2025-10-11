package ru.itis;

public class Course {
    private String title;
    private String description;
    private double price;

    public Course(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + price;
    }
}
