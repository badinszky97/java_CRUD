
package MNB_soap;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.2
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "string", targetNamespace = "http://schemas.microsoft.com/2003/10/Serialization/")
public class MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private String faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage(String message, String faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage(String message, String faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: java.lang.String
     */
    public String getFaultInfo() {
        return faultInfo;
    }

}
