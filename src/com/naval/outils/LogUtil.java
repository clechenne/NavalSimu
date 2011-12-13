package com.naval.outils;




import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


/**
 * Cette classe permet de centraliser le servic de log et de debug.
 * 
 * @author Lechenne_c
 */
public class LogUtil {
    /**
     * Fichier XML de confirguration.
     */
    private static final String FIC_CONF_LOG4J="/log4j.conf";
            
    /**
     * Outil de log utilis�.
     */
    private static Logger logger = null;
    
    /**
     * Initialiseur statique de la classe, il charge la configuration
     * une fois pour toutes.
     */
    static {
            init();
    }		
		
	/**
	 * Constructeur priv�.
	 *
	 */
	private LogUtil() {
	}

	/**
	 * Error log.
	 *
	 * @param aMessage message to log.
	 * @param t Exception
	 */
	public static void doError(String aMessage, Throwable t) {
		logger.error(aMessage, t);
	}

	/**
	 * Error log.
	 * 
	 * @param aMessage message to log. 
	 */
	public static void doError(String aMessage) {
		logger.error(aMessage);
	}
	
	/**
	 * Info. 
	 * 
	 * @param aMessage message to log.
	 */
	public static void doInfo(String aMessage) {
		logger.info(aMessage);
	}

	/**
	 * debug.
	 *  
	 * @param aMessage message to log.  
	 */
	public static void doDebug(String aMessage) {
		logger.debug(aMessage);
	}
    /**
     * Lecture de la confifuration log4j.
     * En cas de probleme : un msg est ecrit sur System.err, et la
     * variable logger n'est pas affectee.
     * 
     */
    private static void init() {
            URL log4jConfigUrl= LogUtil.class.getResource(FIC_CONF_LOG4J);
            if(log4jConfigUrl!=null) {
                    DOMConfigurator.configure(log4jConfigUrl);
            } else {
                    System.out.println("Fichier de configuration de log4j "+FIC_CONF_LOG4J+" non trouvée, utilisation de la confirguration par défaut pour log4j.");
            }
            logger=LogManager.getLogger("naval");
    }
	
}
