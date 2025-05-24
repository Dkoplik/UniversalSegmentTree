package segtrees;

public interface Updater<T, U> {
	T apply(T current, U update, int len);

	U compose(U existing, U update);

	U neutral();
}
