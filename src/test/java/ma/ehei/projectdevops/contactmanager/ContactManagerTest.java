package ma.ehei.projectdevops.contactmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ContactManagerTest {
    ContactManager contactManager = new ContactManager();

    @Test
    public void testAddContact() {
        contactManager.addContact("John Doe", "1234567890", "john@example.com");
        List<Contact> contacts = contactManager.listContacts();
        assertEquals(1, contacts.size());
        assertEquals("John Doe", contacts.get(0).getName());
    }

    @Test
    public void testRemoveContact() {
        contactManager.addContact("John Doe", "1234567890", "john@example.com");
        contactManager.removeContact("John Doe");
        List<Contact> contacts = contactManager.listContacts();
        assertTrue(contacts.isEmpty());
    }

    @Test
    public void testAddContactWithNullDetails() {
        assertThrows(IllegalArgumentException.class, () -> contactManager.addContact(null, "1234567890", "john@example.com"));
    }
}
