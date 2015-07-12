package eu.greenlightning.softengine.geometry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import eu.greenlightning.softengine.math.Vector3;

public final class MeshFactory {

	public static Mesh createCube(double sideLength) {
		double halfLength = sideLength * 0.5;
		Vector3[] vertices = new Vector3[8];
		vertices[0] = new Vector3(+halfLength, +halfLength, +halfLength);
		vertices[1] = new Vector3(+halfLength, +halfLength, -halfLength);
		vertices[2] = new Vector3(+halfLength, -halfLength, +halfLength);
		vertices[3] = new Vector3(+halfLength, -halfLength, -halfLength);
		vertices[4] = new Vector3(-halfLength, +halfLength, +halfLength);
		vertices[5] = new Vector3(-halfLength, +halfLength, -halfLength);
		vertices[6] = new Vector3(-halfLength, -halfLength, +halfLength);
		vertices[7] = new Vector3(-halfLength, -halfLength, -halfLength);
		Face[] faces = new Face[2 * 6];

		// right face
		faces[0] = new Face(0, 1, 2);
		faces[1] = new Face(1, 3, 2);

		// left face
		faces[2] = new Face(5, 4, 7);
		faces[3] = new Face(4, 6, 7);

		// top face
		faces[4] = new Face(5, 1, 4);
		faces[5] = new Face(1, 0, 4);

		// bottom face
		faces[6] = new Face(6, 2, 7);
		faces[7] = new Face(2, 3, 7);

		// front face
		faces[8] = new Face(4, 0, 6);
		faces[9] = new Face(0, 2, 6);

		// back face
		faces[10] = new Face(1, 5, 3);
		faces[11] = new Face(5, 7, 3);
		return new Mesh(vertices, faces);
	}

	public static Mesh createSquarePyramid(double sideLength, double height) {
		double halfLength = sideLength * 0.5;
		Vector3[] vertices = new Vector3[5];
		vertices[0] = new Vector3(0, height, 0);
		vertices[1] = new Vector3(+halfLength, 0, +halfLength);
		vertices[2] = new Vector3(+halfLength, 0, -halfLength);
		vertices[3] = new Vector3(-halfLength, 0, +halfLength);
		vertices[4] = new Vector3(-halfLength, 0, -halfLength);
		Face[] faces = new Face[6];

		// base
		faces[0] = new Face(3, 1, 4);
		faces[1] = new Face(1, 2, 4);

		// sides
		faces[2] = new Face(0, 2, 1);
		faces[3] = new Face(0, 4, 2);
		faces[4] = new Face(0, 3, 4);
		faces[5] = new Face(0, 1, 3);
		return new Mesh(vertices, faces);
	}

	/**
	 * Parses a blender file previously exported in Json format.
	 * @param path The full path to the json file
	 * @return a Mesh.
	 */
	public static Mesh parseBlenderJsonFile(String path) {
        try {
			InputStream fis = new FileInputStream(path);
			
			//create JsonReader object 
	        JsonReader jsonReader = Json.createReader(fis);
        	JsonObject jsonObject = jsonReader.readObject();
        	
	        JsonArray meshes = jsonObject.getJsonArray("meshes");
	        for(int i=0; i<meshes.size(); i++){
	        	JsonObject meshObj = (JsonObject) meshes.get(i);	        	
	        	JsonArray meshesVertices = meshObj.getJsonArray("vertices");
	        	JsonArray meshesIndices = meshObj.getJsonArray("indices");
	        	int uvCount = Integer.parseInt(meshObj.get("uvCount").toString());	       
	        	int verticesStep = 1;
	        	
	            // Depending of the number of texture's coordinates per vertex
	            // we're jumping in the vertices array  by 6, 8 & 10 windows frame
	            switch ((int)uvCount) {
	                case 0: verticesStep = 6; break;
	                case 1: verticesStep = 8; break;
	                case 2: verticesStep = 10; break;
	            }	        	
	            
	            // the number of interesting vertices information for us
	            int verticesCount = meshesVertices.size() / verticesStep;
	            
	            // number of faces is logically the size of the array divided by 3 (A, B, C)
	            int facesCount = meshesIndices.size() / 3;
	            
	            Mesh mesh = new Mesh(meshObj.get("name").toString());
            	Vector3 [] meshVertices = new Vector3[verticesCount];
            	Face[] meshFaces = new Face[facesCount];	            
	            
		        for(int iV=0; iV < verticesCount; iV++){
		            float x = Float.parseFloat(meshesVertices.get(iV * verticesStep).toString());
		            float y = Float.parseFloat(meshesVertices.get(iV * verticesStep + 1).toString());
		            float z = Float.parseFloat(meshesVertices.get(iV * verticesStep + 2).toString());
		            meshVertices[iV] = new Vector3(x, y, z);
		        }

		        for(int iI=0; iI < facesCount; iI++){
		        	// int indice = Integer.parseInt(meshesIndices.get(iI).toString());
		            int a = Integer.parseInt(meshesIndices.get(iI * 3).toString());
		            int b = Integer.parseInt(meshesIndices.get(iI * 3 + 1).toString());
		            int c = Integer.parseInt(meshesIndices.get(iI * 3 + 2).toString());
		            meshFaces[iI] = new Face(a,b,c);
		        }
		        mesh.setVertices(meshVertices);
		        mesh.setFaces(meshFaces);
	        	return mesh;
	        }
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;				
	}
	
	private MeshFactory() {}

}
