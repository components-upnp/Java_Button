package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;
import java.beans.PropertyChangeSupport;

/**
 * Classe Service Bouton pour intéragir avec un bouton.
 * @author mkostiuk
 *
 */

@UpnpService(
        serviceId = @UpnpServiceId("ButtonService"),								// Identifiant Unique
        serviceType = @UpnpServiceType(value = "ButtonService", version = 1)		// D�finition de la version
)
public class Button {
    /**
     * Variable D'Etat, non �venemenc�e 
     * Permet d'envoyer le message de l'�tat dans lequel la lampe doit �tre
     */
    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private boolean target = false;
    
    /**
     * Variable d'etat �venemmenc�e
     * Permet de v�rifier si la lampe est bien dans le bon �tat.
     */
    @UpnpStateVariable(defaultValue = "0")
    private boolean status = false;
    
    /**
     * Variable qui me permet d'emmettre des �venements UPnP et JavaBean
     */
    private final PropertyChangeSupport propertyChangeSupport;
    public Button() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Get propertyChangeSupport
     * @return PropertyChangeSupport
     */
    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    /**
     * Permet d'envoyer un message de changement d'etat de la lampe
     * @param newTargetValue
     */
    @UpnpAction
    public void setTarget(@UpnpInputArgument(name = "NewTargetValue") boolean newTargetValue) {

    	// [FACULTATIF] je garde la l'ancienne valeur pour emmettre l'evenenment 
        boolean targetOldValue = target;
        target = newTargetValue;
        
        /*
         * ...
         * Ici on imagine un algorithme qui v�rifie que la lampe a bien chang� d'�tat
         * ...
         */
        
        boolean statusOldValue = status;
        status = newTargetValue;

        // Envoie un �venement UPnP, c'est le nom de la variable d'etat qui lance l'�venement
        // COMMENCE PAR UNE MAJUSCULE. Ici "Status" pour la varialbe status
        getPropertyChangeSupport().firePropertyChange("Status", statusOldValue, status);
        
        System.err.println("FIRE");
        // Fonctionne aussi :
        // getPropertyChangeSupport().firePropertyChange("Status", null null);
        
        // [FACULTATIF]
        // Ceci n'a pas d'effet pour le monitoring UPnP, mais fonctionne avec Javabean.
        // Ici on met le nom de la variable : status
        getPropertyChangeSupport().firePropertyChange("status", statusOldValue, status);
    }

    /**
     * Get target of the lamp
     * Methode Upnp grace au syst�me d'annotation
     * @return boolean
     */
    @UpnpAction(out = @UpnpOutputArgument(name = "RetTargetValue"))
    public boolean getTarget() {
        return target;
    }

    /**
     * Get Status of the lamp
     * Methode Upnp grace au syst�me d'annotation
     * @return boolean
     */
    @UpnpAction(out = @UpnpOutputArgument(name = "ResultStatus"))
    public boolean getStatus() {
    	// Pour ajouter des informations suppl�mentaires UPnP en cas d'erreur :
        // throw new ActionException(ErrorCode.ACTION_NOT_AUTHORIZED);
        return status;
    }
    
    /**
     * Print the version of the code
     * Ceci n'est pas une methode UPnP
     */
    public void printVersion(){
    	System.out.println("Version : 1.0");
    }
}