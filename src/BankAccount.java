abstract class BankAccount {
    private double balance;
    private String idAccount;
    private String type;
    private String name;
    private String surname;
    private String passport;
    private String address;
    private boolean confirmed;


    public double getBalance() {
        return balance;
    }

    public String getId() {
        return idAccount;
    }

    public String getType() {
        return type;
    }

    public void topUp(double sum) {}

    public boolean transfer(double sum, String id) {return true;}

    public boolean withdrow(double sum) {return true;}
    public String getInfo(){return "";}
}

