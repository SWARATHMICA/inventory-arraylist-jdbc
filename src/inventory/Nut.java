package inventory;

public class Nut extends Item {

    public Nut(String type, String name, int count, double price) {
        super(type, name, count, price);
    }
    @Override
    public double calculateValue() {
        return count * price;
    }
}
