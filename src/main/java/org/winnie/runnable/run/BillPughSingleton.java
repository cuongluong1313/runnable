package org.winnie.runnable.run;

public class BillPughSingleton {

    private BillPughSingleton() {
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }


}
