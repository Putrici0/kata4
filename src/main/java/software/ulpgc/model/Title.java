package software.ulpgc.model;

public record Title(String id, TitleType titleType,
					String primaryTitle) {

	public enum TitleType {
		VideoGame,
		TvPilot,
		Movie,
		TvSeries,
		TvMiniSeries,
		Short,
		TvSpecial,
		TvShort,
		Video,
		TvMovie,
		TvEpisode
	}
}
