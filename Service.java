

public class Service {
    private String name;
    private int duration;
    private Integer price;
    
    public Service(String name, int duration, Integer price) {
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public int getDuration() {
        return this.duration;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d minutes\t$%d", name, duration, price);
    }




}
