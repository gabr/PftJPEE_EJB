package pl.polsl.gabrys.arkadiusz.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import pl.polsl.gabrys.arkadiusz.interfaces.DatabaseManagerRemote;

/**
 * Class provides CLI and interactive console interface
 * @author Arkadiusz Gabryś
 * @version 1.5
 */
public class View {
    
    /**
     * Error code for no errors
     */
    public final Integer ERROR_CODE_OK = 0;
    
    /**
     * Error code when option is invalid
     */
    public final Integer ERROR_CODE_OPTION_ERROR = 1;
    
    /**
     * Error code for unknown errors
     */
    public final Integer ERROR_CODE_UNKNOWN_ERROR = 2;
    
    /**
     * Error code for exit action
     */
    public final Integer ERROR_CODE_EXIT = -1;
    
    /**
     * Help message for help option
     */
    private final String HELP_HELP = "help\n"
            + "usage: help [option]\n"
            + "\n"
            + "Without any option shows main help message.\n"
            + "With specified option shows help message for\n"
            + "specified option.\n"
            + "\n"
            + "Examples:\n"
            + "    EJB-Client -h\n"
            + "    EJB-Client -help\n"
            + "    EJB-Client -h f\n"
            + "    EJB-Client -h find\n";
    
//    /**
//     * Help message for interactive option
//     */
//    private final String HELP_INTERACTIVE = "interactive\n"
//            + "usage: interactive\n"
//            + "\n"
//            + "Starts interactive console interface.\n"
//            + "\n"
//            + "Examples:\n"
//            + "    EJB-Client -i\n"
//            + "    EJB-Client -interactive\n";
    
    /**
     * Help message for persist option
     */
    private final String HELP_PERSIST = "persist\n"
            + "usage:\n"
            + "       persist Author <Name> <LastName>\n"
            + "       persist Book   <Title> <Pages> <Date> <AuthorId>\n"
            + "\n"
            + "Adds new Author or Book entity to the database.\n"
            + "\n"
            + "Examples:\n"
            + "    EJB-Client -p Author Stephen King\n"
            + "    EJB-Client -persist Book \"The Waste Lands\" 351 \"1991.01.23\" 1\n";
    
    /**
     * Help message for find option
     */
    private final String HELP_FIND = "find\n"
            + "usage:\n"
            + "       find Author All\n"
            + "       find Author Id <id>\n"
            + "       find Author Name <name>\n"
            + "       find Book   All\n"
            + "       find Book   Id <id>\n"
            + "       find Book   Title <title>\n"
            + "\n"
            + "Finds all entities or entities with given value.\n"
            + "\n"
            + "Examples:\n"
            + "    EJB-Client -f Author Name Stephen\n"
            + "    EJB-Client -find Book All\n";
    
    /**
     * Help message for merge option
     */
    private final String HELP_MERGE = "merge\n"
            + "usage:\n"
            + "       merge Author <Id> <Name> <LastName>\n"
            + "       merge Book   <Id> <Title> <Pages> <Date> <AuthorId>\n"
            + "\n"
            + "Changes values for entity with given id.\n"
            + "\n"
            + "Examples:\n"
            + "    EJB-Client -m 1 Author Stephen King\n"
            + "    EJB-Client -merge 2 Book \"Drawing of the Three\" 284 \"1987.03.13\" 1\n";
    
    /**
     * Help message for remove option
     */
    private final String HELP_REMOVE = "remove\n"
            + "usage:\n"
            + "       remove Author <Id>\n"
            + "       remove Book   <Id>\n"
            + "\n"
            + "Removes entity with given id.\n"
            + "\n"
            + "Examples:\n"
            + "    EJB-Client -r 1\n"
            + "    EJB-Client -remove 2\n";
    
    /**
     * Options structure for parsing
     */
    private final Options options;
    
    /**
     * Database manager
     */
    private final DatabaseManagerRemote databaseManager;

