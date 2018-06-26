package OneStopPackage;
import java.rmi.*;
import java.rmi.registry.*;
import OneStopPackage.OneStopServerImplementation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author yangm
 */
public class OneStopPackage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //start registry 
        try {     
	    startRegistry( 15001 );
	    OneStopServerImplementation serverObject = new OneStopServerImplementation( );
	    Naming.rebind( "rmi://localhost:15001/server", 
			   serverObject );
	    System.out.println( "Server ready." );
	} catch ( Exception e ) {
	    e.printStackTrace( );
	    System.exit( -1 );
	}

    }
    private static void startRegistry( int port ) throws RemoteException {
	try {
	    Registry registry = LocateRegistry.getRegistry( port );
	    registry.list( );  
	}
	catch ( RemoteException e ) { 
	    Registry registry = LocateRegistry.createRegistry( port );
	}
    }  
}
