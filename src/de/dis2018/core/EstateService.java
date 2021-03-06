package de.dis2018.core;

import de.dis2018.data.*;
import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;

/**
 * Class for managing all database entities.
 * <p>
 * TODO: All data is currently stored in memory.
 * The aim of the exercise is to gradually outsource data management to the
 * database. When the work is done, all sets in this class become obsolete!
 */
public class EstateService {

    //Hibernate Session
    private SessionFactory sessionFactory;

    public EstateService() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Find an estate agent with the given id
     *
     * @param id The ID of the agent
     * @return Agent with ID or null
     */
    public EstateAgent getEstateAgentByID(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        EstateAgent ea = session.get(EstateAgent.class, id);
        session.getTransaction().commit();
        session.close();

        return ea;
    }

    /**
     * Find estate agent with the given login.
     *
     * @param login The login of the estate agent
     * @return Estate agent with the given ID or null
     */
    public EstateAgent getEstateAgentByLogin(String login) {
        Session session = sessionFactory.openSession();
        String hql = "FROM EstateAgent ea WHERE ea.login = '" + login + "'";
        List<EstateAgent> results = session.createQuery(hql, EstateAgent.class).list();
        assert results.size() == 1;
        session.close();

        return results.get(0);
    }

    /**
     * Returns all estateAgents
     */
    public List<EstateAgent> getAllEstateAgents() {
        Session session = sessionFactory.openSession();
        String hql = "SELECT ea FROM EstateAgent ea JOIN FETCH ea.estates";
        List<EstateAgent> results = session.createQuery(hql, EstateAgent.class).list();
        session.close();

        return results;
    }

    /**
     * Find an person with the given id
     *
     * @param id The ID of the person
     * @return Person with ID or null
     */
    public Person getPersonById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Person p = session.get(Person.class, id);
        session.getTransaction().commit();
        session.close();

