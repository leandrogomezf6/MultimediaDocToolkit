/**
 * Este libreria proporciona metodos prefabricados para una mejor y facil manipulación
 * de documentos y archivos multimedia.
 * 
 */

package com.multimediadoctoolkit.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.JSONObject;

/**
 *
 * @author Leandro Gómez.
 * @version 0.0.1
 * @since 0.0.1
 */
public class MetadataToolkit {
    
    /**
     * Metodo para optener los metadatos de Archivos multimedia.
     * @since 0.0.1
     * @param inputFile Archivo multimedia del cual se quiera optener los metadatos.
     * @param outputFile Archivo donde se guardara los metadatos en formato JSON.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public static void getMultiMetadata(File inputFile, File outputFile) throws IOException {
        try(FileWriter writer = new FileWriter(outputFile)) 
        {
           Metadata metadata = ImageMetadataReader.readMetadata(inputFile);
           JSONObject jsonMetadata = new JSONObject();
           
            for (Directory directory : metadata.getDirectories()) {
                for (int tag = 0; tag <= directory.getTagCount(); tag++) 
                {
                    String tagName = directory.getTagName(tag);
                    String description = directory.getDescription(tag);
                    jsonMetadata.put(tagName, description);
                }
            }

            writer.write(jsonMetadata.toString(2));
            
        } catch (ImageProcessingException ex) {
           ex.printStackTrace();

        }
    }
    
    /**
     * Metodo para optener los metadatos de documentos PDF.
     * @since 0.0.1
     * @param inputFile Archivo PDF del cual se quiera optener los metadatos.
     * @param outputFile Archivo donde se guardara los metadatos en formato JSON.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public static void getPdfData(File inputFile, File outputFile) throws IOException {
        try(FileWriter writer = new FileWriter(outputFile)) 
        {
           PDDocument document = PDDocument.load(inputFile);
           JSONObject jsonMetadata = new JSONObject();
           
           Set<String> metadataKeys = document.getDocumentInformation().getMetadataKeys();
           
            for (String key : metadataKeys) {
                String description = document.getDocumentInformation().getCustomMetadataValue(key);
                jsonMetadata.put(key, description);
            }

            writer.write(jsonMetadata.toString(2)); 
        }
    }
    
}
