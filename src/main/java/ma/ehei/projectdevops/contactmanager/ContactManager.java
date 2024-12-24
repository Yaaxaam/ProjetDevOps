package ma.ehei.projectdevops.contactmanager;

import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(String name, String phone, String email) {
        if (name == null || phone == null || email == null) {
            throw new IllegalArgumentException("Contact details cannot be null");
        }
        contacts.add(new Contact(name, phone, email));
    }

    public void removeContact(String name) {
        contacts.removeIf(contact -> contact.getName().equals(name));
    }

    public List<Contact> listContacts() {
        return new ArrayList<>(contacts);
    }
}
