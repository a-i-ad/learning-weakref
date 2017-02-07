package com.aiad.learning.weakref;

import java.util.WeakHashMap;

/**
 * このコードの元ネタ: http://stackoverflow.com/questions/10599710/weakhashmap-example
 * @author aki
 *
 */
public class WeakHashMapDemo {
    public static void main(String[] args) throws Exception {
        // -- Fill a weak hash map with one entry
        WeakHashMap<Data, String> map = new WeakHashMap<Data, String>();
        Data someDataObject = new Data("foo");
        map.put(someDataObject, someDataObject.value);
        System.out.println("map contains someDataObject ? " + map.containsKey(someDataObject));

        // -- now make someDataObject elligible for garbage collection...
        someDataObject = null;

        for (int i = 0; i < 10000; i++) {
            if (map.size() != 0) {
                System.out.println("At iteration " + i + " the map still holds the reference on someDataObject");
            } else {
                System.out.println("somDataObject has finally been garbage collected at iteration " + i + ", hence the map is now empty");
                break;
            }
            // GCが早々に発生するようにメモリを無駄に消費
            StringBuffer buf = new StringBuffer(1024 * 1024); // 1K x 1K の領域を確保
            for (int k = 0; k < 100; k++) {
                buf.append("0123456789");
            }
            // 残り利用可能メモリ量を標準出力に出す
            long free = Runtime.getRuntime().freeMemory();
            System.out.println("free memory: " + free);
            Thread.sleep(1000);
        }
    }

    static class Data {
        String value;

        Data(String value) {
            this.value = value;
        }
    }
}
