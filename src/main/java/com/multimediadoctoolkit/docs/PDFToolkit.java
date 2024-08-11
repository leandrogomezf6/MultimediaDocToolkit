/**
 * Este libreria proporciona metodos prefabricados para una mejor y facil manipulación
 * de documentos y archivos multimedia.
 * 
 */

package com.multimediadoctoolkit.docs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author Leandro Gómez.
 * @version 0.0.1
 * @since 0.0.1
 */
public class PDFToolkit {

    private File inputFile;
    private String outputFilePath;
    private String outputFileName;
    
    
    /**
     * Constructor de la clase.
     * @since 0.0.1
     * @param inputFile Documento PDF a cargar.
     * @param outputFilePath Ruta de destino del documento resultante.
     * @param outputFileName Nombre del documento resultante.
     */
    public PDFToolkit(File inputFile, String outputFilePath, String outputFileName) {
        this.inputFile = inputFile;
        this.outputFilePath = outputFilePath;
        this.outputFileName = outputFileName;
    }
    
    
    // Convert to PNG.
    
    /**
     * Metodo para convertir un PDF en varias imagenes PNG.
     * @since 0.0.1
     * @param dpi Calidad de la imagen entre 300-1200 recomendado.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void converToPng(int dpi) throws IOException{
        PDDocument document = PDDocument.load(inputFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        
        for (short page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage img = pdfRenderer.renderImageWithDPI(page, dpi);
            File outputFile = new File(outputFilePath+ "/" +outputFileName +page+ ".png");
            ImageIO.write(img, "png", outputFile);
        }
        document.close();
    }
    
    
    /**
     * Metodo para convertir una pagina del documento a PNG.
     * @since 0.0.1
     * @param page Numero de la pagina la cual se desea convertir.
     * @param dpi Calidad de la imagen entre 300-1200 recomendado.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void converToPng(int page, int dpi) throws IOException{
        PDDocument document = PDDocument.load(inputFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        
        BufferedImage img = pdfRenderer.renderImageWithDPI(page, dpi);
        File outputFile = new File(outputFilePath+ "/" +outputFileName+ ".png");
        ImageIO.write(img, "png", outputFile);
        document.close();
    }
    
    
    /**
     * Metodo para convertir un rango de paginas del documento a PNG.
     * @since 0.0.1
     * @param startPage Numero de la pagina inicial.
     * @param endPage Numero de la pagina final.
     * @param dpi Calidad de la imagen entre 300-1200 recomendado.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void converToPng(int startPage, int endPage, int dpi) throws IOException{
        PDDocument document = PDDocument.load(inputFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        
        while (startPage <= endPage) {
            BufferedImage img = pdfRenderer.renderImageWithDPI(startPage, dpi);
            File outputFile = new File(outputFilePath+ "/" +outputFileName +startPage+ ".png");
            ImageIO.write(img, "png", outputFile);
            startPage++;
        }
        document.close();
    }
    
    
    // Etract text.
    
    /**
     * Metodo para extraer el texto.
     * @since 0.0.1
     * @return Retorna el texto como un String.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public String extractText() throws IOException{
        PDDocument document = new PDDocument().load(inputFile);
        PDFTextStripper extractor = new PDFTextStripper();
        final String text = extractor.getText(document);
        document.close();
        
        return text;
    }
    
    // Extract images.
    
    /**
     * Metodo para extraer imagenes de todo el documento.
     * @since 0.0.1
     * @param dpi Calidad de la imagen entre 300-1200 recomendado.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void extractImages(int dpi) throws IOException{
        try (PDDocument document = new PDDocument().load(inputFile)) {
            PDFRenderer renderer = new PDFRenderer(document);
            
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage img = renderer.renderImageWithDPI(i, dpi);
                File outputFile = new File(outputFilePath+ "/" +outputFileName +i+ ".png");
                ImageIO.write(img, "PNG", outputFile);
            }
        }
    }
    
    /**
     * Metodo para extraer imagenes de una pagina espesifica.
     * @since 0.0.1
     * @param page Numero de la pagina a la cual se quiera extraer la imagen.
     * @param dpi Calidad de la imagen entre 300-1200 recomendado.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void extractImages(int page, int dpi) throws IOException{
        try (PDDocument document = new PDDocument().load(inputFile)) {
            PDFRenderer renderer = new PDFRenderer(document);
            
            BufferedImage img = renderer.renderImageWithDPI(page, dpi);
            File outputFile = new File(outputFilePath+ "/" +outputFileName+ ".png");
            ImageIO.write(img, "PNG", outputFile);
        }
    }
    
    /**
     * Metodo para extraer imagenes en un rango de paginas.
     * @since 0.0.1
     * @param startPage Numero de la pagina inicial.
     * @param endPage Numero de la pagina final.
     * @param dpi Calidad de la imagen entre 300-1200 recomendado.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void extractImages(int startPage, int endPage, int dpi) throws IOException{
        PDDocument document = new PDDocument().load(inputFile);
        PDFRenderer renderer = new PDFRenderer(document);
        
        while (startPage <= endPage) {
            BufferedImage img = renderer.renderImageWithDPI(startPage, dpi);
            File outputFile = new File(outputFilePath+ "/" +outputFileName +startPage+ ".png");
            ImageIO.write(img, "PNG", outputFile);
            startPage++;
        }
        document.close();
    }
    
    // Split.
    
    /**
     * Metodo para dividir las paginas de un documento.
     * @since 0.0.1
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void split() throws IOException{
        PDDocument document = new PDDocument().load(inputFile);
        
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            try (PDDocument newDocument = new PDDocument()) {
                PDPage page = newDocument.getPage(i);
                newDocument.addPage(page);
                
                File outputFile = new File(outputFilePath+ "/" +outputFileName +i+ ".pdf");
                newDocument.save(outputFile);
            }
        }
        document.close();
    }
    
    /**
     * Metodo para extraer una pagina de un documento.
     * @since 0.0.1
     * @param page Numero de la agina a extraer.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void split(int page) throws IOException{
        try (PDDocument document = new PDDocument().load(inputFile)) {
            PDPage pdPage = document.getPage(page);
            document.addPage(pdPage);
            
            File outputFile = new File(outputFilePath+ "/" +outputFileName+ ".pdf");
            document.save(outputFile);
        }
    }
    
    /**
     * Metodo para extraer un rango de paginas de un documento.
     * @since 0.0.1
     * @param startPage Numero de la pagina inicial.
     * @param endPage Numero de la pagina final.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void split(int startPage, int endPage) throws IOException{
        PDDocument document = new PDDocument().load(inputFile);
        
        while (startPage <= endPage) {
            try (PDDocument newDocument = new PDDocument()) {
                PDPage page = newDocument.getPage(startPage);
                newDocument.addPage(page);
                
                File outputFile = new File(outputFilePath+ "/" +outputFileName +startPage+ ".pdf");
                newDocument.save(outputFile);
            }
            startPage++;
        }
        document.close(); 
    }
    
    // Merge.
    
    /**
     * Metodo para juntar varios documentos.
     * @since 0.0.1
     * @param inputFiles Array que contendra los documentos a juntar.
     * @throws IOException Este metodo lanza una excepción IOException. 
     */
    public void merge(File[] inputFiles) throws IOException{
        PDFMergerUtility merger = new PDFMergerUtility();
        MemoryUsageSetting memory = MemoryUsageSetting.setupMainMemoryOnly();
        
        for (File file : inputFiles) {merger.addSource(file);}

        merger.setDestinationFileName(outputFilePath+ "/" +outputFileName+ ".pdf");
        merger.mergeDocuments(memory);
    }
}
