package api;

public class SortListToYear {
    public Integer id;
    public String name;
    public Integer year;
    public String color;
    public String pantone_value;

    public SortListToYear () {
        super();
    }

    public SortListToYear(Integer id, String name, Integer year, String color, String pantone_value) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.color = color;
        this.pantone_value = pantone_value;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getPantone_value() {
        return pantone_value;
    }
}
