package software.ulpgc.model;

public record Title(String id, TitleType titleType, String primaryTitle) {

    public enum TitleType{
        VideoGame,
        TvPilot,
        Movie,
        tvSeries,
        TvMiniSeries,
        Short,
        TvSpecial,
        TvShort,
        Video,
        TvMovie,
        TvEpisode
    }
}
