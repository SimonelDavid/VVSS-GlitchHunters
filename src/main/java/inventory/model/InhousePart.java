package inventory.model;


public class InhousePart extends Part {

    // Declare fields
    private int machineId;

    // Constructor
    public InhousePart(int partId, String name, double price, int stockCapacity, int minCapacity, int maxCapacity, int machineId) {
        super(partId, name, price, stockCapacity, minCapacity, maxCapacity);
        this.machineId = machineId;
    }
    
    // Getter
    public int getMachineId() {
        return machineId;
    }
    
    // Setter
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    @Override
    public String toString() {
        return "I,"+super.toString()+","+getMachineId();
    }
}


