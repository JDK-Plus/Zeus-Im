package plus.jdk.zeus.common.skeleton;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CollectionUtil {
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <K, T> HashMap<K, T> toHashMap(List<T> dataList, IKeyReader<K, T> keyReader) {
        HashMap<K, T> hashMap = new HashMap<>();
        if (dataList == null || keyReader == null) {
            return hashMap;
        }
        for (T data : dataList) {
            if(data == null) {
                continue;
            }
            K key = keyReader.getKey(data);
            if(key == null) {
                continue;
            }
            hashMap.put(key, data);
        }
        return hashMap;
    }
}
