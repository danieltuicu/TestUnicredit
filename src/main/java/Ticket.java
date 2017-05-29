public class Ticket {

    private final int id;
    private String event;
    private static int idGnerator = 100;

    public Ticket(String event) {
        this.id = idGnerator;
        idGnerator++;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

}
