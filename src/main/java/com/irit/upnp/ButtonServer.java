package com.irit.upnp;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.*;
import org.fourthline.cling.binding.annotations.*;
import org.fourthline.cling.model.*;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.*;
import java.io.IOException;

/**
 * Classe qui permet de lancer le serveur du bouton UPnP
 * @author tbille
 *
 */

public class ButtonServer implements Runnable {
	/**
	 * Main
	 * Copy code if you need to add a Upnp service on your device
	 * @param args
	 * @throws Exception
	 */
    
    private ButtonFrame frameB;
    
    public static void main(String[] args) throws Exception {
        // Start a user thread that runs the UPnP stack
        Thread serverThread = new Thread(new ButtonServer());
        serverThread.setDaemon(false);
        serverThread.start();
    }
    
    /**
     * Run the UPnP service
     */
    public void run() {
        try {

            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(
                    createDevice()
            );
        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    /**
     * Permet de cr�er un device
     * Il est possible de cr�er plusieurs service pour un m�me device, dans ce cas confer commentaires en fin de methode.
     * @return LocalDevice
     * @throws ValidationException
     * @throws LocalServiceBindingException
     * @throws IOException
     */
	public LocalDevice createDevice()
	        throws ValidationException, LocalServiceBindingException, IOException {
	
		/**
		 * Description du Device
		 */
	    DeviceIdentity identity =
	            new DeviceIdentity(
	                    UDN.uniqueSystemIdentifier("Button")
	            );
	
	    DeviceType type =
	            new UDADeviceType("Button", 1);
	
	    DeviceDetails details =
	            new DeviceDetails(
	                    "Friendly Button",					// Friendly Name
	                    new ManufacturerDetails(
	                    		"CreaTech",								// Manufacturer
	    						""),								// Manufacturer URL
	                    new ModelDetails(
	                            "ButtonTest",						// Model Name
	                            "A basic button.",	// Model Description
	                            "v1" 								// Model Number
	                    )
	            );
	
	
	    // Ajout du service
	    LocalService<Button> buttonService =
	            new AnnotationLocalServiceBinder().read(Button.class);
	
	    buttonService.setManager(
	            new DefaultServiceManager(buttonService, Button.class)
	    );
	
            new ButtonFrame(buttonService).setVisible(true);
            
	    // retour en cas de 1 service
	    return new LocalDevice(identity, type, details, buttonService);
	

		/* Si jamais plusieurs services pour un device (adapter code)
	    return new LocalDevice(
	            identity, type, details, 
	            new LocalService[] {switchPowerService, myOtherService}
	    );
	    */
	}


}