package plus.jdk.zeus.common.skeleton;

@FunctionalInterface
public interface IKeyReader<K, T> {
    K getKey(T data);
}
