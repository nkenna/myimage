/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colnet.Beans;

import colnet.entities.Job;
import colnet.entities.Owner;
import colnet.entities.Worker;
import colnet.interfaces.ColnetRemote;
import helpers.OwnerRef;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author STEINACOZ-PC
 */
@Stateless
@Remote
public class ColnetSessionBean implements ColnetRemote{
    
    @PersistenceContext(unitName="ColnetHQ-PU")
    private EntityManager em;
    Owner owner;
    OwnerRef oRef;
    private String status; 
    
 
   @Override
    public void addOwner(String fname, String lname, String email, String address, String state, String country, String lga, String password) throws ParseException {
     LocalDate ld = LocalDate.now(); 
        int day = ld.getDayOfMonth();
        int month = ld.getMonthValue();
        int year = ld.getYear();
        Owner own = new Owner();
     
      
      try{
       Query q = em.createQuery("SELECT o FROM Owner o WHERE o.email = :email");
       q.setParameter("email", email);
       own = (Owner)q.getSingleResult();
       
       String resEmail = own.getEmail();
       
       if(resEmail.equalsIgnoreCase(email)){
         System.out.println("duplicate records");
         setStatus("duplicate records");
       
       }else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       
        Date d = format.parse(year+"-"+month+"-"+day);
        own = new Owner();
        oRef = new OwnerRef();
        own.setFirstname(fname);
        own.setLastname(lname);
       
        own.setAddress(address);
        own.setState(state);
        own.setEmail(email);
        own.setCountry(country);
        own.setPassword(password);
        own.setRef(oRef.refNumber(lname));
        own.setRegisterdate(d);
        
        em.persist(own);
        em.flush();
        setStatus("Operation Successful"); 
       }
       
       
      }catch(NoResultException e){
      
        
       Owner own1 = new Owner();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       
        Date d = format.parse(year+"-"+month+"-"+day);
        
        oRef = new OwnerRef();
        own1.setFirstname(fname);
        own1.setLastname(lname);
       
        own1.setAddress(address);
        own1.setState(state);
        own1.setEmail(email);
        own1.setCountry(country);
        own1.setPassword(password);
        own1.setRef(oRef.refNumber(lname));
        own1.setRegisterdate(d);
        
        em.persist(own1);
        em.flush();
        setStatus("Operation Successful"); 
      }
      
     
        
     
       
    }

    @Override   
    public void addJob() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   

    @Override
    public String login(String email, String password) {
         String flag = "fail";
     Owner ow;    
     try{
       Query query = em.createQuery("SELECT o FROM Owner o WHERE o.email = :email and o.password = :password");
   query.setParameter("email", email);
   query.setParameter("email", password);
   System.out.println("email1: " + email);
   ow = (Owner) query.getSingleResult();
   
   String verifyEmail = ow.getEmail();
   String verifyPass = ow.getPassword();
   
    System.out.println("email2: " + verifyEmail);
   
 if(verifyEmail.equalsIgnoreCase(email) && verifyPass.equals(password)){
     flag = "success";
 }
     }catch(NoResultException e){
       return  null;  
     }
     return flag;
     }

    @Override
    public List<Owner> profileOwner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Owner> jobsByOwner() {
         throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String OwnerBuildRef() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeOwner(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTradesPeople() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String tPeopleBuildRef() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String completedJobs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String showRemarks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeTradesPeople(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String jobBuildRef() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Job> allJobs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeJob(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Job> getJobByLocation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Owner> getOwnerByLocation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Worker> getWorkerByLocation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean searchEmailOwner (String email) {
        boolean e = true;
      return e;
    }

    
}
