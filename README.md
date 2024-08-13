# TableToConsoleOut
A Java class that allows you to present a list of objects in the form of a table and display it in the console.

First, you need to create a list of objects of some class.
Then, you need to create an object of the TableToConsoleOut class and call the tableOut(list) method in it, to which you will pass your list of objects.
Your list will be displayed in the console in the format of a structured and aligned table, where each column corresponds to a getter method in your class.

You can also customize some optional output parameters of the table, such as:
- Table header (pass a String[] array containing column names (including the name for the numbering column) to the setHeader() method);
- The order of calling class getters (the order of columns in the table) (also a String[] array that should be passed to the setCustomSorting() method)
//If you do not set your own sorting method, the columns will be displayed in random order (there may be a discrepancy with the header);
- Table name (it will be displayed alongside with the table);
- Line enumeration (will the lines be numbered);
  
//Might later add a function for displaying a table from a specific list item or displaying a certain number of lines.

The class can be used in console Java applications to simplify the process of displaying any list of objects in the console;
it also makes it easier for the user to read data from the console.
