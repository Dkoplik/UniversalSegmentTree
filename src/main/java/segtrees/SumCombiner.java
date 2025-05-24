package segtrees;

public class SumCombiner implements Combiner<Long> {
	@Override
	public Long combine(Long a, Long b) {
		return a + b;
	}

	@Override
	public Long neutral() {
		return 0L;
	}
}
