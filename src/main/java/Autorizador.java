import java.util.List;

import org.apache.xmlbeans.*; 

public class Autorizador {
	
	public Autorizador() {
		
	}

	public String operaciones(String canal, List<String> parametros) {
		
		System.out.println("Canal: " + canal);
		String trama = "";
		String strMessageType = "", strTrace = "";
		
		try{
			XmlObject xmlObject = XmlObject.Factory.parse(parametros.get(0));
			
			XmlObject[] results = xmlObject.selectPath("./Messages/TXN_FIN_REQ/MESSAGE_TYPE/@value");
			strMessageType = results[0].getDomNode().getNodeValue();
			
			results = xmlObject.selectPath("./Messages/TXN_FIN_REQ/TRACE/@value");
			strTrace = results[0].getDomNode().getNodeValue();
			
		}catch(XmlException e) {
			e.printStackTrace();
		}
		
		switch(strMessageType){
			case "0800": trama = messageEcho(strTrace);
		}
	    
		return trama;
	}

	private String messageEcho(String strTrace) {
		String trama;
		
		XmlObject xo = XmlObject.Factory.newInstance();

		XmlCursor cursor = xo.newCursor();
		
        cursor.toNextToken();
        cursor.beginElement("Messages");
        
        cursor.beginElement("TXN_FIN_RES");
        
        cursor.beginElement("MESSAGE_TYPE");
        cursor.insertAttributeWithValue("value", "0810");
        
        cursor.toNextToken();
        cursor.beginElement("RESP_CODE");
        cursor.insertAttributeWithValue("value", "00");
        
        cursor.toNextToken();
        cursor.beginElement("TRACE");
        cursor.insertAttributeWithValue("value", strTrace);

        cursor.dispose();
	    trama = xo.toString();
	    
	    return trama;
	}
}
