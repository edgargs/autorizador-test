import spock.lang.Specification
import groovy.xml.StreamingMarkupBuilder
import java.util.ArrayList

class AutorizadorSpec extends Specification {
	
	void 'operaciones echo'() {
    	given:
    		def classUnderTest = new Autorizador()
    		def xml = new StreamingMarkupBuilder().bind { 
			    Messages {
			        TXN_FIN_REQ { 
			            TRACE(value:'000123')
			            MESSAGE_TYPE(value:'0800')
			        }
			    }
			}
			def trama = xml.toString()
			def canal = "PMPVG"
			def parametros = new ArrayList<String>()
			parametros.add(trama)
			println trama
    	when:
    		def echo = classUnderTest.operaciones(canal,parametros)
    		def Messages = new XmlSlurper().parseText(echo) 
    	then: 
    		Messages.TXN_FIN_RES.MESSAGE_TYPE.@value == '0810'
    		Messages.TXN_FIN_RES.RESP_CODE.@value == '00'
    		Messages.TXN_FIN_RES.TRACE.@value == '000123'
    		
    }
}