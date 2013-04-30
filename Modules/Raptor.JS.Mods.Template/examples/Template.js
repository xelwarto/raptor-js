/* Template Example
 * This javascript is an example of how to use the Template class to create
 * templates with in your script.
 * 
 * Constructor: 
 * var tmpl = new Template(text);
 *  - text is a string which includes template markers in the form
 *    of {0},{1}...
 *  
 *  Methods:
 *  tmpl.getMessage([text1,text2]);
 *  - Returns a string containing the values applied to the template string.
 *  - The getMessage method requires an array of strings.
 *  - The array size should be equal to the number of template markers defined.
 *  
 * Written By: Ted Elwartowski - 2008
 * */
var text = "Hello {0},\n\tThis is an example of the {1} class " +
    "and how to use it.";
var text2 = "\n\nThank You\n{0}\n";

var array = [
    "Ted",
    "Neil",
    "Ali",
    "Dave"
];
var me = "Ted Elwartowski";
var name = "Template";

var tmpl2 = new Template(text2);
var end = tmpl2.getMessage([
    me
]);

for (i=0;i<array.length;i++) {
    var tmpl1 = new Template(text);
    var outText = tmpl1.getMessage([array[i],name]);
    out.println(i);
    out.println(outText + end);
}
