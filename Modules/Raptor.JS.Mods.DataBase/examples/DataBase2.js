/* DataBase Example
 * 
 * Constructor: 
 * var db = new DataBase({
 *    datasource: "jdbc/test"
 * });
 * 
 * - datasource: A string containing a configured java datasource
 *
 * Written By: Ted Elwartowski - 2008
 */
var db = new DataBase({
   datasource: "jdbc/test"
});
db.close();
