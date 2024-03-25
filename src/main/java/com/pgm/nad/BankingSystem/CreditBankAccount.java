package com.pgm.nad.BankingSystem;

public class CreditBankAccount extends BankAccount {
    private double balance;
    private String idAccount;
    private String type = "credit";
    private String name;
    private String surname;
    private String passport;
    private String address;
    private long timeStart;
    private long timeFinish;

    private double commision;
    private int limit;
    private boolean confirmed = false;

    public CreditBankAccount (String type, String idAccount, double commision, int limit) {
        this.type = "credit";
        this.idAccount = idAccount;
        this.commision = commision;
        this.limit = limit;
        // bilder
    }

    public void topUp(double sum) {
        balance = getBalance();
        if (sum >= 0) {
            balance += sum;
        }
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
        balance = getBalance();
        if (sum <= balance && sum >= 0) {
            balance -= sum;
            return true;
        } else if (sum  <= balance + limit && sum >= 0) {
            if (balance > 0){
                timeStart = System.currentTimeMillis();
            }
            balance -= sum;
            return true;
        }
        return false;
    }

    public double getBalance() {
        timeFinish = System.currentTimeMillis();
        long temp = (timeFinish - timeStart) / (600000);
        return balance * Math.pow(commision, temp); // комиссия добавляется каждые 10 минут
    }

    public static class CreditBankAccountBuilder {
        private double balance;
        private String idAccount;
        private String type;
        private String name;
        private String surname;
        private String passport;
        private String address;
        private boolean confirmed;


    }
}
