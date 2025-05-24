package segtrees;

import java.util.Arrays;

public class SegmentTree<T, U> {
	private final int n;
	private final int size;
	private final T[] val;
	private final U[] lz;
	private final Combiner<T> combiner;
	private final Updater<T, U> updater;

	public SegmentTree(T[] array, Combiner<T> combiner, Updater<T, U> updater) {
		this.combiner = combiner;
		this.updater = updater;
		this.n = array.length;
		this.size = 4 * n;
		this.val = (T[])new Object[2 * this.size];
		this.lz = (U[])new Object[2 * this.size];
		Arrays.fill(this.lz, updater.neutral());
		build(array, 0, 0, this.size);
	}

	private void build(T[] array, int node, int nodeLeft, int nodeRight) {
		if (nodeRight - nodeLeft == 1) {
			if (nodeLeft < n) {
				val[node] = array[nodeLeft];
			} else {
				val[node] = combiner.neutral();
			}
			return;
		}
		int mid = (nodeLeft + nodeRight) / 2;
		build(array, 2 * node + 1, nodeLeft, mid);
		build(array, 2 * node + 2, mid, nodeRight);
		val[node] = combiner.combine(val[2 * node + 1], val[2 * node + 2]);
	}

	public T query(int l, int r) {
		return query(l, r, 0, 0, this.size);
	}

	private T query(int l, int r, int node, int nodeLeft, int nodeRight) {
		if (nodeRight <= l || r <= nodeLeft) {
			return combiner.neutral();
		}
		if (l <= nodeLeft && nodeRight <= r) {
			return val[node];
		}
		propagate(node, nodeLeft, nodeRight);
		int mid = (nodeLeft + nodeRight) / 2;
		T left = query(l, r, 2 * node + 1, nodeLeft, mid);
		T right = query(l, r, 2 * node + 2, mid, nodeRight);
		return combiner.combine(left, right);
	}

	public void update(int l, int r, U update) {
		update(l, r, update, 0, 0, this.size);
	}

	private void update(int l, int r, U update, int node, int nodeLeft, int nodeRight) {
		if (nodeRight <= l || r <= nodeLeft) {
			return;
		}
		if (l <= nodeLeft && nodeRight <= r) {
			applyUpdate(node, update, nodeRight - nodeLeft);
			return;
		}
		propagate(node, nodeLeft, nodeRight);
		int mid = (nodeLeft + nodeRight) / 2;
		update(l, r, update, 2 * node + 1, nodeLeft, mid);
		update(l, r, update, 2 * node + 2, mid, nodeRight);
		val[node] = combiner.combine(val[2 * node + 1], val[2 * node + 2]);
	}

	private void propagate(int node, int nodeLeft, int nodeRight) {
		if (lz[node] == updater.neutral()) {
			return;
		}
		int mid = (nodeLeft + nodeRight) / 2;
		applyUpdate(2 * node + 1, lz[node], mid - nodeLeft);
		applyUpdate(2 * node + 2, lz[node], nodeRight - mid);
		lz[node] = updater.neutral();
	}

	private void applyUpdate(int node, U update, int len) {
		T updatedval = updater.apply(val[node], update, len);
		val[node] = updatedval;
		U composed = updater.compose(lz[node], update);
		lz[node] = composed;
	}
}
