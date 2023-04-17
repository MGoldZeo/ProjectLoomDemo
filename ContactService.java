import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;

/**
 * The runner class that allows for multiple searches.
 * The input may be part of the first or last name.
 */

public class ContactService
{
    public static void searchForContacts(String[] namesToSearch)
    {
    }
    public static void main(String[] args) throws Exception
    {
        ContactDB cdb = new ContactDB("/Users/mgoldzeo/IdeaProjects/untitled4/src/export.csv");
        while (true)
        {
            // Used to keep track of execution time
            long starttime = System.currentTimeMillis();
            ArrayList<Future<Contact[]>> futures = new ArrayList<Future<Contact[]>>();
            List<String> searchargs;
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter search arguments separated by commas:");
            String arg = sc.nextLine();
            String[] elements = arg.split(",");
            searchForContacts(elements);
            searchargs = Arrays.asList(elements);
            for (String searcharg : searchargs)
            {
                //Storing the result of each search as a future
                futures.add(cdb.SearchForContacts(searcharg));
            }
            for (Future<Contact[]> future : futures)
            {
                //get method used to block calls until results are available
                Contact[] contacts = future.get();
                System.out.println("\nSearch for " + searchargs.get(futures.indexOf(future)) + " yielded " + contacts.length + " results.");
                for(Contact contact : contacts) {
                    //prints each contact found as a result of the search
                    System.out.println(contact);
                }
            }
            long endtime = System.currentTimeMillis();
            //prints execution time
            System.out.println("The execution completed in " + (endtime - starttime) + " milliseconds");
            break;
        }
    }
}
