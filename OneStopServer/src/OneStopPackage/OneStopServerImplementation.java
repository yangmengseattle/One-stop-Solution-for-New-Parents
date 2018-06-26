/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OneStopPackage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;

/**
 * This method will sort the vector of rate of Product  
 * @author yangm
 */
public class OneStopServerImplementation extends UnicastRemoteObject implements OneStopServerInterface 
{
    //constructor
    public OneStopServerImplementation() throws RemoteException{
        
    }
    @Override
    public List<String> sorting(List<String> names) throws RemoteException{
        try{
            if(names == null){
                System.out.println("names is empty");
                return null;
            }
            Collections.shuffle(names);
            return names;
        }catch (Exception e){
            e.printStackTrace( );
	    System.exit( -1 );
        }
        return null;
    } 
}

