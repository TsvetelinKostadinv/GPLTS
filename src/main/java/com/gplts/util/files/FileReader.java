package com.gplts.util.files;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.gplts.datatypes.either.Either;


/**
 * Facility for reading files, safely without try-catch blocks
 * 
 * @author Tsvetelin
 *
 */
public interface FileReader
{
    
    /**
     * 
     * Reads the whole file.
     * 
     * @param file
     * @return the whole file as a String on the left or an exception on the
     *         right if any occurred
     */
    public static Either< String , IOException > readFile ( final File file )
    {
        final StringBuilder lines = new StringBuilder();
        
        try (
            final BufferedReader reader =
                new BufferedReader(
                    new java.io.FileReader( file )
                )
        )
        {
            String line = reader.readLine();
            
            while ( line != null )
            {
                lines.append( line );
                lines.append( System.lineSeparator() );
                line = reader.readLine();
            }
        } catch ( IOException e )
        {
            return Either.right( e );
        }
        
        return Either.left( lines.toString() );
    }
    
    /**
     * 
     * Reads the whole file.
     * 
     * @param path
     *            to the file to be read
     * @return the whole file as a String on the left or an exception on the
     *         right if any occurred
     */
    public static Either< String , IOException > readFile ( final String path )
    {
        return readFile( new File( path ) );
    }
    
    /**
     * 
     * Reads the specified line of the file contained in the File variable
     * 
     * @param file
     *            to be read
     * @param line
     *            to be read
     * @return the specified line as a String on the left or an exception on the
     *         right if any occurred
     */
    public static Either< String , Exception > readLineFromFile (
        final File file ,
        final int line
    )
        throws IOException
    {
        try
        {
            return Either
                .left( Files.lines( file.toPath() ).skip( line - 1 ).findFirst().get() );
        } catch ( Exception e )
        {
            return Either.right( e );
        }
    }
    
    /**
     * 
     * Reads the specified line of the file annotated by the path
     * 
     * @param path
     *            to the file to be read
     * @param line
     *            to be read
     * @return the specified line as a String on the left or an exception on the
     *         right if any occurred
     */
    public static Either< String , Exception > readLineFromFile (
        final String path ,
        final int line
    )
        throws IOException
    {
        return readLineFromFile( new File( path ) , line );
    }
}
