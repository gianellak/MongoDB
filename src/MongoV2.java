import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class MongoV2 {



	public static void main(String[] args) throws IOException {

		
		
		InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\Users\\Yo\\Desktop\\movimientos_1000000.txt"), "UTF-8");
        BufferedReader br = new BufferedReader(isr);


        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        
        DB db = mongoClient.getDB("test");
        
        DBCollection coll = db.getCollection("texto");
        
        coll.drop();
     
        String line = br.readLine();
        
        BasicDBObject document ;
    	final int batchSize = 10000;
        long inicio =  System.currentTimeMillis();
        BulkWriteOperation  bulk = coll.initializeUnorderedBulkOperation();
    
        int count = 0;
		while (line != null ) {    
       

            	document= new BasicDBObject();
            	
            	 String[] partes = line.split(",");

    		     String id = partes[0];
    		     
    		     Double monto = Double.parseDouble(partes[1]);
    		     
    		     String c = partes[2];
    		     

    		    
            	if (line.trim().isEmpty() != true){
            		
            		document.put("idCliente", id);
            		document.put("monto", monto);
            		document.put("tipo", c);
            	
            		
            		
            		bulk.insert(document);
            		
            	
            		if(++count % batchSize == 0) {
            			BulkWriteResult  r1 = bulk.execute();
            			bulk = coll.initializeUnorderedBulkOperation();
        		    	System.out.println("imprimo: " + coll.count());
            			
            		}
            		
            		
            	}
    		    	
           line = br.readLine();   
		}
		
		
		//BulkWriteResult  r1 = bulk.execute();
		
		

		long finProc =  System.currentTimeMillis();
		System.out.println("Tardo: " + (finProc - inicio) );
		
		long proceso = finProc - inicio;
		
		inicio =  System.currentTimeMillis();
		
		ConsultaMongo.consulta(coll);
		
		finProc =  System.currentTimeMillis();
		
		System.out.println("Tardo en la consulta: " + (finProc - inicio) );
		
		long consulta  = finProc - inicio;
		
		System.out.println("Tardo en total: " + (proceso + consulta) );

		
	}	
		
		
		
}

