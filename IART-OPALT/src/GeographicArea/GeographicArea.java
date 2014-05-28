package GeographicArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import Algorithm.Genetic.Chromosome;
import GeographicArea.Utilities.UtilitieType;
import GrafoStruct.Grafo;
import GrafoStruct.Node;

enum CurrentLoadOperation{
	LOAD_LOTES,
	LOAD_CONNECTIONS,
	LOAD_UTILITIES,
	LOAD_RESTRICTIONS
}

public class GeographicArea extends Grafo{
	
	private Vector<UtilitieType> utilities = new  Vector<UtilitieType>();
	
	
	public GeographicArea() throws IOException{
		
		loadFiles();
		
		Chromosome.setChromosomeSize(vertexSet.size()*utilities.size());
		Chromosome.setChromosomeSector(utilities.size());
		Chromosome.setChromosomeUtilities(utilities);
	}
	
	private void loadFiles() throws IOException{
		
		loadUtilities();
		loadLotes();
	}
	
	private void loadUtilities() throws IOException{
		CurrentLoadOperation loadOperation = null;
		//String[] _temp_str_arr;
		
		BufferedReader fd = new BufferedReader(new FileReader("utilities.txt"));
		
	    try {
	        String line = fd.readLine();

	        while (line != null) {    
	        	
	        	//_temp_str_arr = line.split("//");
	        	
	        	if(line.split("//").length > 1 && line.split("//")[1].equals("Utilities"))
	        		loadOperation = CurrentLoadOperation.LOAD_UTILITIES;
	        	else if(line.split("//").length > 1 &&  line.split("//")[1].equals("Restrictions"))
	        		loadOperation = CurrentLoadOperation.LOAD_RESTRICTIONS;
	        	else{
	        		
	        		if(loadOperation != null)
	        			if(loadOperation == CurrentLoadOperation.LOAD_UTILITIES){
	        				addUtilitie(line);
	        			}
	        			else
	        				if(loadOperation == CurrentLoadOperation.LOAD_RESTRICTIONS){
	        				}
	        	}
	        		
	            line = fd.readLine();
	        }
	        
	    } finally {
	        fd.close();
	    }
	}
	
	public void addUtilitie(String utilitieName){
		utilities.add(new UtilitieType(utilitieName));
	}
	
	public void addLote(Lote lote){
		
		for(Node _temp_lote : this.vertexSet)
			if(_temp_lote.getName() == lote.getName())
				return;
		
		this.vertexSet.add(lote);
	}
	
	private UtilitieType getUtilitie(String utilitieType){
		
		for(int i = 0; i < utilities.size(); i++)
			if(utilities.get(i).getName().equals(utilitieType))
				return utilities.get(i);
		return null;
	}
	
	private Lote getLote(String loteName){
		
		for(Node lote : this.vertexSet)
			if(lote.getName().equals(loteName))
				return (Lote)lote;
		
		return null;
	}
	
	public void addConnection(String lote1, String lote2, double distance){
		
		Lote _temp_lote1 = getLote(lote1),
			 _temp_lote2 = getLote(lote2);
		if(_temp_lote1 != null && _temp_lote2 != null){
			_temp_lote1.addEdge(new Distance((Lote)_temp_lote2,distance));
			_temp_lote2.addEdge(new Distance(_temp_lote1,distance));
		}
	}
	
	public int getNumberOfLotes(){
		return this.getNumVertex();
	}
	
	public void printInfo(){
		
		for(Node lote : this.vertexSet)
			lote.printInfo();
		
		System.out.println("Numero de utilidades: " + UtilitieType.getNumberOfUtilities());
	}
	
	private void loadLotes() throws IOException{
		
		CurrentLoadOperation loadOperation = null;
		String[] _temp_str_arr;
		
		BufferedReader fd = new BufferedReader(new FileReader("geographicArea.txt"));
		
	    try {
	        String line = fd.readLine();

	        while (line != null) {    
	        	
	        	_temp_str_arr = line.split("//");
	        	
	        	if(line.split("//").length > 1 && line.split("//")[1].equals("Lotes"))
	        		loadOperation = CurrentLoadOperation.LOAD_LOTES;
	        	else if(line.split("//").length > 1 &&  line.split("//")[1].equals("Connections"))
	        		loadOperation = CurrentLoadOperation.LOAD_CONNECTIONS;
	        	else{
	        		
	        		if(loadOperation != null)
	        			if(loadOperation == CurrentLoadOperation.LOAD_LOTES){
	        				_temp_str_arr = line.split("-");
	        				addLote(new Lote(_temp_str_arr[0],_temp_str_arr[1],_temp_str_arr[2],Integer.parseInt(_temp_str_arr[3]),Integer.parseInt(_temp_str_arr[4]),getUtilitie(_temp_str_arr[5])));
	        			}
	        			else
	        				if(loadOperation == CurrentLoadOperation.LOAD_CONNECTIONS){
	        					_temp_str_arr = line.split("-");
	        					addConnection(_temp_str_arr[0],_temp_str_arr[1],Integer.parseInt(_temp_str_arr[2]));
	        				}
	        	}
	        		
	            line = fd.readLine();
	        }
	        
	    } finally {
	        fd.close();
	    }
		
	}
	
	
	public static void main(String[] args) throws IOException{
		
		GeographicArea ga = new GeographicArea();		
		
		ga.printInfo();
	}

}
