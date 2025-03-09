import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws AWTException, UnsupportedFlavorException, IOException {
        boolean isValidZip;
        Robot inputController = new Robot(); //create robot object

        String zipCodesString = """
        10001-14999, 15001-19699, 19700-19999, 19815, 19853, 19925, 20001-20599, 20101-20199,  
        20331, 20588, 20601-21999, 22001-24699, 24701-26899, 27001-28999, 29001-29999, 
        30001-31999, 31788, 31905, 32001-34999, 32350, 35001-36999, 37001-38599, 38601-39799, 
        38769, 38852, 39801-39899, 39901, 40001-42799, 43001-45999, 46001-47999, 48001-49999, 
        50001-52899, 53001-54999, 55001-56799, 56701-56799, 56901-56999, 57001-57799, 57026, 
        57030, 57068, 57078, 57638, 57641-57642, 57645, 57648, 57660, 57683, 57724, 
        57840-57841, 57949, 58000-58899, 58982, 59000-59999, 60001-62999, 63001-65899, 65729, 
        65733, 65761, 66001-67999, 66541, 67950, 68001-69399, 68119-68120, 68325, 68719, 68978, 
        69201, 69212, 69216, 70000-71499, 71233, 71545, 71601-72999, 71749, 72338, 72644, 
        73001-73199, 73301, 73344, 73367, 73371, 73401-74999, 75001-79999, 75502, 75556, 76356, 
        79051, 79837, 79922, 80000-81699, 80758, 82001-83199, 82063, 82082, 82801, 82701, 
        82930, 83111, 83120, 83201-83899, 84001-84799, 84034, 84531, 84536, 85001-86599, 86504, 
        86515, 86631, 87001-88499, 88501-88599, 88603, 88901-89999, 89010, 89019, 89024, 89060, 
        89421, 89439, 90001-96199, 96701-96899, 97001-97999, 97635, 97910, 97913, 98001-99499, 
        98791, 99128, 99362, 99501-99999
        """; //zip codes string
        String[] zipCodesArray = zipCodesString.split(", "); //split zip codes into array


        inputController.mouseMove(1253, 10); //move mouse to minimize code editor
        inputController.delay(1000); //wait for 1 second
        inputController.mousePress(InputEvent.BUTTON1_DOWN_MASK); //press left mouse button
        inputController.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); //release left mouse button

        for (String zipCodeString : zipCodesArray) {
            if (zipCodeString.contains("-")) {
                String[] zipCodeRange = zipCodeString.split("-");
                int startZip = Integer.parseInt(zipCodeRange[0]);
                int endZip = Integer.parseInt(zipCodeRange[1]);
                for (int currentZip = startZip; currentZip <= endZip; currentZip++) {
                    enterZip(inputController, currentZip);
                }
            } else {
                int currentZip = Integer.parseInt(zipCodeString);
                enterZip(inputController, currentZip);
            }
        }

        

        
    }

    public static void enterZip(Robot robot, int zip) throws AWTException, UnsupportedFlavorException, IOException {
        System.out.println("Beginning data collection for Zip " + zip); //print first zip code
        robot.mouseMove(520, 645); //move mouse to zipcode search box
        robot.delay(1000); //wait for 1 second
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); //press left mouse button
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); //release left mouse button
        for (int i = 0; i < 5; ++i) {
            System.out.println(Integer.toString(zip).charAt(i)); //print current digit
            int keyToPress = returnKeycode(Integer.toString(zip).charAt(i)); //get keycode for current digit
            robot.keyPress(keyToPress); //press key)
            robot.delay(200); //wait for 200 milliseconds
            robot.keyRelease(keyToPress); //release key

        }
        robot.delay(1000); //wait for 1 second
        robot.keyPress(KeyEvent.VK_ENTER); //press enter key
        robot.keyRelease(KeyEvent.VK_ENTER); //release enter key
        robot.delay(1000); //wait for 1 second

        robot.mouseWheel(4); //scroll down
        robot.delay(1000); //wait for 1 second

        if(determineIfValidZip(robot, zip)) {
            
        } //determine if zip code is valid
    }

    public static int returnKeycode(char character) {
        int keyCode = 1;
        switch (character) {
            case '0':
                keyCode = KeyEvent.VK_0;
                break;
            case '1':
                keyCode = KeyEvent.VK_1;
                break;
            case '2':
                keyCode = KeyEvent.VK_2;
                break;
            case '3':
                keyCode = KeyEvent.VK_3;
                break;
            case '4':
                keyCode = KeyEvent.VK_4;
                break;
            case '5':
                keyCode = KeyEvent.VK_5;
                break;
            case '6':
                keyCode = KeyEvent.VK_6;
                break;
            case '7':
                keyCode = KeyEvent.VK_7;
                break;
            case '8':
                keyCode = KeyEvent.VK_8;
                break;
            case '9':
                keyCode = KeyEvent.VK_9;
                break;
            default:
                keyCode = 0;
        }
        return keyCode;
    }

    public static boolean determineIfValidZip(Robot robot, int zip) throws AWTException, UnsupportedFlavorException, IOException {
        robot.mouseMove(179, 226);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(80);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(80);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(80); //triple click to highlight zip code
        copyText(robot); //copy zip code
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String clipboardContents = (String) clipboard.getData(DataFlavor.stringFlavor);
        System.out.println(clipboardContents);
        if (clipboardContents.contains("Utility")) {
            System.out.println("Zip " + zip + " is valid");
            return true;
        } else {
            System.out.println("Zip " + zip + " is invalid");
            return false;
        }

    }

    public static void copyText(Robot robot) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(80);
    }

}