package segtrees;

public class NaiveArray<T, U> {
	private class GenArray<F> {
		private final Object[] arr;

		public GenArray(int size) {
			arr = new Object[size];
		}

		public void set(int index, F item) {
			arr[index] = item;
		}

		@SuppressWarnings("unchecked")
		public F get(int index) {
			return (F) arr[index];
		}
	}

	private final int size;
	private final GenArray<T> val;
	private final Combiner<T> combiner;
	private final Updater<T, U> updater;

	public NaiveArray(T[] array, Combiner<T> combiner, Updater<T, U> updater) {
		this.combiner = combiner;
		this.updater = updater;
		this.size = array.length;
		this.val = new GenArray<T>(this.size);
		for (int i = 0; i < this.size; i++) {
			val.set(i, array[i]);
		}
	}

	public T query(int l, int r) throws IndexOutOfBoundsException {
		if ((l > r) || (r > this.size)) {
			throw new IndexOutOfBoundsException();
		}
		T res = combiner.neutral();
		for (int i = l; i < r; i++) {
			res = combiner.combine(res, val.get(i));
		}
		return res;
	}

	public void update(int l, int r, U value) throws IndexOutOfBoundsException {
		if ((l > r) || (r > this.size)) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = l; i < r; i++) {
			val.set(i, updater.apply(val.get(i), value, 1));
		}
	}
}
