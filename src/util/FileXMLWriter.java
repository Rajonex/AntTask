package util;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileXMLWriter {

	public static void createAndWriteFile(String fileName, List<MetalinkFile> metalinks) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("metalink");
			doc.appendChild(rootElement);

			Calendar calendar = Calendar.getInstance();

			Element publishedDate = doc.createElement("published");
			publishedDate.appendChild(doc.createTextNode(calendar.getTime().toString()));
			rootElement.appendChild(publishedDate);

			for(MetalinkFile meta : metalinks)
			{
				Element file = doc.createElement("file");
				Attr attrFile = doc.createAttribute("name");
				attrFile.setValue(meta.getName());
				file.setAttributeNode(attrFile);
				rootElement.appendChild(file);
				
				Element size = doc.createElement("size");
				size.appendChild(doc.createTextNode(Long.toString(meta.getSize())));
				file.appendChild(size);
				
				Element md5 = doc.createElement("hash");
				Attr attrHash = doc.createAttribute("type");
				attrHash.setValue("md5");
				md5.setAttributeNode(attrHash);
				md5.appendChild(doc.createTextNode(meta.getMD5()));
				file.appendChild(md5);
				
				Element url = doc.createElement("url");
				url.appendChild(doc.createTextNode(meta.getUrl()));
				file.appendChild(url);
			}


			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));

			transformer.transform(source, result);


		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
