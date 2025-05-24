package segtrees;

public interface Combiner<T> {
	T combine(T a, T b);

	T neutral();
}
