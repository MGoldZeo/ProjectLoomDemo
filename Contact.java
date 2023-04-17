public class Contact
{
/**
 * Represents the data of a single contact
 */
    private String firstname;
    private String lastname;
    private String email;
    private int age;
    private String street;
    private String city;
    private String state;
    private int zip;
    public Contact(String fn, String ln, String em, int ag, String str, String ci, String sta, int zi)
    {
        firstname = fn;
        lastname = ln;
        email = em;
        age = ag;
        street = str;
        city = ci;
        state = sta;
        zip = zi;
    }

    @Override
    public String toString()
    {
        return this.firstname + " " + this.lastname + " - " + this.email;
    }

    public boolean matches(String partname)
    /**
     * Checks if the given contact's name matches a search result
     */
    {
        String fullname = this.firstname.toLowerCase() + " " + this.lastname.toLowerCase();
            return fullname.contains(partname.toLowerCase());
        }
}