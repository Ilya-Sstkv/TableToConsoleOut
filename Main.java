import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Creating a list of some random objects of a certain class
        ArrayList<SomeClass> list = new ArrayList<>();
        list.add(new SomeClass("abcd", 1.23, "efg", 1));
        list.add(new SomeClass("hijk", 99.99, "lmnop", 22));
        list.add(new SomeClass("qrs", 0.01, "tuv", 333));
        list.add(new SomeClass("wx", 5, "yx", 4444));
        list.add(new SomeClass("!@#$%", -4, "^&*()", 55555));

        //Creating a table header and setting the column output order
        String[] header = new String[]{"#", "First", "Second", "Third", "Fourth"};
        String[] sorting = new String[]{"getFirst", "getSecond", "getThird", "getFourth"};

        //Creating an object of the TableToConsoleOut class
        TableToConsoleOut tableToConsoleOut = new TableToConsoleOut();

        //Editing table output settings
        tableToConsoleOut.setTableName("Some Table");
        tableToConsoleOut.setCustomSorting(sorting);
        tableToConsoleOut.setEnumerate(true);
        tableToConsoleOut.setHeader(header);

        //Output the table to the console
        tableToConsoleOut.tableOut(list);
    }
}