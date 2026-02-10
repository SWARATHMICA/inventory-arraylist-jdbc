package inventory;

public class Bolt extends Item {

    public Bolt(String type,String name, int count, double price) {
        super(type,name, count, price);
    }

    @Override
    public double calculateValue() {
        return count * price;
    }
}
