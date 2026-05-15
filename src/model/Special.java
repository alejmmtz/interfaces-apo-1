package model;

public class Special extends Local implements Insurable {

    private double totalValue;

    public Special(String userId, boolean available, double totalValue) {
        super(userId, available);
        this.totalValue = totalValue;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public double insuranceValue() {
        return totalValue * 0.8;
    }
}
