package de.dis2018.menu;

import de.dis2018.data.Person;

import java.util.Iterator;
import java.util.List;

/**
 * A small menu showing all persons from a set for selection
 */
public class PersonSelectionMenu extends Menu {
    public static final int BACK = -1;

    public PersonSelectionMenu(String title, List<Person> personen) {
        super(title);

        Iterator<Person> it = personen.iterator();
        while (it.hasNext()) {
            Person p = it.next();
            addEntry(p.getFirstname() + " " + p.getName(), p.getId());
        }
        addEntry("Back", BACK);
    }
}
