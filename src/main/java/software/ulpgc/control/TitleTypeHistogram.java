package software.ulpgc.control;

import software.ulpgc.model.Histogram;
import software.ulpgc.model.Title;

import java.io.IOException;
import java.util.*;

public class TitleTypeHistogram implements Histogram {
	private final Map<Title.TitleType, Integer> histogram;

	public TitleTypeHistogram(TitleReader reader) throws IOException {
		histogram = createHistogram(reader);
	}

	@Override
	public String title() {
		return "Title types distribution";
	}

	@Override
	public List<String> keys() {
		List<String> keys = new ArrayList<>();
		for (Title.TitleType titleType : histogram.keySet()) keys.add(titleType.name());
		return keys;
	}

	@Override
	public int valueOf(String key) {
		Title.TitleType titleType = Title.TitleType.valueOf(key);
		return histogram.get(titleType);
	}

	private static Map<Title.TitleType, Integer> createHistogram(TitleReader titles) throws IOException {
		Map<Title.TitleType, Integer> histogram = new HashMap<>();
		Iterator<Title> titleIterator = titles.read();
		while (titleIterator.hasNext()){
			var t = titleIterator.next();
			histogram.putIfAbsent(t.titleType(), 0);
			histogram.computeIfPresent(t.titleType(), (tt, i) -> i + 1);
		}
		return histogram;
	}
}