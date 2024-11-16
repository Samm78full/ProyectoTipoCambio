import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.StringReader;
import org.xml.sax.InputSource;

@Component
public class SoapClient {
    private static final String SOAP_URL = "https://www.banguat.gob.gt/variables/ws/TipoCambio.asmx";
    private static final String SOAP_ACTION = "http://www.banguat.gob.gt/variables/ws/TipoCambioDiaString";

    public String getTipoCambio() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        headers.set("SOAPAction", SOAP_ACTION);

        String soapEnvelope = 
            "&lt;?xml version=\"1.0\" encoding=\"utf-8\"?&gt;" +
            "&lt;soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"&gt;" +
            "  &lt;soap:Body&gt;" +
            "    &lt;TipoCambioDiaString xmlns=\"http://www.banguat.gob.gt/variables/ws/\" /&gt;" +
            "  &lt;/soap:Body&gt;" +
            "&lt;/soap:Envelope&gt;";

        HttpEntity<String> request = new HttpEntity<>(soapEnvelope, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(SOAP_URL, request, String.class);

        return parseXmlResponse(response.getBody());
    }

    private String parseXmlResponse(String xmlResponse) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

            NodeList nodeList = document.getElementsByTagName("TipoCambioDiaStringResult");
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error al parsear la respuesta XML";
    }
}