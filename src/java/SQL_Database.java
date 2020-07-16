/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author divya
 */
public class SQL_Database implements DataStorage{
    
    final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/murugananthad33?useSSL=false";
    final String db_id = "murugananthad33";
    final String db_psw = "1857326";
    private static String theuser_id;
    private static String thetag1;
    private static String thetag2;
    
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    
    @Override
    public String createUserAccount(String name, String userid, 
            String psw, String tag1, String tag2)
    {
        try
        {
            //connect to the database;
            conn = DriverManager.getConnection(DB_URL, db_id, db_psw);
            //create a statement obj to write sql statement
            st = conn.createStatement();
            //get the next accountnumber
            
            thetag1 = tag1;
            thetag2 = tag2;
            
            rs = st.executeQuery("Select * from user "
                    + "where userid = '" + userid + "'");
            
            if(rs.next())
            {
                return "Account creation failed. The same user already exists.";
            }
            else
            {
                int r = st.executeUpdate("Insert into user values('" + userid +
                    "', '" + name + "', '" + psw + "', '" + tag1 + 
                    "', '" + tag2 + "')");
            
            return "Your account is created!";
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return "Account creation failed!";
        }
        finally
        {
            //close the database
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    
     public ArrayList<String> login(String userid, String password)
    {
        ArrayList<String> arr = new ArrayList<String>();
        try
        {
            //connect to the databse
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            //create statement
            st = conn.createStatement();
            //search the accountID in the onlineAccount table
            rs = st.executeQuery("Select * from user "
                    + "where userid = '" + userid + "'");
            
            if(rs.next())
            {
                //the id is found, check the password
                if(password.equals(rs.getString(3)))
                {
                    arr.add(rs.getString(1));
                    arr.add(rs.getString(2));
                    theuser_id = rs.getString(1);
                }
                else
                {
                    arr.add("The password is not correct!");
                    arr.add("The password is not correct!");
                }
            }
            else
            {
                arr.add("The login failed!");
                arr.add("The login failed!");
            }
            return arr;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            arr.add("Internal error");
        }
        finally
        {
            //close the database
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
     
     
     @Override
    public String createAttraction(String attractionName, String attractionDesc, String state, String city, String tag){
        try
        {
            //connect to the databse
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            //create statement
            st = conn.createStatement();
            //search the accountID in the onlineAccount table
            rs = st.executeQuery("Select * from attraction "
                    + "where name = '" + attractionName + "'");
            
            if(rs.next())
            {
                return attractionName + " attraction is already created";
            }
            else
            {
                int r = st.executeUpdate("Insert into attraction values('" + attractionName +
                    "', '"+theuser_id+"', '" + city + "', '" + state +"', '" + attractionDesc + 
                    "', '" + tag + "', 0, 'Pending')");
                return "Attraction is created successfully!";
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
                   return "error";
        }
        finally
        {
            //close the database
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    
    public ArrayList<Attraction> getPendingRequests()
    {
        try
        {
            //connect to the databse
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            //create statement
            st = conn.createStatement();
            //search the accountID in the onlineAccount table
            rs = st.executeQuery("Select * from attraction "
                    + "where status = 'Pending'");
            
            ArrayList<Attraction> aList = new ArrayList<Attraction>();
            
            while(rs.next())
            {
                 Attraction attr = new Attraction(rs.getString(1), 
                 rs.getString(3), 
                 rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7));
                 aList.add(attr);
            }
            return aList;
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            //close the database
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            } 
        }
    }
    
    public void updateAttraction(String attrName, String status)
    {
        try
        {
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            st = conn.createStatement();
            rs = st.executeQuery("Select * from attraction "
                    + "where status = 'Pending'");
            
            int t = st.executeUpdate("Update attraction set "
                     + "status = '" + status + "' where name = '" + attrName + "'");
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
   
    
    @Override
    public ArrayList<Attraction> getApprovedAttractions(String userInput, int option)
    {
        try
        {
            String temp_tag1 = null;
            String temp_tag2 = null;
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            st = conn.createStatement();
            if(option == 1){
                rs = st.executeQuery("Select * from attraction "
                    + "where status = 'Approved' and tag = '"+userInput+"' order by score desc");
            }
            else if(option == 2){
                rs = st.executeQuery("Select * from attraction "
                    + "where status = 'Approved' and city = '"+userInput+"' order by score desc");                
            }
            else{
                rs1 = st.executeQuery("Select * from user where userid = '"+theuser_id+"'");
                if(rs1.next()){
                    temp_tag1 = rs1.getString(4);
                    temp_tag2 = rs1.getString(5);
                }
                rs = st.executeQuery("Select * from attraction "
                    + "where status = 'Approved' and userid = '"+theuser_id+"' and tag in('"+temp_tag1+"','"+temp_tag2+"') order by score desc limit 3");
            }
            
            ArrayList<Attraction> aList = new ArrayList<Attraction>();
            
            while(rs.next())
            {
                 Attraction attr = new Attraction(rs.getString(1), 
                 rs.getString(3), 
                 rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7));
                 aList.add(attr);
            }
            return aList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Attraction> getApprovedAttractionDetails(String attractionName)
    {
        try
        {
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            st = conn.createStatement();
            
            rs = st.executeQuery("Select * from attraction "
                + "where status = 'Approved' and name = '"+attractionName+"' order by score desc");
            
            ArrayList<Attraction> aList = new ArrayList<Attraction>();
            
            while(rs.next())
            {
                 Attraction attr = new Attraction(rs.getString(1), 
                 rs.getString(3), 
                 rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7));
                 aList.add(attr);
            }
            return aList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createReview(String attrname, float scores, String comments) {
        try
        {
            float avgscore = 0.0f;
            int id = 0;
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            st = conn.createStatement();
            rs = st.executeQuery("select max(id) from review");
            while(rs.next()){
                id = rs.getInt(1);
            }
            id++;
            int r = st.executeUpdate("Insert into review values('"+id+"','" + attrname +
                    "', '"+theuser_id+"', " + scores + ", '" + comments + "', '"+DateAndTime.DateTime()+"')");
            System.out.println("Review is created!");
            
            rs1 = st.executeQuery("Select avg(scores) from review "
                    + "where attrname = '"+attrname+"'");
            while(rs1.next()){
                avgscore = (float) (Math.round(rs1.getFloat(1) * 10)/10.0);
            }
            
            int t = st.executeUpdate("Update attraction set "
                     + "score = '" + avgscore + "' where name = '" + attrname + "'");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String saveFavouriteDestination(String attrname) {
        try
        {
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            st = conn.createStatement();

            rs = st.executeQuery("Select * from favdestination "
                    + "where attrname = '" + attrname + "' and userid = '"+theuser_id+"'");
            if(rs.next()){
                return "Save failed because attraction "+attrname+" is already saved to your favourites!!";
            }
            else{
                int r = st.executeUpdate("Insert into favdestination values('"+theuser_id+"','" + attrname + "')");
                return "Your Favourite destination is saved!";
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "error";
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<String> getFavouriteDestinations() {
         try
        {
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            st = conn.createStatement();
            rs = st.executeQuery("Select attrname from favdestination where userid = '"+theuser_id+"'");              
            
            ArrayList<String> fList = new ArrayList<String>();
            //ArrayList<Attraction> aList = new ArrayList<Attraction>();
            
            while(rs.next())
            {
                 String s = rs.getString(1);
                 fList.add(s);
            }
            return fList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    @Override
    public ArrayList<String> getYouMayLike() {
         try
        {
            String temp_tag1 = null;
            String temp_tag2 = null;
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            st = conn.createStatement();
            
            ArrayList<String> yList = new ArrayList<String>();
            //ArrayList<Attraction> aList = new ArrayList<Attraction>();
            
            
                rs1 = st.executeQuery("Select * from user where userid = '"+theuser_id+"'");
                if(rs1.next()){
                    temp_tag1 = rs1.getString(4);
                    temp_tag2 = rs1.getString(5);
                }
                rs = st.executeQuery("Select name from attraction "
                    + "where status = 'Approved' and userid = '"+theuser_id+"' and tag in('"+temp_tag1+"','"+temp_tag2+"') order by score desc limit 3");
            
            
            while(rs.next())
            {
                 String s = rs.getString(1);
                 yList.add(s);
            }
            return yList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    
    @Override
    public ArrayList<Review> displayReviews(String userInput)
    {
        try
        {
            conn = DriverManager.getConnection(DB_URL, 
                  db_id, db_psw);
            st = conn.createStatement();
            rs = st.executeQuery("Select * from review "
                    + "where attrname = '"+userInput+"' order by scores desc");
            
            ArrayList<Review> aList = new ArrayList<Review>();
            
            while(rs.next())
            {
                 Review reviews = new Review(rs.getString(2), 
                 rs.getFloat(4), rs.getString(5), rs.getString(6));
                 aList.add(reviews);
            }
            return aList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public String updatePassword(String id, String newPsw1)
    {
        try 
        {
            conn = DriverManager.getConnection(DB_URL, 
                    db_id, db_psw);

            st = conn.createStatement();

            int r = st.executeUpdate("Update user set "
                    + "psw = '" +  newPsw1 + "' where userid= '" + id + "'");
            return("confirmationResetOk");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "internalError";
         }
         finally
         {
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch (Exception e)
            {                 
                e.printStackTrace();
            }
         }
    }
}

