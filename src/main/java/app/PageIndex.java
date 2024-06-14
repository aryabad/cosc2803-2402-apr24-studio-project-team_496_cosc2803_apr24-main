package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Waste Stopper's Mission</a>
                <a href='page2A.html'>Sub Task 2.A</a>
                <a href='page2B.html'>Sub Task 2.B</a>
                <a href='page3A.html'>Sub Task 3.A</a>
                <a href='page3B.html'>Sub Task 3.B</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>
                    <img src='logo.png' class='top-image' alt='RMIT logo' height='75'>
                    Waste Stoppers!!!
                </h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <div class="leftalign">
            <!-- add image of welcome page -->

            
            <p>Welcome to Waste Stoppers! Waste stoppers is the one stop shop for finding every and all information about food wastage throughout the entire world. Throughout our website, we invite users to investigate the different countries and regions of the world to further their knowledge of food waste and to help us tackle the issue together. Below we have all of our subpages listed that we invite you to use to investigate further.</p>

            <p> Feel free to click <a href="PageMission.java"> here </a> to take you to our mission statement page! On this page you can find the mission statement for Waste Stoppers, as well as be introduced to some of our wonderful users! This page will help you get a greater understanding of the purpose of our website and what we aim to achieve!</p>
            
            <div class="rightalign"> 
                image
            </div>
            <p> Use <a href="PageST2B"> this link </a> to take you to our page that helps you investigate food loss by country! This page will let you explore detailed information about the food waste of different countries across the world starting from the mid 1900's until recent years. </p>

            <div class="leftalign">
                image
            </div>
            <p> Use <a href="PageST2A"> this link</a> to take you to our page that helps you investigate food loss by different food groups! On this page you'll be able to search through our exstensive database that logs food wastage based on the loss by different food types and groups!  </p>

            <div class="rightalign"
                image
            </div>
            <p> use <a href="PageST3A"> this link </a> to take you to our page that helps you investigate different locations around the world that have similar food waste. This page will allow you a detailed look into the different loss percentages of countries and find what other regions in the world are the most similar. </p>

            <div class="leftalign">
                image
            </div
            <p> use  Use <a href="PageST3B"> this link</a> to take you to the page that will help you investigate the related losses of different food groups and commodoties. This page will help you understand the different food commodities and the types of loss usually encountered when dealing and storing these food groups. </p>
            """;

        // Get the ArrayList of Strings of all countries
        ArrayList<String> countryNames = getAllCountries();

        // Add HTML for the country list
        html = html + "<h1>All Countries in the food loss database</h1>" + "<ul>";

        // Finally we can print out all of the countries
        for (String name : countryNames) {
            html = html + "<li>" + name + "</li>";
        }

        // Finish the List HTML
        html = html + "</ul>";

        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
            <div class='footer'>
                <p>COSC2803 - Studio Project Starter Code (Apr24)</p>
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }


    /**
     * Get the names of the countries in the database.
     */
    public ArrayList<String> getAllCountries() {
        // Create the ArrayList of String objects to return
        ArrayList<String> countries = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String countryName  = results.getString("countryName");

                // Add the country object to the array
                countries.add(countryName);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
                //e.printStackTrace();
            }
        }

        // Finally we return all of the countries
        return countries;
    }
}
