package segtrees;

public class MinCombiner implements Combiner<Long> {
	@Override
	public Long combine(Long a, Long b) {
		return Math.min(a, b);
	}

	@Override
	public Long neutral() {
		return Long.MAX_VALUE;
	}
}
