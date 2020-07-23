package com.gplts.util.files;


import java.io.File;

import com.gplts.datatypes.either.Either;


/**
 * 
 * Facility for deleting files, safely without try-catch blocks
 * 
 * @author Tsvetelin
 *
 */
public interface FileDeleter
{
    
    /**
     * 
     * Deletes the file contained in the object
     * 
     * @param file
     *            to be deleted
     * @return a boolean denotating if the file was deleted in the left case or
     *         an exception in the right if occurred
     */
    public static Either< Boolean , Exception > delete ( final File file )
    {
        try
        {
            if ( file.exists() ) Either.left( file.delete() );
        } catch ( Exception e )
        {
            return Either.right( e );
        }
        return Either.left( false ); // this should be here cuz the file might
                                     // not exist in that case it is not deleted
    }
    
    /**
     * 
     * Deletes the file annotated by the path
     * 
     * @param path
     *            to the file to be deleted
     * @return a boolean denotating if the file was deleted in the left case or
     *         an exception in the right if occurred
     */
    public static Either< Boolean , Exception > delete ( final String path )
    {
        return delete( new File( path ) );
    }
}
