import java.util.ArrayList;
import java.util.HashMap;

public class Client {//все что здесь написано, храниться на сервере
    private String id;
    private HashMap<String, ArrayList<ArrayList<String>>> bankApps;

    public Client(String id) {this.id = id;}

    public void createBankAccount(String bank, String type) {} // bank - это название банка

    public boolean transfer(String accountFrom, String accountTo, int sum) {return true;}

    public boolean topUp(String account, int sum) {return true;}

    public boolean withdrow(String account, int sum) {return true;}

    public String toastmaster(String request) {return "";} // последний метод указания места, где в приложении находится клиент

    public String getMainCatalog() {return "";}
}