        return p;
    }

    /**
     * Adds an estate agent
     *
     * @param ea The estate agent
     */
    public void addEstateAgent(EstateAgent ea) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(ea);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Deletes an estate agent
     *
     * @param ea The estate agent
     */
    public void deleteEstateAgent(EstateAgent ea) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(ea);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Adds a person
     *
     * @param p The person
     */
    public void addPerson(Person p) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(p);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Returns all persons
     */
    public List<Person> getAllPersons() {
        Session session = sessionFactory.openSession();
        String hql = "FROM Person";
        List<Person> results = session.createQuery(hql, Person.class).getResultList();
        session.close();

        return results;
    }

    /**
     * Returns all Houses
     */
    public List<House> getAllHouses() {
        Session session = sessionFactory.openSession();
        String hql = "SELECT h FROM House h JOIN FETCH h.manager";
        List<House> houses = session.createQuery(hql, House.class).getResultList();
        List<House> results = new ArrayList<>(houses);
        session.close();

        return results;
    }

    /**
     * Returns all Apartments
     */
    public List<Apartment> getAllApartments() {
        Session session = sessionFactory.openSession();
        String hql = "SELECT a FROM Apartment a JOIN FETCH a.manager";
        List<Apartment> apart = session.createQuery(hql, Apartment.class).getResultList();
        List<Apartment> results = new ArrayList<>(apart);
        session.close();

        return results;
    }


    /**
     * Returns all Estates
     */
    public List<Estate> getAllEstates() {
        List<Estate> results = new ArrayList<>();

        results.addAll(this.getAllApartments());
        results.addAll(this.getAllHouses());

        return results;
    }

    /**
     * Deletes a person
     *
     * @param p The person
     */
    public void deletePerson(Person p) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(p);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Adds a house
     *
     * @param h The house
     */
    public void addHouse(House h) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(h);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Returns all houses of an estate agent
     *
     * @param ea the estate agent
     * @return All houses managed by the estate agent
     */
    public Set<House> getAllHousesForEstateAgent(EstateAgent ea) {
        Session session = sessionFactory.openSession();
        Set<House> ret = new HashSet<>();
        String hql = "FROM House h JOIN FETCH h.manager";
        List<House> houses = session.createQuery(hql, House.class).list();

        for (House house : houses) {
            if (house.getManager().equals(ea)) {
                ret.add(house);
            }
        }

        session.close();

        return ret;
    }

    /**
     * Find a house with a given ID
     *
     * @param id the house id
     * @return The house or null if not found
     */
    public House getHouseById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        House h = session.get(House.class, id);
        session.getTransaction().commit();
        session.close();

        return h;
    }

    /**
     * Deletes a house
     *
     * @param h The house
     */
    public void deleteHouse(House h) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(h);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Adds an apartment
     *
     * @param a the aparment
     */
    public void addApartment(Apartment a) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(a);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Returns all apartments of an estate agent
     *
     * @param ea The estate agent
     * @return All apartments managed by the estate agent
     */
    public Set<Apartment> getAllApartmentsForEstateAgent(EstateAgent ea) {
        Session session = sessionFactory.openSession();
        Set<Apartment> ret = new HashSet<>();
        String hql = "FROM Apartment a JOIN FETCH a.manager";
        List<Apartment> apartments = session.createQuery(hql, Apartment.class).list();

        for (Apartment apartment : apartments) {
            if (apartment.getManager().equals(ea)) {
                ret.add(apartment);
            }
        }
        session.close();

        return ret;
    }

    /**
     * Find an apartment with given ID
     *
     * @param id The ID
     * @return The apartment or zero, if not found
     */
    public Apartment getApartmentByID(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Apartment a = session.get(Apartment.class, id);
        session.getTransaction().commit();
        session.close();

        return a;
    }

    /**
     * Deletes an apartment
     *
     * @param w The apartment
     */
    public void deleteApartment(Apartment w) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(w);
        session.getTransaction().commit();
        session.close();
    }


    /**
     * Adds a tenancy contract
     *
     * @param tc The tenancy contract
     */
    public void addTenancyContract(TenancyContract tc) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(tc);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Adds a purchase contract
     *
     * @param pc The purchase contract
     */
    public void addPurchaseContract(PurchaseContract pc) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(pc);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Finds a tenancy contract with a given ID
     *
     * @param id Die ID
     * @return The tenancy contract or zero if not found
     */
    public TenancyContract getTenancyContractByID(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        TenancyContract tc = session.get(TenancyContract.class, id);
        session.getTransaction().commit();
        session.close();

        return tc;
    }

    /**
     * Finds a purchase contract with a given ID
     *
     * @param id The id of the purchase contract
     * @return The purchase contract or null if not found
     */
    public PurchaseContract getPurchaseContractById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        PurchaseContract pc = session.get(PurchaseContract.class, id);
        session.getTransaction().commit();
        session.close();

        return pc;
    }

    /**
     * Returns all tenancy contracts for apartments of the given estate agent
     *
     * @param ea The estate agent
     * @return All contracts belonging to apartments managed by the estate agent
     */
    public Set<TenancyContract> getAllTenancyContractsForEstateAgent(EstateAgent ea) {
        Session session = sessionFactory.openSession();
        Set<TenancyContract> ret = new HashSet<>();
        String hql = "FROM TenancyContract tc JOIN FETCH tc.apartment";
        List<TenancyContract> tenancyContracts = session.createQuery(hql, TenancyContract.class).list();

        for (TenancyContract tenancyContract : tenancyContracts) {
            if (tenancyContract.getApartment().getManager().equals(ea)) {
                ret.add(tenancyContract);
            }
        }
        session.close();

        return ret;
    }

    /**
     * Returns all purchase contracts for houses of the given estate agent
     *
     * @param ea The estate agent
     * @return All purchase contracts belonging to houses managed by the given estate agent
     */
    public Set<PurchaseContract> getAllPurchaseContractsForEstateAgent(EstateAgent ea) {
        Session session = sessionFactory.openSession();
        Set<PurchaseContract> ret = new HashSet<>();
        String hql = "FROM PurchaseContract pc JOIN FETCH pc.contractPartner JOIN FETCH pc.house";
        List<PurchaseContract> purchaseContracts = session.createQuery(hql, PurchaseContract.class).list();

        for (PurchaseContract purchaseContract : purchaseContracts) {
            if (purchaseContract.getHouse().getManager().equals(ea)) {
                ret.add(purchaseContract);
            }
        }
        session.close();

        return ret;
    }

    /**
     * Finds all tenancy contracts relating to the apartments of a given estate agent
     *
     * @param ea The estate agent
     * @return set of tenancy contracts
     */
    public Set<TenancyContract> getTenancyContractByEstateAgent(EstateAgent ea) {
        Session session = sessionFactory.openSession();
        Set<TenancyContract> ret = new HashSet<>();
        String hql = "FROM TenancyContract tc JOIN FETCH tc.contractPartner, tc.apartment";
        List<TenancyContract> tenancyContracts = session.createQuery(hql, TenancyContract.class).list();

        for (TenancyContract tenancyContract : tenancyContracts) {
            if (tenancyContract.getApartment().getId() == ea.getId()) {
                ret.add(tenancyContract);
            }
        }
        session.close();

        return ret;
    }

    /**
     * Finds all purchase contracts relating to the houses of a given estate agent
     *
     * @param ea The estate agent
     * @return set of purchase contracts
     */
    public Set<PurchaseContract> getPurchaseContractByEstateAgent(EstateAgent ea) {
        Session session = sessionFactory.openSession();
        Set<PurchaseContract> ret = new HashSet<>();
        String hql = "FROM PurchaseContract pc JOIN FETCH pc.contractPartner, pc.house";
        List<PurchaseContract> purchaseContracts = session.createQuery(hql, PurchaseContract.class).list();

        for (PurchaseContract purchaseContract : purchaseContracts) {
            if (purchaseContract.getHouse().getId() == ea.getId()) {
                ret.add(purchaseContract);
            }
        }
        session.close();

        return ret;
    }


    /**
     * Deletes a tenancy contract
     *
     * @param tc the tenancy contract
     */
    public void deleteTenancyContract(TenancyContract tc) {
        Session session = sessionFactory.openSession();
        session.getTransaction();
        session.delete(tc);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Deletes a purchase contract
     *
     * @param pc the purchase contract
     */
    public void deletePurchaseContract(PurchaseContract pc) {
        Session session = sessionFactory.openSession();
        session.getTransaction();
        session.delete(pc);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Adds some test data
     */
    public void addTestData() {
        EstateAgent m = new EstateAgent();
        m.setName("Max Mustermann");
        m.setAddress("Am Informatikum 9");
        m.setLogin("max");
        m.setPassword("max");

        this.addEstateAgent(m);

        Person p1 = new Person();
        p1.setAddress("Informatikum");
        p1.setName("Mustermann");
        p1.setFirstname("Erika");

        Person p2 = new Person();
        p2.setAddress("Reeperbahn 9");
        p2.setName("Albers");
        p2.setFirstname("Hans");

        this.addPerson(p1);
        this.addPerson(p2);

        House h = new House();
        h.setCity("Hamburg");
        h.setPostalcode(22527);
        h.setStreet("Vogt-Kölln-Street");
        h.setStreetnumber("2a");
        h.setSquareArea(384);
        h.setFloors(5);
        h.setPrice(10000000);
        h.setGarden(true);
        h.setManager(m);
        this.addHouse(h);

        Session session = sessionFactory.openSession();
        EstateAgent m2 = (EstateAgent) session.get(EstateAgent.class, m.getId());
        Set<Estate> immos = m2.getEstates();
        Iterator<Estate> it = immos.iterator();

        while (it.hasNext()) {
            Estate i = it.next();
            System.out.println("Estate " + i.getId() + ": " + i.getCity());
        }

        session.close();

        Apartment w = new Apartment();
        w.setCity("Hamburg");
        w.setPostalcode(22527);
        w.setStreet("Vogt-Kölln-Street");
        w.setStreetnumber("3");
        w.setSquareArea(120);
        w.setFloor(4);
        w.setRent(790);
        w.setKitchen(true);
        w.setBalcony(false);
        w.setManager(m);
        this.addApartment(w);

        w = new Apartment();
        w.setCity("Berlin");
        w.setPostalcode(22527);
        w.setStreet("Vogt-Kölln-Street");
        w.setStreetnumber("3");
        w.setSquareArea(120);
        w.setFloor(4);
        w.setRent(790);
        w.setKitchen(true);
        w.setBalcony(false);
        w.setManager(m);
        this.addApartment(w);

//        PurchaseContract pc = new PurchaseContract();
//        pc.setHouse(h);
//        pc.setContractPartner(p1);
//        pc.setContractNo(9234);
//        pc.setDate(new Date(System.currentTimeMillis()));
//        pc.setPlace("Hamburg");
//        pc.setNoOfInstallments(5);
//        pc.setIntrestRate(4);
//        this.addPurchaseContract(pc);

        TenancyContract tc = new TenancyContract();
        tc.setApartment(w);
        tc.setContractPartner(p2);
        tc.setContractNo(23112);
        tc.setDate(new Date(System.currentTimeMillis() - 1000000000));
        tc.setPlace("Berlin");
        tc.setStartDate(new Date(System.currentTimeMillis()));
        tc.setAdditionalCosts(65);
        tc.setDuration(36);
        this.addTenancyContract(tc);

//    	  deletePurchaseContract(pc);
//        deleteHouse(h);
//        deleteTenancyContract(tc);
//        deleteApartment(w);
//        deletePerson(p1);
//        deletePerson(p2);
//        deleteEstateAgent(m);
    }
}
