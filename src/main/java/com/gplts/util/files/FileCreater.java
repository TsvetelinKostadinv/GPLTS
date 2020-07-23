package com.gplts.util.files;


import java.io.File;
import java.io.IOException;

import com.gplts.datatypes.either.Either;


/**
 * 
 * Facility for crating files, safely without try-catch blocks
 * 
 * @author Tsvetelin
 *
 */
public interface FileCreater
{
    
    /**
     * 
     * Creates the directory contained in the File variable
     * 
     * @param dir
     *            to be created
     * @return the created directory in the left case, or an exception if any occurred
     */
    public static Either< File , Exception > createDirectory (
        final File dir
    )
    {
        try
        {
            if ( !dir.exists() ) dir.mkdirs();
        } catch ( SecurityException e )
        {
            return Either.right( e );
        }
        return Either.left( dir );
    }
    
    /**
     * 
     * Creates the directory annotated by the path in the String
     * 
     * @param path
     *            to the directory to be created
     * @return the created directory in the left case, or an exception if any occurred
     */
    public static Either< File , Exception > createDirectory (
        final String directoryPath
    )
    {
        return createDirectory( new File( directoryPath ) );
    }
    
    /**
     * 
     * Creates the file contained in the File variable
     * 
     * @param file
     *            to be created
     * @return the created file in the left case, or an exception if any occurred
     */
    public static Either< File , Exception > createFile ( final File file )
    {
        return createDirectory( file.getParent() )
            .match(
                lazyDir -> {
                    try
                    {
                        if ( !file.exists() ) file.createNewFile();
                    } catch ( Exception e )
                    {}
                    return Either.left( file );
                } ,
                lazyExcept -> {
                    return Either.right( lazyExcept );
                }
            );
    }
    
    /**
     * 
     * Creates the file annotated by the path in the String
     * 
     * @param path
     *            to the file to be created
     * @return the created file in the left case, or an exception if any occurred
     */
    public static Either< File , Exception > createFile ( final String path ) throws IOException
    {
        return createFile( new File( path ) );
    }
    
}
