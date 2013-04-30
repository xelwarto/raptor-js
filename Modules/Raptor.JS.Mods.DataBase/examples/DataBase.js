/* DataBase Example
 * 
 * Constructor: 
 * var db = new DataBase({
 *  driver: "com.mysql.jdbc.Driver",
 *  url: "jdbc:mysql://localhost.local:3306/test",
 *  user: "dbuser",
 *  password: "test1234"
 * });
 * 
 * - user: a string containing the connection user id
 * - password: a string containing the connection password. The password can
 * - driver: a string containing JDBC driver class name
 * - url: a string containing the JDBC connection url
 *  
 *  Methods:
 *  db.runSelect(sql,[param1,param2]);
 *  - sql: A string containing the sql statement to run. This may contain 
 *    statement markers in the form of a question mark (?).
 *  - The second parameter is an array of the values to be passwed to the 
 *    statement markers in the sql string. This parameter can not be null, use 
 *    an empty array ([]) for queries which do not have any statement markers.
 *    
 *  db.runInsert(sql,[param1,param2]);
 *  - sql: A string containing the sql statement to run. This may contain 
 *    statement markers in the form of a question mark (?).
 *  - The second parameter is an array of the values to be passwed to the 
 *    statement markers in the sql string. This parameter can not be null, use 
 *    an empty array ([]) for queries which do not have any statement markers.
 *  - Run an sql insert
 *    
 *  db.runUpdate(sql,[param1,param2]);
 *  - sql: A string containing the sql statement to run. This may contain 
 *    statement markers in the form of a question mark (?).
 *  - The second parameter is an array of the values to be passwed to the 
 *    statement markers in the sql string. This parameter can not be null, use 
 *    an empty array ([]) for queries which do not have any statement markers.
 *  - Run an sql update
 *  
 *  db.close();
 *  Close the database connection.
 *
 *  db.getLabels();
 *  - Returns an array of the labels from a select query
 *    
 *  
 * Written By: Ted Elwartowski - 2008
 * */
var db = new DataBase({
    driver: "com.mysql.jdbc.Driver",
    url: "jdbc:mysql://localhost.local:3306/test",
    user: "dbuser",
    password: "test1234"
});

var sql = "select * from test_table order by id";
var results = db.runSelect(sql,[]);

if (results.length > 0) {
    for (i = 0; i < results.length; i++) {
        var obj = results[i];
        out.print(obj.dept_descr);
        out.print(" ~ ");
        out.println(obj.dept_id);
    }
}
db.close();
