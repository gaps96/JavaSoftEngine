package eu.greenlightning.softengine.geometry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

import org.junit.Test;

import eu.greenlightning.softengine.geometry.Face;
import eu.greenlightning.softengine.geometry.Mesh;
import eu.greenlightning.softengine.math.Vector3;

public class MeshFactoryTest {

	public MeshFactoryTest() {
		// TODO Auto-generated constructor stub
	}
		
	// @Test
	public Mesh testParseBlenderJsonFile() {
		String JSON_FILE = "./monkey.babylon";
        try {
			InputStream fis = new FileInputStream(JSON_FILE);
			
			//create JsonReader object 
	        JsonReader jsonReader = Json.createReader(fis);
        	JsonObject jsonObject = jsonReader.readObject();
        	
            // get a String from the JSON object
	        String autoClear = jsonObject.get("autoClear").toString();
	        System.out.println("The autoClear is: " + autoClear);
		
	        JsonArray meshes = jsonObject.getJsonArray("meshes");
	        for(int i=0; i<meshes.size(); i++){
	        	System.out.println("The mesh " + i + " is: " + meshes.get(i));
	        	JsonObject meshObj = (JsonObject) meshes.get(i);
	        	
	        	JsonArray meshesVertices = meshObj.getJsonArray("vertices");
	        	JsonArray meshesIndices = meshObj.getJsonArray("indices");

	        	int uvCount = Integer.parseInt(meshObj.get("uvCount").toString());
		        System.out.println("The uvCount is: " + uvCount);	        	
	        	
	        	int verticesStep = 1;
	            // Depending of the number of texture's coordinates per vertex
	            // we're jumping in the vertices array  by 6, 8 & 10 windows frame
	            switch ((int)uvCount) {
	                case 0:
	                    verticesStep = 6;
	                    break;
	                case 1:
	                    verticesStep = 8;
	                    break;
	                case 2:
	                    verticesStep = 10;
	                    break;
	            }	        	
	            
	            // the number of interesting vertices information for us
	            int verticesCount = meshesVertices.size() / verticesStep;
	            // number of faces is logically the size of the array divided by 3 (A, B, C)
	            int facesCount = meshesIndices.size() / 3;
	            
	            Mesh mesh = new Mesh(meshObj.get("name").toString());
            	Vector3 [] meshVertices = new Vector3[verticesCount];
            	Face[] meshFaces = new Face[facesCount];	            
	            
		        for(int iV=0; iV < verticesCount; iV++){
		        	// float vertice = Float.parseFloat(meshesVertices.get(iV).toString());
		            float x = Float.parseFloat(meshesVertices.get(iV * verticesStep).toString());
		            float y = Float.parseFloat(meshesVertices.get(iV * verticesStep + 1).toString());
		            float z = Float.parseFloat(meshesVertices.get(iV * verticesStep + 2).toString());
		            meshVertices[iV] = new Vector3(x, y, z);
		        	
		        	System.out.println("Vertice: " + meshVertices[iV]);
		        }
		        for(int iI=0; iI < facesCount; iI++){
		        	// int indice = Integer.parseInt(meshesIndices.get(iI).toString());
		            int a = Integer.parseInt(meshesIndices.get(iI * 3).toString());
		            int b = Integer.parseInt(meshesIndices.get(iI * 3 + 1).toString());
		            int c = Integer.parseInt(meshesIndices.get(iI * 3 + 2).toString());
		            meshFaces[iI] = new Face(a,b,c);
		            
		        	System.out.println("Face: " + meshFaces[iI]);
		        }
		        mesh.setVertices(meshVertices);
		        mesh.setFaces(meshFaces);
	        	return mesh;
	        }
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		
	}
}
