import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
/*
create table conf (value varchar(100));
insert into conf values ('x=1');
insert into conf values ('y=2');
insert into conf values ('z=3');
 */
class DatabaseReader {
	public List<String> select() throws SQLException {
		List<String> result = new LinkedList<>();
		String url = "jdbc:h2:tcp://localhost/~/test";
		try (Connection conn = DriverManager.getConnection(url, "sa", "")) {
			try (Statement st = conn.createStatement()) {
				String sql = "select value from conf";
				try (ResultSet rs = st.executeQuery(sql)) {
					while (rs.next()) {
						result.add(rs.getString(1));
					}
				}
			}
			return result;
		}
	}
}

class FileReader {
	public String[] read() throws IOException {
		File file = new File("conf.txt");
		return FileUtils.readFileToString(file).split(";");
	}
}


//interface Adapter {
//	Map<String, String> read() throws Exception;
//}
//class DatabaseAdapter implements Adapter {
//
//}
//
//class FileAdapter implements Adapter {
//
//}

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("データベースからの設定値読み取り");
		readDatabase();

		System.out.println("ファイルからの設定値読み取り");
		readFile();


	}

	public static void readDatabase() throws SQLException {
		DatabaseReader dr = new DatabaseReader();
		List<String> result = dr.select();
		for (String s: result) {
			System.out.println(s);
		}
	}

	public static void readFile() throws IOException {
		FileReader fr = new FileReader();
		String[] result = fr.read();
		for (String s: result) {
			System.out.println(s);
		}
	}
}
