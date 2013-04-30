/* DataBase Example
*/

var cfg = {
   db: {
      driver: "com.mysql.jdbc.Driver",
      url: "jdbc:mysql://localhost.local:3306/test",
      user: "dbuser",
      password: "test1234"
   }
};


var db = new DataBase(cfg.db);

var sql = "INSERT into test_table VALUES(?,?)";
db.runInsert(sql,["v1", "v2"]);

db.close();
