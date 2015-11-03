package pl.polsl.gabrys.arkadiusz;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pl.polsl.gabrys.arkadiusz.interfaces.DatabaseManagerRemote;
import pl.polsl.gabrys.arkadiusz.view.View;

/**
 * Main class with the main method
 * @author Arkadiusz Gabryś
 * @version 1.0
 */
public class Controller {
    
    /**
     * Parses input arguments and controls the View object
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        InitialContext ic = null;
        
        try {
            ic = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DatabaseManagerRemote db = null;
        
        try {
            db = (DatabaseManagerRemote) ic.lookup("DatabaseManager");
        } catch (NamingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        args = new String[] {"-f", "Author", "All"};
        
        // create a View class and pass command line arguments
        View view = new View(db);
        
        // manage user input
        Integer errorCode = view.manageUserInput(args);
        
        // return error code to the shell
        System.exit(errorCode);
    }
}
