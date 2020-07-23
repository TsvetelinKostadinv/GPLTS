package com.gplts.util.files;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.gplts.datatypes.either.Either;

/**
 * Facility for writing to files, safely without try-catch blocks
 * @author Tsvetelin
 *
 */
public interface FileWriter
{
    
    /**
     * 
     * Writes to the file in the File variable
     * 
     * @param file
     *            - to be written to
     * @param str
     *            - string to be written
     * @param append
     *            - whether or not it should append at the end of the file
     * @return the file to which was written
     * @throws IOException
     */
    public static Either< File , IOException > writeToFile (
        final File file ,
        final String str ,
        final boolean append
    )
    {
        try (
            final PrintWriter out =
                new PrintWriter(
                    new BufferedWriter( new java.io.FileWriter( file , append ) )
                )
        )
        {
            out.print( str );
            out.close();
        } catch ( IOException e )
        {
            Either.right( e );
        }
        
        return Either.left( file );
    }
    
    /**
     * 
     * Writes to the file annotated by the path
     * 
     * @param path
     *            to the file
     * @param string
     *            to be written
     * @param whether
     *            or not it should append at the end of the file
     * @return the file to which was written
     * @throws IOException
     */
    public static Either< File , IOException > writeToFile (
        final String path ,
        final String str ,
        final boolean append
    )
    {
        return writeToFile( new File( path ) , str , append );
    }
}
