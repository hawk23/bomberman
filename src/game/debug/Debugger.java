package game.debug;

/**
 * Created by Roland Schreier on 03.04.2015.
 *
 * For printing to the Console.
 */
public class Debugger {
    public static boolean isEnabled(){
        return true;
    }

    public static void log(Object o){
        if(Debugger.isEnabled()) {
            System.out.println(o.toString());
        }
    }
}
