package de.interhyp.ing.hackathon.calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.google.GoogleJsonTokenExtractor;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import de.interhyp.ing.hackathon.domain.Calendar;
import de.interhyp.ing.hackathon.domain.Location;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleHandler
{
    private static final String APPLICATION_NAME =
        "Interhyp4Hackathon";

    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/google-calendar");

    private static FileDataStoreFactory DATA_STORE_FACTORY;

    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    private static HttpTransport HTTP_TRANSPORT;

    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static Credential authorize() throws IOException {
        InputStream in =
            Quickstart.class.getResourceAsStream("/client_id.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver.Builder().setPort(62856).build()).authorize("user");
        System.out.println(
            "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public static com.google.api.services.calendar.Calendar
    getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    public List<Calendar> requestCalendarEvents (String from, String till) throws IOException
    {
        List<Calendar> result = new ArrayList<>();
        com.google.api.services.calendar.Calendar service =
            getCalendarService();

        DateTime fromDt = new DateTime (from);
        DateTime tillDt = new DateTime (till);
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
            .setMaxResults(1000)
            .setTimeMin(fromDt)
            .setTimeMax(tillDt)
            .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                Calendar element = new Calendar ();
                element.setStart(ZonedDateTime.parse(event.getStart().getDateTime().toStringRfc3339()));
                element.setEnd(ZonedDateTime.parse(event.getEnd().getDateTime().toStringRfc3339()));

                if (event.getLocation() != null)
                {
                    Location location = requestLocation(event.getLocation());
                    element.setLocation(location);
                }
                element.setTitle(event.getSummary());
                result.add (element);
            }
        }

        return result;
    }

    Location requestLocation (String locationName)
    {
        Location result = new Location ();
        try {
            String response = sendGet ("https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(locationName) + "&key=AIzaSyC72kYRChCmlzea8w4PceCInmmz7neRJkg");
            ObjectMapper objectMapper = new ObjectMapper();
            GoogleLocation googleLocation = objectMapper.readValue(response, GoogleLocation.class);
            if (googleLocation.getResults().size() == 1) {
                String street = "";
                String houseNo = "";
                String country = "";
                for (AddressComponent component : googleLocation.getResults().get(0).getAddressComponents()) {
                    if (component.getTypes().contains("locality"))
                    {
                        result.setCity(component.getLongName());
                    }
                    else if (component.getTypes().contains("country"))
                    {
                        country = component.getLongName();
                    }
                    else if (component.getTypes().contains("postal_code"))
                    {
                        result.setPostalCode(component.getLongName());
                    }
                    else if (component.getTypes().contains("route"))
                    {
                        street = component.getLongName();
                    }
                    else if (component.getTypes().contains("street_number"))
                    {
                        houseNo = component.getLongName();
                    }
                }
                result.setStateProvince(country);
                result.setStreetAddress(street + " " + houseNo);
                result.setLatitude("" + googleLocation.getResults().get(0).getGeometry().getLocation().getLat());
                result.setLongitude("" + googleLocation.getResults().get(0).getGeometry().getLocation().getLng());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
