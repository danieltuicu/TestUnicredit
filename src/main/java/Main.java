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
                response.status(400);
                return gson.toJson(new Error("Event not registered."));
            }

            if (!canceledTickets.isEmpty()) {
                ticket = canceledTickets.poll();
                ticket.setEvent(request.params("name"));
                tickets.put(ticket.getId(), ticket);
            } else {
                ticket = new Ticket(request.params("name"));
                tickets.put(ticket.getId(), ticket);
            }
            return gson.toJson(ticket);
        });

        // Lists the currently available events
        get("/list_events", (request, response) -> gson.toJson(events));

        // Prints the details of a ticket in json format
        get("/ticket_details/:id", (request, response) -> {
            int id;
            try {
                id = Integer.parseInt(request.params("id"));
            } catch (NumberFormatException e) {
                id = -1;
            }

            if (tickets.containsKey(id)) {
                return gson.toJson(tickets.get(id));
            } else {
                response.status(400);
                return gson.toJson(new Error("Ticket not registered."));
            }
        });

        // Cancels a ticket
        post("/cancel_ticket/:id", (request, response) -> {
            int id;
            try {
                id = Integer.parseInt(request.params("id"));
            } catch (NumberFormatException e) {
                id = -1;
            }

            if (tickets.containsKey(id)) {
                Ticket ticket = tickets.get(id);
                tickets.remove(id);
                canceledTickets.add(ticket);

                return gson.toJson(new Response("Ticket successfully canceled"));
            } else {
                response.status(400);
                return gson.toJson(new Error("Ticket not registered."));
            }
        });

        // Lists the current tickets in json format. Requires password.
        get("/list_tickets/:pass", (request, response) -> {
            if (request.params("pass").equals(password)) {
                return gson.toJson(tickets);
            }
            response.status(401);
            return gson.toJson(new Error("Password incorrect."));

        });

        // Adds an event to the list. Requires a password.
        post("/add_event/:pass/:name", (request, response) -> {
            if (request.params("pass").equals(password)) {
                if (!events.contains(request.params("name"))) {
                    events.add(request.params("name"));
                    return gson.toJson(new Response(request.params("name")));
                } else {
                    response.status(409);
                    return gson.toJson(new Error("Event already registered."));
                }
            }
            response.status(401);
            return gson.toJson(new Error("Password incorrect"));
        });

        // Removes an event to the list. Requires a password.
        delete("/remove_event/:pass/:name", (request, response) -> {
            if (request.params("pass").equals(password)) {
                if (events.contains(request.params("name"))) {
                    events.remove(request.params("name"));
                    return gson.toJson(new Response("Successfully remvoed."));
                } else {
                    response.status(400);
                    return gson.toJson(new Error("Event not found."));
                }
            }
            response.status(401);
            return gson.toJson(new Error("Password incorrect"));
        });
    }
}
