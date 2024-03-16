public class DebetBankAccount extends BankAccount {
    private double balance;
    private String id;
    private String type = "debet";
    private String name;
    private String surname;
    private String passport;
    private String address;
    private boolean confirmed = false;

    public DebetBankAccount (String type, String id) {
        this.type = "debet";
        this.id = id;
        // bilder
    }
    @Override
    public void topUp(double sum) {
        if (sum >= 0) balance += sum;
    }

    public boolean transfer(double sum, String toId) {
        BankAccount to = Server.accounts.get(toId);
        if (this.withdrow(sum)) {
            to.topUp(sum);
            return true;
        }
        return false;
    }

    public boolean withdrow(double sum) {
        if (sum <= balance && sum >= 0) {
            balance -= sum;
            return true;
        } else {
            return false;
        }
    }

    public static class DebetBankAccountBuilder {
        private double balance;
        private String id;
        private String type;
        private String name;
        private String surname;
        private String passport;
        private String address;
        private boolean confirmed;
    }
}
