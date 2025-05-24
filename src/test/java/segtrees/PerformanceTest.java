package segtrees;

import java.util.Random;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTest {
	@Test
	void speedLarge() {
		int n = 1_000_000;
		int ops = 1_000_000;
		Long[] a = new Long[n];
		Random rnd = new Random(1);
		Arrays.fill(a, rnd.nextLong());
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new SumCombiner(), new AddUpdater());
		long t0 = System.nanoTime();
		for (int i = 0; i < ops; i++) {
			if (rnd.nextBoolean()) {
				int l = rnd.nextInt(n), r = rnd.nextInt(n - l) + l + 1;
				st.update(l, r, rnd.nextLong(1000));
			} else {
				int l = rnd.nextInt(n), r = rnd.nextInt(n - l) + l + 1;
				st.query(l, r);
			}
		}
		long ms = (System.nanoTime() - t0) / 1_000_000;
		assertTrue(ms < 2000, "slow: " + ms + "ms");
	}
}
