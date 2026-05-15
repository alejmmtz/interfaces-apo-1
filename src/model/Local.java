package model;

public abstract class Local {

    private String userId;
    private boolean available;

    public Local(String userId, boolean available) {
        this.userId = userId;
        this.available = available;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
