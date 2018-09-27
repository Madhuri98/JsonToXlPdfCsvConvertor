/*
package clock.alarm.com.xltojsonconvertor.model;

*/
/**
 * Created by Madhuri on 26/09/18.
 *//*

package com.linkwithweb.converter;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

*/
/**
 * Prerequisites
 * %OPENOFFICE_HOME%/soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
 *
 * @author Ashwin Kumar
 *
 *//*

public class OpenOfficeConverter {
    // connect to an OpenOffice.org instance running on port 8100
    private OpenOfficeConnection connection = null;

    */
/**
     * @param oHTMLText
     * @param oOutputStream
     *//*

    public void convertFromHTMLToPDF(String oHTMLText,
                                     OutputStream oOutputStream) {
        try {
            DocumentFormat inputDocumentFormat = new DocumentFormat("HTML",
                    DocumentFamily.TEXT, "text/html", "html");
            inputDocumentFormat.setExportFilter(DocumentFamily.TEXT,
                    "HTML 	(StarWriter)");
            DocumentFormat outputDocumentFormat = new DocumentFormat(
                    "Portable	Document Format", DocumentFamily.TEXT,
                    "application/pdf", "pdf");
            outputDocumentFormat.setExportFilter(DocumentFamily.TEXT,
                    "writer_pdf_Export");
            DocumentConverter oDocumentConverter = new OpenOfficeDocumentConverter(
                    connection);
            oDocumentConverter.convert(
                    new ByteArrayInputStream(oHTMLText.getBytes()),
                    inputDocumentFormat, oOutputStream, outputDocumentFormat);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    */
/**
     *
     *//*

    public void openConnection() {
        try {
            if (connection == null || !connection.isConnected())
                connection = new SocketOpenOfficeConnection(8100);
            connection.connect();
        } catch (ConnectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    */
/**
     *
     *//*

    public void openConnection(String host, int port) {
        try {
            if (connection == null || !connection.isConnected())
                connection = new SocketOpenOfficeConnection(host, port);
            connection.connect();
        } catch (ConnectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    */
/**
     *
     *//*

    public void closeConnection() {
        // close the connection
        try {
            if (connection != null && connection.isConnected())
                connection.disconnect();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
    Now to Use above Method Here is sample Code

*/
/**
 *
 *//*

        package com.linkwithweb.converter;

        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.OutputStream;

*/
/**
 * @author Ashwin Kumar
 *
 *//*

public class HTMLToPDFConverter {

    public static void main(String[] args) {

        String strHindiHTML = "Hello hi tehrere<h1>heading</h1>";
        OpenOfficeConverter oOpenOfficeConverter = new OpenOfficeConverter();
        oOpenOfficeConverter.openConnection();
        OutputStream out = null;
        try {
            out = new FileOutputStream("test.pdf");

            oOpenOfficeConverter.convertFromHTMLToPDF(strHindiHTML, out);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Throwable th) {
            // TODO Auto-generated catch block
            th.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        oOpenOfficeConverter.closeConnection();
        */
/*
         * ByteArrayOutputStream oByteArrayOutputStream = new ByteArrayOutputStream();
         *
         * ByteArrayInputStream oByteArrayInputStream = new ByteArrayInputStream(
         * oByteArrayOutputStream.toByteArray());
         *//*

    }

}
*/
