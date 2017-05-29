import com.google.gson.Gson;

import java.util.*;

import static spark.Spark.*;

public class Main {

    private static final String password = "pass123";

    public static void main(String[] args) {
        Gson gson = new Gson();
        Map<Integer, Ticket> tickets = new HashMap<>();
        LinkedList<Ticket> canceledTickets = new LinkedList<>();
        ArrayList<String> events = new ArrayList<>(
                Arrays.asList("Metallica", "Queen", "Slipknot", "Nightwish", "Dragonforce"));

        // Buy a ticket for the <name> event.
        post("/buy_ticket/:name", (request, response) -> {
            Ticket ticket;

            if (!events.contains(request.params("name"))) {
                return "There is no " + request.params("name") + " event registered.";
            }

            if (!canceledTickets.isEmpty()) {
                ticket = canceledTickets.poll();
                ticket.setEvent(request.params("name"));
                tickets.put(ticket.getId(), ticket);
            } else {
                ticket = new Ticket(request.params("name"));
                tickets.put(ticket.getId(), ticket);
            }
            return "Thank you for your purchasing a ticket for the " +
                    request.params("name") + " event" +
                    "\nYour ticket ID is " + ticket.getId() + ". Please keep it somewhere safe.";
        });

        // Lists the currently available events
        get("/list_events", (request, response) -> gson.toJson(events));

        // Prints the details of a ticket in json format
        get("/ticket_details/:id", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));

            if (tickets.containsKey(id)) {
                return gson.toJson(tickets.get(id));
            } else {
                return request.params("id") + " is not registered in our database.";
            }
        });

        // Cancels a ticket
        post("/cancel_ticket/:id", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            if (tickets.containsKey(id)) {
                Ticket ticket = tickets.get(id);
                tickets.remove(id);
                canceledTickets.add(ticket);

                return "Ticket nr. " + request.params("id") + " has been successfully canceled.";
            } else {
                return request.params("id") + " is not registered in our database.";
            }
        });

        // Lists the current tickets in json format. Requires password.
        get("/list_tickets/:pass", (request, response) -> {
            if (request.params("pass").equals(password)) {
                return gson.toJson(tickets);
            }
            return "Password incorrect. You don't have access to this database.";

        });

        // Adds an event to the list. Requires a password.
        post("/add_event/:pass/:name", (request, response) -> {
            if (request.params("pass").equals(password)) {
                if(!events.contains(request.params("name"))) {
                    events.add(request.params("name"));
                    return "Entry for event " + request.params("name") + " successfully added.";
                } else {
                    return request.params("name") + " event already registered.";
                }
            }
            return "Password incorrect. You don't have access to this command.";
        });

        // Removes an event to the list. Requires a password.
        post("/remove_event/:pass/:name", (request, response) -> {
            if (request.params("pass").equals(password)) {
                if(events.contains(request.params("name"))) {
                    events.remove(request.params("name"));
                    return "Entry for event " + request.params("name") + " successfully removed.";
                } else {
                    return "Entry for event " + request.params("name") + " not found.";
                }
            }
            return "Password incorrect. You don't have access to this command.";
        });
    }
}
