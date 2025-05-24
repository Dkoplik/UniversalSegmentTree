package segtrees;

import java.util.Arrays;

public class SegmentTree<T, U> {
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

		public void fill(F value) {
			Arrays.fill(arr, value);
		}
	}

	private final int size;
	private final GenArray<T> val;
	private final GenArray<U> lz;
	private final Combiner<T> combiner;
	private final Updater<T, U> updater;

	public SegmentTree(T[] array, Combiner<T> combiner, Updater<T, U> updater) {
		this.combiner = combiner;
		this.updater = updater;
		this.size = array.length;
		this.val = new GenArray<T>(4 * this.size);
		this.lz = new GenArray<U>(4 * this.size);
		val.fill(combiner.neutral());
		lz.fill(updater.neutral());
		build(0, 0, this.size, array);
	}

	private void build(int x, int lx, int rx, T[] arr) {
		if (rx - lx == 1) {
			val.set(x, arr[lx]);
			return;
		}
		int m = (lx + rx) / 2;
		build(2 * x + 1, lx, m, arr);
		build(2 * x + 2, m, rx, arr);
		val.set(x, combiner.combine(val.get(2 * x + 1), val.get(2 * x + 2)));
	}

	public T query(int l, int r) throws IndexOutOfBoundsException {
		if ((l > r) || (r > this.size)) {
			throw new IndexOutOfBoundsException();
		}
		return query(0, 0, this.size, l, r);
	}

	private T query(int x, int lx, int rx, int l, int r) {
		if (r <= lx || rx <= l) {
			return combiner.neutral();
		}
		if (l <= lx && rx <= r) {
			return val.get(x);
		}
		push(x, lx, rx);
		int m = (lx + rx) / 2;
		T qleft = query(2 * x + 1, lx, m, l, r);
		T qright = query(2 * x + 2, m, rx, l, r);
		return combiner.combine(qleft, qright);
	}

	public void update(int l, int r, U value) throws IndexOutOfBoundsException {
		if ((l > r) || (r > this.size)) {
			throw new IndexOutOfBoundsException();
		}
		update(0, 0, this.size, l, r, value);
	}

	private void update(int x, int lx, int rx, int l, int r, U value) {
		if (r <= lx || rx <= l) {
			return;
		}
		if (l <= lx && rx <= r) {
			applyUpdate(x, value, rx - lx);
			return;
		}
		push(x, lx, rx);
		int m = (lx + rx) / 2;
		update(2 * x + 1, lx, m, l, r, value);
		update(2 * x + 2, m, rx, l, r, value);
		val.set(x, combiner.combine(val.get(2 * x + 1), val.get(2 * x + 2)));
	}

	private void push(int x, int lx, int rx) {
		if (lz.get(x) == updater.neutral()) {
			return;
		}
		if (rx - lx == 1) {
			lz.set(x, updater.neutral());
			return;
		}
		int m = (lx + rx) / 2;
		applyUpdate(2 * x + 1, lz.get(x), m - lx);
		applyUpdate(2 * x + 2, lz.get(x), rx - m);
		lz.set(x, updater.neutral());
	}

	private void applyUpdate(int x, U value, int len) {
		val.set(x, updater.apply(val.get(x), value, len));
		lz.set(x, updater.compose(lz.get(x), value));
	}
}
