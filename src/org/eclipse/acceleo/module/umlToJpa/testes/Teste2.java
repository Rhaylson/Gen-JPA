package org.eclipse.acceleo.module.umlToJpa.testes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.module.umlToJpa.controle.CascadeFetchControlador;
import org.eclipse.acceleo.module.umlToJpa.controle.InheritanceControlador;
import org.eclipse.acceleo.module.umlToJpa.main.Main;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.modelo.Associacao;
import org.eclipse.acceleo.module.umlToJpa.util.GerenciadorDeElementosDoModelo;
import org.eclipse.acceleo.module.umlToJpa.util.GerenciadorDeTipos;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

public class Teste2 {

	public static void main(String[] args) throws IOException {
		
	/*	ResourceSetImpl resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(org.eclipse.uml2.uml.UMLPackage.eINSTANCE.getNsURI(), org.eclipse.uml2.uml.UMLPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		Map uriMap = resourceSet.getURIConverter().getURIMap();
		
		URI uri = URI.createURI("jar:file:/C:/Users/Rhaylson/OneDrive/workspace/org.eclipse.acceleo.module.umlToJpa/libs/uml.jar!/");
		uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), uri.appendSegment("libraries").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), uri.appendSegment("metamodels").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), uri.appendSegment("profiles").appendSegment(""));
		
		File model=new File("C:\\Users\\Rhaylson\\OneDrive\\workspace\\org.eclipse.acceleo.module.umlToJpa\\modelo\\model2.uml");
        File folder = new File("C:/Users/Rhaylson/OneDrive/workspace/org.eclipse.acceleo.module.umlToJpa/generate");
        EObject obj = ModelUtils.load(model, resourceSet);
        try{
		Main m = new Main(obj, folder, new ArrayList<String>());
		m.doGenerate(new BasicMonitor());
		}catch(AcceleoEvaluationException e){
		}
		*/
		GerenciadorDeTipos gt = GerenciadorDeTipos.getInstance();
		String destino = "C:/Users/Rhaylson/OneDrive/workspace/org.eclipse.acceleo.module.umlToJpa/generate";
	   GerenciadorDeElementosDoModelo gm = new GerenciadorDeElementosDoModelo("List","JOINED",destino);
	   gm.gerarCodigo();
	  // gm.gerarCodigo();
			    
		/*CascadeFetchControlador cfc = CascadeFetchControlador.getInstance();
		for(String s:cfc.getFetch().getValores().keySet()){
			System.out.println(cfc.getFetch().getValores().get(s));
		}*/
	}
}
