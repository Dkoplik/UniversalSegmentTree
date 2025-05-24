package segtrees;

public class AddUpdater implements Updater<Long, Long> {
	@Override
	public Long apply(Long current, Long update, int len) {
		return current + update * len;
	}

	@Override
	public Long compose(Long existing, Long update) {
		return existing + update;
	}

	@Override
	public Long neutral() {
		return 0L;
	}
}
