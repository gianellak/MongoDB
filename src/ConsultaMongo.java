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

		

		String map = "function(){" +
			"var key = this.idCliente;" +
			"if (this.tipo = 'D'){" +
			" var value = (this.monto *= (-1))}" +
			"else {var value = this.monto};" +
			"emit(key, {count:value}); };";
		
		String reduce = "function(key, values){total= 0;"
				+ "for(var k in key){"
				+ "for(var i in values){"
				+ "total+=values[i].count;}"
				+ "return total;} }";
		
		MapReduceCommand cmd = new MapReduceCommand(coll, map, reduce,
            null, MapReduceCommand.OutputType.INLINE, null);


		MapReduceOutput out = coll.mapReduce(cmd);
		
		
		
		//for (DBObject o : out.results()) {
		//	System.out.println(o.toString());
			
		//}

		
	}	
	
		
		
}

