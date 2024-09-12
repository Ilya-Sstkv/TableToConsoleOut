import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TableToConsoleOut {

    //The number of list components determines the number of columns in the table
    //Each component of the list stores the maximum value of content length in the corresponding column
    private final List<Double> columnWidth = new ArrayList<>();

    //List of methods of the content class
    private List<Method> methods;

    //Table header that contains the names of the columns
    private String[] header;

    //Determines whether the rows of the table will be numbered
    private boolean enumerate = true;

    //The name of the table
    private String tableName;

    private String[] customSorting;

    public void setHeader(String[] header){
        this.header = header;
    }

    public void setEnumerate(boolean value){
        this.enumerate = value;
    }

    public void setTableName(String name){
        this.tableName = name;
    }

    public void setCustomSorting(String[] sorting) {
        this.customSorting = sorting;
    }

    //This method defines the order in which columns are displayed in the table
    private int getMethodOrder(String methodName) {
        for(int i = 0; i < customSorting.length; i++){
            if (methodName.equals(customSorting[i])) return i;
        }
        return Integer.MAX_VALUE;
    }

    //Returns the length (number of characters) of the input
    public double getLength(Object input){
        if(input == null){
            return 0;
        }
        else if(input instanceof String){
            return ((String) input).length();
        }
        else {
            return String.valueOf(input).length();
        }
    }

    //Defines the number of columns in the table according to the number of methods
    private int defineColumnNumber(){
        int columnNumber = 1;
        for (Method method : methods) {
            if (isGetter(method)) columnNumber++;
        }
        if(header!=null) return Math.max(header.length, columnNumber);
        else return columnNumber;
    }

    //Sets the column width to match the header (if there is one)
    private void sizeReset(){
        columnWidth.clear();
        for (int i = 0; i < defineColumnNumber(); i++) {
            if(header != null && i < header.length) {
                columnWidth.add(getLength(header[i]) + 2);
            }
            else columnWidth.add(1d);
        }
    }

    //Returns true if the method is a getter.
    //The methods of the class must be named properly - "getSomething()".
    private boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0;
    }

    //Putting all methods of a content class in a list of methods
    //Removing unnecessary methods
    //Sorting the list of methods in the correct way
    private <T> void methodsAdjustment(T obj){
        methods = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredMethods()));
        methods.removeIf(method -> !isGetter(method));
        if(customSorting!=null) {
            methods.removeIf(method -> !Arrays.asList(customSorting).contains(method.getName()));
            methods.sort(Comparator.comparingInt(method -> getMethodOrder(method.getName())));
        }
    }

    //Defines the length of the longest content for each column of the table
    private <T> void setMaxLength(List<T> list) {
        methodsAdjustment(list.getFirst());
        sizeReset();
        if(getLength(list.size())+2 > columnWidth.getFirst())
            columnWidth.set(0, getLength(list.size())+2);
        for (T obj : list) {
            int columnPos = 1;
            for (Method method : methods) {
                try {
                    if (getLength(method.invoke(obj)) + 2 > columnWidth.get(columnPos)) {
                        columnWidth.set(columnPos, getLength(method.invoke(obj)) + 2);
                    }
                    columnPos++;
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println("Error in method calling occurred: " + e);
                }
            }
        }
    }

    //Draws a horizontal line of the required size
    private void drawLine(){
        double verticalLine;
        List<Double> column = new ArrayList<>();
        column.add(0d);
        for(int i = enumerate ? 0 : 1; i < defineColumnNumber(); i++) {
            verticalLine = Math.ceil(columnWidth.get(i) / 4);
            column.add(column.getLast() + verticalLine);
        }
        for(double j = 0; j < column.getLast(); j++){
            if(column.contains(j)) {
                System.out.print("+---");
            }
            else System.out.print("----");
        }
        System.out.println("+");
    }

    //Does tabulation the required number of times for each content
    private void setTab(int i, double length){
        double maxLengthTab = Math.ceil(columnWidth.get(i) /4);
        double realTab = (length/4);
        double necessaryTab = maxLengthTab - realTab;
        for(int j = 0; j < necessaryTab; j++){
            System.out.print("\t");
        }
    }

    //Outputs all the components of the table in a structured form
    public <T> void tableOut(List<T> list){
        setMaxLength(list);
        if(tableName != null) {
            System.out.println("TABLE: " + String.format(tableName).toUpperCase() + "\n");
        }
        if(header != null) {
            if (header.length-1 != methods.size()) {
                System.out.println("WARNING! The table header does not correspond" +
                        " to the number of its columns\n");
            }
            for (int i = enumerate ? 0 : 1; i < header.length; i++) {
                System.out.print("| " + header[i]);
                setTab(i, getLength(header[i]) + 2);
            }
            System.out.println("|");
        }
        drawLine();
        for (int i = 0; i < list.size(); i++) {
            int methodCount = 1;
            if(enumerate) {
                System.out.print("| " + (i + 1));
                setTab(0, getLength(i + 1) + 2);
            }
            System.out.print("|");
            for (Method method : methods) {
                try {
                    System.out.print(" " + method.invoke(list.get(i)));
                    setTab(methodCount++, getLength(method.invoke(list.get(i))) + 2);
                    System.out.print("|");
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println("\nWARNING! Error in method calling occurred: " + e);
                    return;
                }
            }
            System.out.println();
        }
    }
}
