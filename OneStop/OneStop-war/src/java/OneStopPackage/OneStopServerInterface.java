/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OneStopPackage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This is the server interface 
 * @author yangm
 */
 
public interface OneStopServerInterface extends Remote{
    public List<String> sorting(List<String> names) throws RemoteException;
}
