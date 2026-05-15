package model;

import java.util.ArrayList;

public class Industrial extends Local implements Insurable {

    private ArrayList<String> products;

    public Industrial(String userId, boolean available, ArrayList<String> products) {
        super(userId, available);
        this.products = products == null ? new ArrayList<>() : new ArrayList<>(products);
    }

    public ArrayList<String> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products == null ? new ArrayList<>() : new ArrayList<>(products);
    }

    public int countProduct(String productName) {
        int count = 0;

        for (String product : products) {
            if (product.equalsIgnoreCase(productName)) {
                count++;
            }
        }

        return count;
    }

    public void addProduct(String product) {
        products.add(product);
    }

    public boolean removeProduct(String product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equalsIgnoreCase(product)) {
                products.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public double insuranceValue() {
        return products.size() * 1500;
    }
}
