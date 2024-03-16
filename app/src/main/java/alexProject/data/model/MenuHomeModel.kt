package alexProject.data.model

data class MenuHomeModel(
    val title: Int,
    val image: Int,
    val destination: DestinationHome
)

enum class DestinationHome {
    COMIC,
    CHARACTERS,
    EVENTS,
    MOVIES,
    TV_SHOW,
    GAMES,
    TEAMS
}
