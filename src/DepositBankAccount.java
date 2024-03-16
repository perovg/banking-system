public class DepositBankAccount extends BankAccount  {
    private double balance;
    private String id;
    private String type = "deposit";
    private String name;
    private String surname;
    private String passport;
    private String address;
    private boolean confirmed = false;
    private long time;
    private long timeStart;
    private long timeFinish;


    public DepositBankAccount (String id, String type, long time)  {
        this.id = id;
        this.type = type;
        this.time = time;
        //bilder
    }


    public void topUp(double sum) {
        if (balance == 0) {
            timeStart = System.currentTimeMillis();
        }
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
        timeFinish = System.currentTimeMillis();
        if (timeFinish - timeStart >= time) {
            if (sum <= balance && sum >= 0) {
                balance -= sum;
                return true;
            }
        }
        return false;
    }
}