    /**
     * Creates options structure for parsing
     * @throws IllegalArgumentException if the given database instance is null
     */
    public View(DatabaseManagerRemote db) throws IllegalArgumentException {
        
        // check the database object
        if (db == null)
            throw new IllegalArgumentException("Database manager cannot be null");
        
        // save database instance
        databaseManager = db;
        
        // create options structure
        options = new Options();
        OptionGroup interactiveHelpCRUD = new OptionGroup();
        
        interactiveHelpCRUD.addOption(Option.builder("q")
                .longOpt("quit")
                .optionalArg(false)
                .numberOfArgs(0)
                .desc("exits the program")
                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("h")
                .longOpt("help")
                .optionalArg(true)
                .numberOfArgs(1)
                .argName("option name")
                .desc("prints this help or option help")
                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("p")
                .longOpt("persist")
                .hasArgs()
                .argName("args")
                .numberOfArgs(5)
                .optionalArg(true)
                .desc("persists new entity")
                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("f")
                .longOpt("find")
                .hasArgs()
                .argName("args")
                .numberOfArgs(3)
                .optionalArg(true)
                .desc("finds entities")
                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("m")
                .longOpt("merge")
                .hasArgs()
                .argName("args")
                .numberOfArgs(5)
                .optionalArg(true)
                .desc("merges entity")
                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("r")
                .longOpt("remove")
                .hasArgs()
                .argName("args")
                .numberOfArgs(2)
                .desc("removes entity")
                .build());
        
        interactiveHelpCRUD.setRequired(true);
        options.addOptionGroup(interactiveHelpCRUD);
    }
    
