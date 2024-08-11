/**
 * Este libreria proporciona metodos prefabricados para una mejor y facil manipulación
 * de documentos y archivos multimedia.
 * 
 */

package com.multimediadoctoolkit.multimedia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Leandro Gómez.
 * @version 0.0.1
 * @since 0.0.1
 */
public class ImageToolkit {
    
    public static final String JPEG = "jpeg";
    public static final String PNG = "png";
    public static final String GIF = "gif";
    public static final String BMP = "bmp";
    public static final String WBMP = "wbmp";
    public static final String TIFF = "tiff";
    private String imgName = "";
    private File inputImage;
    private String outputImagePath;
    
    /**
     * Contrctor de la clase.
     * @since 0.0.1
     * @param imputImage Imagen a procesar.
     * @param outputImagePath Ruta donde se guardara la imagen.
     * @param imgName Nombre de la imagen resultante.
     */
    public ImageToolkit(File imputImage, String outputImagePath, String imgName){
        this.inputImage = imputImage;
        this.outputImagePath = outputImagePath;
        this.imgName = imgName;
    }
    
    /**
     * Metodo para transformar el formato de una imagen.
     * @since 0.0.1
     * @param format Formato al cual se quiere convertir la imagen.
     * <br/> JPEG.
     * <br/> PNG.
     * <br/> GIF.
     * <br/> BMP.
     * <br/> WBMP.
     * <br/> TIFF.
     * @throws java.io.IOException Este metodo lanza una excepción IOException.
     */
    public void convertImgFormatTo(String format) throws IOException{
        
        BufferedImage buffer = ImageIO.read(inputImage);
        File outputImage = new File(outputImagePath+ "/" +imgName+ "." +format);
        ImageIO.write(buffer, format, outputImage);
    }
}


