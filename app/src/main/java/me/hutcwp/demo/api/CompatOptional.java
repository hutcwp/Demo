package me.hutcwp.demo.api;

/**
 * 避免空指针，java8 optional才支持,这里自己写一个简单版本
 * @author huangfan(kael)
 * @time 2017/8/16 20:24
 */

public class CompatOptional<T> {

    private static final CompatOptional<?> EMPTY = new CompatOptional<>();

    private T value;

    private CompatOptional() {
        this.value = null;
    }
    private CompatOptional(T value) {
        this.value = value;
    }

    public static <T> CompatOptional<T> ofNullable(T value) {
        return value == null ? (CompatOptional<T>) empty() : new CompatOptional<>(value);
    }

    private static<T> CompatOptional<T> empty() {
        @SuppressWarnings("unchecked")
        CompatOptional<T> t = (CompatOptional<T>) EMPTY;
        return t;
    }

    public <R> CompatOptional<R> notNull(Function<? super T> mapper) {
        if(value == null){
            return empty();
        }else{
            mapper.apply(value);
            return (CompatOptional<R>) this;
        }
    }

    public <R> CompatOptional<R> Null(NullFunction mapper){
        if(value == null){
            mapper.apply();
            return empty();
        }else{
            return (CompatOptional<R>) this;
        }
    }

    public interface Function<T>{
        void apply(T t);
    }

    public interface NullFunction{
        void apply();
    }

}
