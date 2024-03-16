public class Bank {
    private String id;//id т к hash map<String, Bank> банков
    private String name;
    private int limit;
    private int commission;
    private long time;


    public String getId() {return id;}
    public int getLimit() {return limit;}
    public int getCommission() {return commission;}
    public long getTime() {return time;}

    public Bank(String id, String name, int limit, int commission){
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.commission = commission;
    }
}
