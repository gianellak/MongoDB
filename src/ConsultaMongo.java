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

public class ConsultaMongo {

	public static void consulta(DBCollection coll) {

		

		String map = "function() {" + "	if(this.tipo == 'D') " + 
				"{saldo = (-1) * this.monto;}" + 
				"else{saldo = this.monto;}" + 
				"{emit(this.idCliente,saldo);} }";

		String reduce = "function(key, values)"
				+ " { return Array.sum(values)};";
		
		MapReduceCommand cmd = new MapReduceCommand(coll, map, reduce,
            null, MapReduceCommand.OutputType.INLINE, null);


		MapReduceOutput out = coll.mapReduce(cmd);
		
		
		
		//for (DBObject o : out.results()) {
		//	System.out.println(o.toString());
			
		//}

		
	}	
	
		
		
}

