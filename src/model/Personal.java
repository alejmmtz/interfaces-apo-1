package model;

public class Personal extends Local {

    private boolean insurance;

    public Personal(String userId, boolean available, boolean insurance) {
        super(userId, available);
        this.insurance = insurance;
    }

    public boolean hasInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }
}