    /**
     * Prints help message
     */
    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("EJB-Client", "\nLibrary database CRUD", options, "");
    }
    
    /**
     * Gets input arguments from user
     * @return the input arguments 
     */
    public String[] readArgs() {
        Scanner reader = new Scanner(System.in);
        
        System.out.println("\n");
        System.out.println(">>> EJB-Client input arguments: ");
        
        String input = "EJB-Client " + reader.nextLine();
        System.out.println(input);
        return input.split(" ");
    }

    /**
     * Decides about action based on given arguments
     * @param args the command line arguments
     * @return the error code
     */
    public Integer manageUserInput(String[] args) {
        
        Integer errorCode = ERROR_CODE_OK;
        
        CommandLine commandLine;
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException pe) {
            formatter.printHelp("EJB-Client", "\nLibrary database CRUD", options, "\n" + pe.getMessage());
            return ERROR_CODE_OPTION_ERROR;
        }
        
        // only one option can be passed
        Option selected = commandLine.getOptions()[0];
        
        switch (selected.getOpt()) {
            case "h":
                errorCode = help(selected);
                break;
            case "p":
                errorCode = persist(selected);
                break;
            case "f":
                errorCode = find(selected);
                break;
            case "m":
                errorCode = merge(selected);
                break;
            case "r":
                errorCode = remove(selected);
                break;
            case "q":
                errorCode = ERROR_CODE_EXIT;
                break;
        }
        
        return errorCode;
    }
    
    /**
     * Manages help messages
     * @param option the one of CLI options
     * @return the error code
     */
    private Integer help(Option option) {
        String value = option.getValue();
        
        if (value == null || value.isEmpty())
        {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("EJB-Client",
                    "\nLibrary database CRUD",
                    options,
                    "\nFor details type -h with option name.");
            return ERROR_CODE_OK;
        } else {
            
            switch(value) {
                case "h":
                case "help":
                    System.out.println(HELP_HELP);
                    break;
                    
                case "p":
                case "persist":
                    System.out.println(HELP_PERSIST);
                    break;
                    
                case "f":
                case "find":
                    System.out.println(HELP_FIND);
                    break;
                    
                case "m":
                case "merge":
                    System.out.println(HELP_MERGE);
                    break;
                    
                case "r":
                case "remove":
                    System.out.println(HELP_REMOVE);
                    break;
                    
                default:
                    System.out.println("UNKNOWN OPTION!\n");
                    System.out.println(HELP_HELP);
                    return ERROR_CODE_OPTION_ERROR;
            }
            
            return ERROR_CODE_OK;
        }
    }

    /**
     * Persists new entity specified in parameters
     * @param selected the given parameters
     * @return the error code
     */
    private Integer persist(Option selected) {
        List<String> values = selected.getValuesList();
        
        if (values.size() < 2) {
            System.out.println(HELP_PERSIST);
            return ERROR_CODE_OPTION_ERROR;
        }
        
            String entity = values.get(0).toLowerCase().trim();
            
            switch(entity) {
                case "author":
                    if (values.size() < 3) {
                        System.out.println(HELP_PERSIST);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    String name = values.get(1).trim();
                    String lastName = values.get(2).trim();
                    
                    databaseManager.persistAuthor(name, lastName);           
                    break;
                    
                case "book":
                    if (values.size() < 5) {
                        System.out.println(HELP_PERSIST);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    String title = values.get(1).trim();
                    Long pages = 0L;
                    Date date;
                    Long authorId = 0L;
                    
                    try {
                        pages = Long.parseLong(values.get(2));
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong nuber of pages parameter!\n");
                        System.out.println(HELP_PERSIST);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                   
                    try {
                        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
                        date = df.parse(values.get(3));
                    } catch (java.text.ParseException ex) {
                        System.out.println("Wrong date format!\n");
                        System.out.println(HELP_PERSIST);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    try {
                        authorId = Long.parseLong(values.get(4));
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong author id!\n");
                        System.out.println(HELP_PERSIST);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    try {                
                        databaseManager.persistBook(title, pages, date, authorId);
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    break;
                    
                default:
                    System.out.println("Wrong entity name!\n");
                    System.out.println(HELP_PERSIST);

                    return ERROR_CODE_OPTION_ERROR;
            }
            
        return ERROR_CODE_OK;
    }

    /**
     * Finds entities that match with specified parameters
     * @param selected the given parameters
     * @return the error code
     */
    private Integer find(Option selected) {
        //DatabaseManager db = new DatabaseManager();
        List<String> values = selected.getValuesList();
        
        if (values.size() < 2) {
            System.out.println(HELP_FIND);
            return ERROR_CODE_OPTION_ERROR;
        }
        
        String entity = values.get(0).toLowerCase().trim();
        String key = values.get(1).toLowerCase().trim();

        switch (entity) {
            case "author":               

                if (key.equals("all")) {
                    for (Object o: databaseManager.findAllAuthors()) {
                        System.out.println(o.toString());
                    }
                } else if (key.equals("id")) {
                    Long id = null;
                    
                    try {
                        id = Long.parseLong(values.get(2));
                    } catch (NumberFormatException ex) {
                        System.out.println("Given id is not an integer number!\n");                        
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    Object o = databaseManager.findAuthorById(id);
                    
                    if (o != null) {
                        System.out.println(o.toString());
                    } else {
                        System.out.println("No author with given id found.\n");
                    }
                    
                } else if (key.equals("name")) {
                    String pattern = values.get(2).trim();
                    
                    for (Object o: databaseManager.findAuthorsByName(pattern)) {
                        System.out.println(o.toString());
                    }
                } else {
                    System.out.println("Wrong search option!\n");
                    System.out.println(HELP_FIND);
                    
                    return ERROR_CODE_OPTION_ERROR;
                }
                
                break;

            case "book":               
                if (key.equals("all")) {
                    for (Object o: databaseManager.findAllBooks()) {
                        System.out.println(o.toString());
                    }
                } else if (key.equals("id")) {
                    Long id = null;
                    
                    try {
                        id = Long.parseLong(values.get(2));
                    } catch (NumberFormatException ex) {
                        System.out.println("Given id is not an integer number!\n");                        
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    Object o = databaseManager.findBookById(id);
                    
                    if (o != null) {
                        System.out.println(o.toString());
                    } else {
                        System.out.println("No book with given id found.\n");
                    }
                    
                } else if (key.equals("title")) {
                    String pattern = values.get(2).trim();
                    
                    for (Object o: databaseManager.findBooksByTitle(pattern)) {
                        System.out.println(o.toString());
                    }
                } else {
                    System.out.println("Wrong search option!\n");
                    System.out.println(HELP_FIND);
                    
                    return ERROR_CODE_OPTION_ERROR;
                }
                break;

            default:
                System.out.println("Wrong entity name!\n");
                System.out.println(HELP_FIND);

                return ERROR_CODE_OPTION_ERROR;
        }
        
        return ERROR_CODE_OK;
    }

    /**
     * Merges entity specified in parameters
     * @param selected the given parameters
     * @return the error code
     */
    private Integer merge(Option selected) {
        String entity = null;
        Long id = null;
        
        //DatabaseManager db = new DatabaseManager();
        List<String> values = selected.getValuesList();
        
        if (values.size() < 2) {
            System.out.println(HELP_MERGE);
            return ERROR_CODE_OPTION_ERROR;
        }
        
        try {
            entity = values.get(0).toLowerCase().trim();
            id = Long.parseLong(values.get(1));
        } catch (NumberFormatException ex) {
            System.out.println("Id parameter is not an integer number!\n");
            return ERROR_CODE_OPTION_ERROR;
        }

        switch(entity) {
            case "author":
                if (values.size() < 4) {
                    System.out.println(HELP_MERGE);
                    return ERROR_CODE_OPTION_ERROR;
                }

                String name = values.get(2).trim();
                String lastName = values.get(3).trim();

                try {
                    databaseManager.mergeAuthor(id, name, lastName);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return ERROR_CODE_OPTION_ERROR;
                }
                break;

            case "book":
                if (values.size() < 6) {
                    System.out.println(HELP_MERGE);
                    return ERROR_CODE_OPTION_ERROR;
                }

                String title = values.get(2).trim();
                Long pages = 0L;
                Date date;
                Long authorId = 0L;

                try {
                    pages = Long.parseLong(values.get(3));
                } catch (NumberFormatException ex) {
                    System.out.println("Wrong nuber of pages parameter!\n");
                    System.out.println(HELP_MERGE);
                    return ERROR_CODE_OPTION_ERROR;
                }

                try {
                    DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
                    date = df.parse(values.get(3));
                } catch (java.text.ParseException ex) {
                    System.out.println("Wrong date format!\n");
                    System.out.println(HELP_MERGE);
                    return ERROR_CODE_OPTION_ERROR;
                }

                try {
                    authorId = Long.parseLong(values.get(5));
                } catch (NumberFormatException ex) {
                    System.out.println("Author id is not an integer number!\n");
                    System.out.println(HELP_MERGE);
                    return ERROR_CODE_OPTION_ERROR;
                }

                try {
                    databaseManager.mergeBook(id, title, pages, date, authorId);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return ERROR_CODE_OPTION_ERROR;
                }
                break;

            default:
                System.out.println("Wrong entity name!\n");
                System.out.println(HELP_MERGE);

                return ERROR_CODE_OPTION_ERROR;
        }
        
        return ERROR_CODE_OK;
    }

    /**
     * Removes entity specified in parameters
     * @param selected the given parameters
     * @return the error code
     */
    private Integer remove(Option selected) {
        String entity = null;
        Long id = null;
        
        //DatabaseManager db = new DatabaseManager();
        List<String> values = selected.getValuesList();
        
        if (values.size() < 2) {
            System.out.println(HELP_REMOVE);
            return ERROR_CODE_OPTION_ERROR;
        }
        
        try {
            entity = values.get(0).toLowerCase().trim();
            id = Long.parseLong(values.get(1));
        } catch (NumberFormatException ex) {
            System.out.println("Id parameter is not an integer number!\n");
            return ERROR_CODE_OPTION_ERROR;
        }

        switch (entity) {
            case "author":
                try {
                    databaseManager.removeAuthor(id);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return ERROR_CODE_OPTION_ERROR;
                }
                break;

            case "book":
                try {
                    databaseManager.removeBook(id);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return ERROR_CODE_OPTION_ERROR;
                }
                break;

            default:
                System.out.println("Wrong entity name!\n");
                System.out.println(HELP_REMOVE);

                return ERROR_CODE_OPTION_ERROR;
        }
        
        return ERROR_CODE_OK;
    }
    
}
