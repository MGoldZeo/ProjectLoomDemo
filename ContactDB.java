import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

public class ContactDB
{
    /**
     * Used to build a simple database that collects and manipulates contacts
     */
    private String filename;
    private static final ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();
    //above is project loom, below is previous method
    //private static final ExecutorService exec = Executors.newFixedThreadPool(5);

    public ContactDB(String filn)
    {
        this.filename = filn;
    }

    /**
     * A purposefully inefficient search in order to stall the program
     * By doing this, we can better test the throughput
     * @param nameToSearch which is a single search
     * @return futures, which are outputs of asynchronous computation.
     * Calling the get method on a future will stall the future so
     * that it blocks calls until results are available. The futures
     * return matching contacts
     */

    Future<Contact[]> SearchForContacts(String nameToSearch)
    {
        return exec.submit(new SearchTask(nameToSearch, filename));
    }

    private static class SearchTask implements Callable<Contact[]>
    {
        /**
         * Formats the function as a task in order to have it be executed multiple times
         */
        String name;
        String file;
        public SearchTask(String nm, String filnm)
        {
            name = nm;
            file = filnm;
        }
        public Contact[] call() throws Exception
        {
            ArrayList<Contact> searchResult = new ArrayList<Contact>();
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); // first line contains headers so ignore it.
            String line;
            while((line = br.readLine()) != null)
            {
                Scanner sc = new Scanner(line);
                sc.useDelimiter(",");
                Contact contact = new Contact(sc.next(), sc.next(), sc.next(), sc.nextInt(), sc.next(), sc.next(), sc.next(), sc.nextInt());
                if (contact.matches(name))
                {
                    searchResult.add(contact);
                }
            }
            return searchResult.toArray(new Contact[0]);
        }
    }

}
