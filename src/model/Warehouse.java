package model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Warehouse {

    public static final int MAX = 20;

    private final String name;
    private final Local[] locals;
    private final DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    public Warehouse(String name, int localCount) {
        this.name = name;
        if (localCount > MAX) {
            localCount = MAX;
        }
        this.locals = new Local[localCount];

        createDefaultLocals();
    }

    private void createDefaultLocals() {
        for (int i = 0; i < locals.length; i++) {
            int num = i + 1;
            if (num <= 10 && num % 2 != 0) {
                locals[i] = new Industrial("", true, new ArrayList<>());
            } else if (num <= 10 && num % 2 == 0) {
                locals[i] = new Special("", true, 0);
            } else {
                locals[i] = new Personal("", true, false);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getAvailableLocals() {
        int count = 0;

        for (Local local : locals) {
            if (local.isAvailable())
                count++;
        }
        return count;
    }

    public String leasingLocal(String userId, ArrayList<String> products) {
        if (findLocalByUser(userId) != null)
            return "El usuario ya tiene un local asignado.";

        int index = findAvailable(Industrial.class);
        if (index == -1)
            return "No hay locales industriales disponibles.";

        Industrial local = (Industrial) locals[index];
        local.setUserId(userId);
        local.setAvailable(false);
        local.setProducts(products);
        return "Se alquilo el local industrial numero " + (index + 1) + " al usuario " + userId + ".";
    }

    public String leasingLocal(String userId, boolean insurance) {
        if (findLocalByUser(userId) != null)
            return "El usuario ya tiene un local asignado.";

        int index = findAvailable(Personal.class);
        if (index == -1)
            return "No hay locales de persona natural disponibles.";

        Personal local = (Personal) locals[index];
        local.setUserId(userId);
        local.setAvailable(false);
        local.setInsurance(insurance);
        return "Se alquilo el local de persona natural numero " + (index + 1) + " al usuario " + userId + ".";
    }

    public String leasingLocal(String userId, double totalValue) {
        if (findLocalByUser(userId) != null)
            return "El usuario ya tiene un local asignado.";

        int index = findAvailable(Special.class);
        if (index == -1)
            return "No hay locales de objetos especiales disponibles.";

        Special local = (Special) locals[index];
        local.setUserId(userId);
        local.setAvailable(false);
        local.setTotalValue(totalValue);
        return "Se alquilo el local de objetos especiales numero " + (index + 1) + " al usuario " + userId + ".";
    }

    public String countProduct(String userId, String productName) {
        Local local = findLocalByUser(userId);
        if (!(local instanceof Industrial))
            return "El usuario no tiene un local industrial asignado.";

        int count = ((Industrial) local).countProduct(productName);
        return "El producto " + productName + " aparece " + count + " vez/veces en el local del usuario " + userId
                + ".";
    }

    public String insuranceValue(String userId) {
        Local local = findLocalByUser(userId);
        if (!(local instanceof Insurable))
            return "El usuario no tiene un local industrial o de objetos especiales asignado.";

        double value = ((Insurable) local).insuranceValue();
        return "El valor asegurado del local del usuario " + userId + " es " + moneyFormat.format(value) + ".";
    }

    public String addProduct(String userId, String productName) {
        Local local = findLocalByUser(userId);
        if (!(local instanceof Industrial))
            return "El usuario no tiene un local industrial asignado.";

        ((Industrial) local).addProduct(productName);
        return "Se agrego el producto " + productName + " al local del usuario " + userId + ".";
    }

    public String removeProduct(String userId, String productName) {
        Local local = findLocalByUser(userId);
        if (!(local instanceof Industrial))
            return "El usuario no tiene un local industrial asignado.";

        boolean removed = ((Industrial) local).removeProduct(productName);
        if (!removed)
            return "El producto " + productName + " no se encuentra en el local del usuario " + userId + ".";

        return "Se removio una unidad del producto " + productName + " del local del usuario " + userId + ".";
    }

    private int findAvailable(Class<? extends Local> type) {
        for (int i = 0; i < locals.length; i++) {
            if (type.isInstance(locals[i]) && locals[i].isAvailable())
                return i;
        }
        return -1;
    }

    private Local findLocalByUser(String userId) {
        for (Local local : locals) {
            if (!local.isAvailable() && local.getUserId().equalsIgnoreCase(userId))
                return local;
        }
        return null;
    }
}