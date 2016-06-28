/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jparser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jparser.io.FactoryFileParse;
import jparser.pojo.Customer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author srey
 */
public class JParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Options options= new Options();
        CommandLineParser parser= new DefaultParser();
        
        options.addOption(Option.builder().
                longOpt("to").
                desc("Indica el tipo de archivo al que debera convertir: JSON / XML").
                hasArg().
                argName("tipo").
                build());
        
        options.addOption(Option.builder().
                longOpt("path").
                desc("Indica la ruta donde se encuentra el archivo origen").
                hasArg().
                argName("origen").
                build());
        
        options.addOption(Option.builder().
                longOpt("target").
                desc("Indica la ruta donde se guardara el archivo resultante").
                hasArg().
                argName("destino").
                build());
        
        options.addOption("h", "help", false, "Muestra la guia de como usar la aplicacion");
        
        try {
            CommandLine command= parser.parse(options, args);
            Path source= null;
            Path target= null;
            FactoryFileParse.TypeParce type= FactoryFileParse.TypeParce.NULL;
            Optional<Customer> customer= Optional.empty();
            
            if (command.hasOption("h")) {
                HelpFormatter helper= new HelpFormatter();
                helper.printHelp("JParser", options);
                
                System.exit(0);
            }
            
            if (command.hasOption("to"))
                type= FactoryFileParse.TypeParce.fromValue(command.getOptionValue("to", ""));
            
            if (command.hasOption("path"))
                source= Paths.get(command.getOptionValue("path", ""));
            
            if (command.hasOption("target"))
                target= Paths.get(command.getOptionValue("target", ""));
            
            switch (type) {
                case JSON:
                  customer  = FactoryFileParse.
                            createNewInstance(FactoryFileParse.TypeParce.XML).
                            read(source);
                    
                    break;
                
                case XML:
                    customer  = FactoryFileParse.
                            createNewInstance(FactoryFileParse.TypeParce.JSON).
                            read(source);
                    
                    break;
            }
            
            if (customer.isPresent()) {
                Customer c= customer.get();
                
                boolean success= FactoryFileParse.
                        createNewInstance(type).
                        write(c, target);

                System.out.println(String.format("Operatation was: %s", success ? "success" : "fails"));
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(JParser.class.getSimpleName()).log(Level.SEVERE, ex.getMessage(), ex);
            
            System.exit(-1);
        }
    }
    
}
