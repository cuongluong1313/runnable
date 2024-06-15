package org.winnie.runnable.run;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class InvokeJavaScript {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");

        // evaluate JavaScript code that defines an object with one method
        engine.eval("var obj = new Object()");
        engine.eval("obj.hello = function(name) { print('Hello, ' + name) }");

        // expose object defined in the script to the Java application
        Object obj = engine.get("obj");

        // create an Invocable object by casting the script engine object
        Invocable inv = (Invocable) engine;

        // invoke the method named "hello" on the object defined in the script
        // with "Script Method!" as the argument
        inv.invokeMethod(obj, "hello", "Script Method!");
    }

}
