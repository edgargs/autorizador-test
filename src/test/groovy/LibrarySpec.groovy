import spock.lang.Specification
import groovy.xml.StreamingMarkupBuilder

class LibrarySpec extends Specification {

	//https://www.genbetadev.com/herramientas/testeando-tus-aplicaciones-java-con-spock-tests-mas-expresivos-faciles-de-leer-y-mantener
	void 'invertir una cadena de texto'() {            // 2
        given: 'una cadena de text'                    // 3
            def miCadena = 'Hola Genbetadev'

        when: 'la invertimos'                          // 4
            def cadenaInvertida = miCadena.reverse()

        then: 'se invierte correctamente'              // 5
            cadenaInvertida == 'vedatebneG aloH'
    }
    
    void 'test someLibrary method'() {
    	when:
    		def classUnderTest = new Library()
    	then: "someLibraryMethod should return 'true'"
    		classUnderTest.someLibraryMethod()
    }
    
    void 'retorna mismo valor'() {
    	given:
    		def classUnderTest = new Library()
    		String trama = "Hola"
    	when:
    		def echo = classUnderTest.echoTrama(trama)
    	then: 
    		echo == trama
    }
    
    void 'echo trama xml'() {
    	given:
    		def classUnderTest = new Library()
    		def xml = new StreamingMarkupBuilder().bind { 
			    Messages {
			        TXN_FIN_REQ { 
			            TRACE(value:'Australia')
			            MESSAGE_TYPE(value:'speed')
			        }
			    }
			}
			def trama = xml.toString()
    	when:
    		def echo = classUnderTest.echoTrama(trama)
    		def Messages = new XmlSlurper().parseText(echo) 
    	then: 
    		Messages.TXN_FIN_REQ.TRACE.@value == 'Australia'
    }
}