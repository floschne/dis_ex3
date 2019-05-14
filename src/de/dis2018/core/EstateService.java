package de.dis2018.core;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.dis2018.data.House;
import de.dis2018.data.Estate;
import de.dis2018.data.PurchaseContract;
import de.dis2018.data.EstateAgent;
import de.dis2018.data.TenancyContract;
import de.dis2018.data.Person;
import de.dis2018.data.Apartment;

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
        String hql = "FROM EstateAgent as estateAgent WHERE estateAgent.login = '" + login + "'";
        @SuppressWarnings("unchecked")
		List<EstateAgent> results = session.createQuery(hql).list();
        assert results.size() == 1;
        session.close();
        
        return results.get(0);
    }

    /**
     * Returns all estateAgents
     */
    public List<EstateAgent> getAllEstateAgents() {
        Session session = sessionFactory.openSession();
        String hql = "FROM EstateAgent";
        @SuppressWarnings("unchecked")
		List<EstateAgent> results = session.createQuery(hql).list();
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
        session.delete(ea);
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
        @SuppressWarnings("unchecked")
		List<Person> results = session.createQuery(hql).list();
        session.close();
        
        return results;
    }

    /**
     * Deletes a person
     *
     * @param p The person
     */
    public void deletePerson(Person p) {
        Session session = sessionFactory.openSession();
        session.delete(p);
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
        String hql = "FROM Houses";        
        @SuppressWarnings("unchecked")
		List<House> houses = session.createQuery(hql).list();

        for (House house : houses) {
        	if (house.getManager().equals(ea)) {
        		ret.add(house);
        	}        	
        }

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
        session.delete(h);
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
        String hql = "FROM Apartments";        
        @SuppressWarnings("unchecked")
		List<Apartment> apartments = session.createQuery(hql).list();

        for (Apartment apartment : apartments) {
        	if (apartment.getManager().equals(ea)) {
        		ret.add(apartment);
        	}        	
        }

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
        session.delete(w);
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
        String hql = "FROM TenacyContract";        
        @SuppressWarnings("unchecked")
		List<TenancyContract> tenancyContracts = session.createQuery(hql).list();

        for (TenancyContract tenancyContract : tenancyContracts) {
        	if (tenancyContract.getApartment().getManager().equals(ea)) {
        		ret.add(tenancyContract);
        	}        	
        }

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
        String hql = "FROM PurchaseContract";        
        @SuppressWarnings("unchecked")
		List<PurchaseContract> purchaseContracts = session.createQuery(hql).list();

        for (PurchaseContract purchaseContract : purchaseContracts) {
        	if (purchaseContract.getHouse().getManager().equals(ea)) {
        		ret.add(purchaseContract);
        	}        	
        }

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
        String hql = "FROM TenacyContract";        
        @SuppressWarnings("unchecked")
		List<TenancyContract> tenancyContracts = session.createQuery(hql).list();

        for (TenancyContract tenancyContract : tenancyContracts) {
        	if (tenancyContract.getApartment().getId() == ea.getId()) {
        		ret.add(tenancyContract);
        	}        	
        }

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
        String hql = "FROM PurchaseContract";        
        @SuppressWarnings("unchecked")
		List<PurchaseContract> purchaseContracts = session.createQuery(hql).list();

        for (PurchaseContract purchaseContract : purchaseContracts) {
        	if (purchaseContract.getHouse().getId() == ea.getId()) {
        		ret.add(purchaseContract);
        	}        	
        }

        return ret;
    }


    /**
     * Deletes a tenancy contract
     *
     * @param tc the tenancy contract
     */
    public void deleteTenancyContract(TenancyContract tc) {
        Session session = sessionFactory.openSession();
        session.delete(tc);
        session.close();
    }

    /**
     * Deletes a purchase contract
     *
     * @param pc the purchase contract
     */
    public void deletePurchaseContract(PurchaseContract pc) {
        Session session = sessionFactory.openSession();
        session.delete(pc);
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
